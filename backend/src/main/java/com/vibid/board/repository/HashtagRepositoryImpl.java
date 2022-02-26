package com.vibid.board.repository;

import com.vibid.board.domain.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagRepository {

  private final EntityManager entityManager;

  @Override
  public Optional<Hashtag> findByName(String name) {
    String query = "SELECT h FROM Hashtag h WHERE h.name = :name";
    List<Hashtag> results = entityManager.createQuery(query, Hashtag.class)
      .setParameter("name", name)
      .getResultList();

    return Optional.ofNullable(results.isEmpty()?null : results.get(0));
  }

  @Override
  public Hashtag save(Hashtag hashtag) {
    entityManager.persist(hashtag);
    return hashtag;
  }
}
