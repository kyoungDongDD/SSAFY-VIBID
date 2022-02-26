package com.vibid.chat.controller;

import com.vibid.api.ApiResult;
import com.vibid.auth.jwt.domain.JwtAuthentication;
import com.vibid.chat.controller.dto.RequestChatDto;
import com.vibid.chat.controller.dto.ResponseChatDto;
import com.vibid.chat.service.ChatService;
import com.vibid.exception.NotFoundException;
import com.vibid.live.service.LiveService;
import com.vibid.member.domain.Member;
import com.vibid.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ChatController {

  // 클라이언트에게 메세지 전송을 하기 위한 메서드들이 제공된 템플릿
  private final SimpMessagingTemplate template;

  // 채팅 비즈니스 로직 처리를 위한 ChatService
  private final ChatService chatService;

  // 사용자 닉네임을 얻어오기 위한 MemberService
  private final MemberService memberService;

  // 입력으로 들어온 id가 진짜 라이브 진행중인 방의 id 인지 검증을 위한 LiveService;
  private final LiveService liveService;

  /**
   * id는 Live session의 id를 가르킵니다. Live session의 id는 Board id를 사용할 수도 있고 아닐 수도 있습니다.
   * 이는 추가적으로 기획의 변동에 따라 달라질 수 있습니다.
   * @param id
   * @param requestChatDto
   */
  @MessageMapping("/chat/{id}")
  public void chatting(@DestinationVariable("id") Long id,
                       RequestChatDto requestChatDto) throws IOException {

    // 구독 URL
    StringBuilder destination = new StringBuilder();
    destination.append("/sub/chat/").append(id);

    // 사용자 검증을 위해 Member load
    Member member = memberService.findById(requestChatDto.getId())
      .orElseThrow(() -> new NotFoundException(Member.class, requestChatDto.getId()));

    if(liveService.isOpened(id) == false)
      throw new IllegalArgumentException();

//    if(liveService.isContain(id,member.getId()))
//      throw new IllegalArgumentException();

    String fillteredText = chatService.filtering(requestChatDto.getContent());

    template.convertAndSend(destination.toString(),
      ApiResult.OK(ResponseChatDto.createResponse(member.getNickname(),fillteredText)));
  }

}
