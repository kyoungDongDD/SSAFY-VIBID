package com.vibid.live.service;

import com.vibid.board.controller.dto.OrderType;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

import java.util.List;

public interface LiveService {
  boolean isCreated(Long boardId);

  boolean isOpened(Long boardId);

  boolean liveOpen(Long memberId, Long boardId);

  boolean isContain(Long boardId, Long memberId);

  List<Long> popularityList(OrderType order, int limit);

  String joinLive(Long memberId, String nickname, Long boardId) throws OpenViduJavaClientException, OpenViduHttpException, IllegalAccessException;

  void closeLive(Long memberId, Long boardId, String token) throws OpenViduJavaClientException, OpenViduHttpException;
}
