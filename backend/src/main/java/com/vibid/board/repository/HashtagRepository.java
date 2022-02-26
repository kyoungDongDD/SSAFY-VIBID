package com.vibid.board.repository;

import com.vibid.board.domain.Hashtag;

import java.util.Optional;

public interface HashtagRepository {

  Optional<Hashtag> findByName(String name);

  Hashtag save(Hashtag hashtag);
}
