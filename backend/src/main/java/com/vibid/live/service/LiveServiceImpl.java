package com.vibid.live.service;

import com.vibid.bid.service.BidService;
import com.vibid.board.domain.Board;
import com.vibid.board.controller.dto.OrderType;
import com.vibid.board.repository.BoardRepository;
import com.vibid.board.repository.WishRepository;
import com.vibid.exception.NotFoundException;
import com.vibid.notify.service.NotifyService;
import io.openvidu.java.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class LiveServiceImpl implements LiveService {

  // Openvidu SDK
  private final OpenVidu openVidu;

  // Listening URL
  private final String OPENVIDU_URL;

  // Openvidu 서버와 공유할 SECRET KEY
  private final String SECRET_KEY;

  // 개설된 라이브 세션 관리 <Board id, Session>
  private static Map<Long, Session> sessions = new ConcurrentHashMap<>();

  // 개설된 라이브 세션 Open 관리
  private static Map<Long, Boolean> sessionOpened = new ConcurrentHashMap<>();

  // 세션 별 참여자 관리 Map  <Board id, <Member id, role>
  private static Map<Long, Map<Long, Connection>> membersOfSession = new ConcurrentHashMap<>();

  private final BoardRepository boardRepository;

  private final NotifyService notifyService;

  private final WishRepository wishRepository;

  private final BidService bidService;

  public LiveServiceImpl(
    @Value("${openvidu.url}") String openviduUrl,
    @Value("${openvidu.secret}") String secret,
    BoardRepository boardRepository,
    NotifyService notifyService,
    WishRepository wishRepository,
    BidService bidService) {

    this.SECRET_KEY = secret;
    this.OPENVIDU_URL = openviduUrl;
    this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET_KEY);
    this.boardRepository = boardRepository;
    this.notifyService = notifyService;
    this.wishRepository = wishRepository;
    this.bidService = bidService;
  }

  @Override
  public boolean isContain(Long boardId, Long memberId) {
    if(membersOfSession.containsKey(boardId))
      return membersOfSession.get(boardId).containsKey(memberId);
    return false;
  }

  @Override
  public List<Long> popularityList(OrderType order, int limit) {
    Map<Integer, Long> sorted = new TreeMap<>(order == OrderType.DESC ? Collections.reverseOrder():null);

    membersOfSession.forEach((K, V) -> {
      sorted.put(V.size(),K);
    });

    List<Long> result = new ArrayList<>();
    Iterator<Integer> iter = sorted.keySet().iterator();
    for (int count = 0 ; count < limit && iter.hasNext(); count++) {
      result.add(sorted.get(iter.next()));
    }

    return result;
  }

  @Override
  @Transactional
  public String joinLive(Long memberId, String nickname, Long boardId) throws OpenViduJavaClientException, OpenViduHttpException, IllegalAccessException {

    Board board = boardRepository.findById(boardId)
      .orElseThrow(() -> new NotFoundException(Board.class, boardId));
    // 경매 종료 유무 점검
    if (board.getBidInfo().getEndingPrice() != null) {
      throw new IllegalAccessException("이미 종료된 경매입니다.");
    }

    // 경매 시작 시간 점검
//    if (board.getBidInfo().getBidStartTime().isAfter(LocalDateTime.now())) {
//      throw new IllegalAccessException("경매 시간이 되지 않았습니다.");
//    }

    // 작성자 유무 파악해 구매자인지 판매자인지 분류
    // PUBLISHER : 판매자 / SUBSCRIBER : 구매자
    OpenViduRole role = board.isAuthor(memberId) ? OpenViduRole.PUBLISHER : OpenViduRole.SUBSCRIBER;
    Connection connection = null;
    if(role == OpenViduRole.PUBLISHER) {
      if(!isCreated(board.getId())) {
        connection = createSession(board.getId(), role, getBroadCastMsg(role, nickname));
        bidService.createSession(board.getId());

      }
      else
        connection = membersOfSession.get(board.getId()).get(memberId);
    } else {
      if(isOpened(board.getId())) {
          connection = joinSession(board.getId(), role, getBroadCastMsg(role, nickname));
      }
    }

    return Optional.ofNullable(connection)
      .map(con -> { this.membersOfSession.get(board.getId()).put(memberId, con);
        return con.getToken();})
      .orElseThrow(() -> new IllegalAccessError("아직 오픈되지 않은 경매입니다."));
  }

  @Override
  public void closeLive(Long memberId, Long boardId, String token) throws OpenViduJavaClientException, OpenViduHttpException {
    // 세션이 존재하고 세션에 유저가 참여중일 경우에만 처리
    if(sessions.containsKey(boardId) && membersOfSession.get(boardId).containsKey(memberId)) {
      // Publisher일 경우 세션 종료 & 모든 구매자 추방
      // Subscriber일 경우 memberOfSession에서만 삭제
      Connection connection = membersOfSession.get(boardId).get(memberId);
      if(connection.getRole() == OpenViduRole.PUBLISHER) {
        kickAllUser(membersOfSession.get(boardId),sessions.get(boardId));
        membersOfSession.remove(boardId);
        sessionOpened.remove(boardId);
        sessions.remove(boardId);
      } else {
        membersOfSession.get(boardId).remove(memberId);
      }
    }
  }

  /**
   * Collection으로 관리하고 있는 Session, 사용자 tokens 정보 삭제
   * @param connections       해당 세션의 모든 token 무효화
   * @param session
   */
  private void kickAllUser (Map<Long, Connection> connections, Session session) throws OpenViduJavaClientException, OpenViduHttpException {
    Iterator<Long> iterator = connections.keySet().iterator();
    while(iterator.hasNext()) {
      Long memberId = iterator.next();
      session.forceDisconnect(connections.get(memberId));
    }
    connections.clear();
  }

  private Connection createSession(Long boardId, OpenViduRole role, String broadCastMsg) throws OpenViduJavaClientException, OpenViduHttpException {
    // 세션 생성
    Session session = openVidu.createSession();
    // 토큰 생성
    Connection connection = session.createConnection(createConnectionProperties(role, broadCastMsg));

    this.sessions.put(boardId, session);
    this.membersOfSession.put(boardId, new ConcurrentHashMap<>());
    this.sessionOpened.put(boardId, false);

    return connection;
  }

  private Connection joinSession(Long boardId, OpenViduRole role, String broadCastMsg) throws OpenViduJavaClientException, OpenViduHttpException {
    return this.sessions.get(boardId)
      .createConnection(
        createConnectionProperties(role, broadCastMsg));
  }

  @Override
  public boolean isOpened(Long boardId) {
    return sessions.containsKey(boardId) && sessionOpened.get(boardId);
  }

  @Override
  @Transactional
  public boolean liveOpen(Long memberId, Long boardId) {
    return boardRepository.findById(boardId)
      .filter(board -> board.isAuthor(memberId))
      .filter(board -> !sessionOpened.get(board.getId()))
      .map(board -> {
        board.liveOn();
        wishRepository.findAllMemberByBoard(board)
            .forEach(member ->
              notifyService.save(member, board,"["+board.getTitle()+"] 경매가 시작됐습니다"));

        return sessionOpened.replace(boardId, true);
      })
      .isPresent();
  }

  @Override
  public boolean isCreated(Long boardId) {
    return sessions.containsKey(boardId);
  }

  private ConnectionProperties createConnectionProperties(OpenViduRole role, String data) {
    return new ConnectionProperties.Builder().
      type(ConnectionType.WEBRTC)
      .role(role)
      .data(data)
      .build();
  }

  private String getBroadCastMsg(OpenViduRole role, String nickname) {
    StringBuilder stringBuilder = new StringBuilder();
    return stringBuilder.append(nickname).append("님 ").append(role == OpenViduRole.PUBLISHER ? "[판매자]" : "[구매자]")
      .append("\n").append("입장 하셨습니다.").toString();
  }
}

