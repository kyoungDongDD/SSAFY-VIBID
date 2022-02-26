package com.vibid.bid.repository;

import com.vibid.bid.domain.Trade;
import com.vibid.bid.domain.TradeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TradeRepositoryImpl implements TradeRepository {

  private final EntityManager entityManager;

  @Override
  public Trade save(Trade trade) {
    entityManager.persist(trade);
    return trade;
  }

  @Override
  public List<Trade> findAllByBoardId(Long boardId) {
    String query = "SELECT t FROM Trade t where t.board.id=:boardId";
    return entityManager.createQuery(query, Trade.class)
      .setParameter("boardId",boardId)
      .getResultList();
  }

  @Override
  public Optional<Trade> findByBoardId(Long boardId, TradeType type) {
    String query = "SELECT t FROM Trade t where t.board.id=:boardId and t.tradeType = :type";
    List<Trade> results = entityManager.createQuery(query, Trade.class)
      .setParameter("boardId",boardId)
      .setParameter("type",type)
      .getResultList();
    return Optional.ofNullable(results.isEmpty()?null:results.get(0));
  }

  @Override
  public List<Trade> findAllByMemberId(Long memberId) {
    String query = "SELECT t FROM Trade t WHERE t.member.id = : memberId";
    return entityManager.createQuery(query, Trade.class)
      .setParameter("memberId", memberId)
      .getResultList();
  }

  @Override
  public List<Trade> findAllByMemberIdAndType(Long memberId, TradeType type) {
    String query = "SELECT t FROM Trade t WHERE t.member.id = : memberId AND t.tradeType = :type";
    return entityManager.createQuery(query, Trade.class)
      .setParameter("memberId", memberId)
      .setParameter("type",type)
      .getResultList();
  }
}
