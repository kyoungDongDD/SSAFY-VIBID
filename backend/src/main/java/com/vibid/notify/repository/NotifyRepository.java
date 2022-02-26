package com.vibid.notify.repository;

import com.vibid.notify.domain.Notify;

import java.util.List;
import java.util.Optional;

public interface NotifyRepository {

  Notify save(Notify notify);

  Optional<Notify> findById(Long notifyId);

  List<Notify> findAllById(Long memberId);

  void delete(Notify notify);

  void deleteAllById(Long memberId);
}
