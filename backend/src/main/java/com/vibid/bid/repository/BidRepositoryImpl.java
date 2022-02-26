package com.vibid.bid.repository;

import com.vibid.bid.domain.Bid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BidRepositoryImpl implements BidRepository {

  private final EntityManager entityManager;

  @Override
  public List<Bid> findByIdMaxPrice(Long boardId) {
    String query = "select b from Bid b where b.board.id = :boardId ORDER BY b.price desc";
    List<Bid> results = entityManager.createQuery(query, Bid.class)
      .setParameter("boardId",boardId)
      .setMaxResults(1)
      .getResultList();
    return results;
  }

  @Override
  public Bid save(Bid bid) {
    entityManager.persist(bid);
    return bid;
  }

  @Override
  public List<Bid> findByBoardId(Long boardId) {
    String query = "select b from Bid b where b.board.id = :boardId";
     return entityManager.createQuery(query, Bid.class)
       .setParameter("boardId",boardId)
      .getResultList();
  }

  @Override
  public List<Bid> findByMemberId(Long memberId) {
    String query = "select b from Bid b where b.member.id = :memberId";
    return entityManager.createQuery(query, Bid.class)
      .setParameter("memberId",memberId)
      .getResultList();
  }

  @Override
  public List<Bid> findAll() {
    String query = "select b from Bid b";
    return entityManager.createQuery(query, Bid.class)
      .getResultList();
  }
}
