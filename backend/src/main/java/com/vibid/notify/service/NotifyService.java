package com.vibid.notify.service;

import com.vibid.board.domain.Board;
import com.vibid.member.domain.Member;
import com.vibid.notify.domain.Notify;

import java.util.List;
import java.util.Optional;

public interface NotifyService {

  Notify save(Long memberId, Long boardId, String content);

  Notify save(Member member, Board board, String content);

  Optional<Notify> read(Long notifyId);

  Boolean delete(Long notifyId);

  void deleteAllByMemberId(Long memberId);

  List<Notify> findAllByMemberId(Long memberId);
}
