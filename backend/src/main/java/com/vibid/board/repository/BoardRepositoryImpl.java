package com.vibid.board.repository;

import com.vibid.board.domain.Board;
import com.vibid.board.controller.dto.OrderType;
import com.vibid.board.controller.dto.SortType;
import com.vibid.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

  private final EntityManager entityManager;

  @Override
  public Board save(Board board) {
    entityManager.persist(board);
    return board;
  }

  @Override
  public List<Board> findAllByMemberId(Long memberId, int offset, int limit) {
    String query = "SELECT b FROM Board b where b.member.id = :id";
    return entityManager.createQuery(query, Board.class)
      .setFirstResult(offset)
      .setMaxResults(limit)
      .setParameter("id", memberId)
      .getResultList();
  }

  @Override
  public List<Board> findWishAllByMember(Member member, int offset, int limit) {
    String query = "SELECT b FROM Board b " +
      "join b.wishes w " +
      "where w.member = :member " +
      "order by b.registDate DESC";

    return entityManager.createQuery(query, Board.class)
      .setParameter("member",member)
      .setFirstResult(offset)
      .setMaxResults(limit)
      .getResultList();

  }

  @Override
  public List<Board> findBuyAllByMember(Member member) {
    String query = "SELECT b FROM Board b " +
      "join b.wishes w " +
      "where w.member = :member";

    return entityManager.createQuery(query, Board.class)
      .setParameter("member",member)
      .setMaxResults(10)
      .getResultList();
  }

  @Override
  public List<Board> findSellAllByMember(Member member) {
    String query = "SELECT b FROM Board b " +
      "join b.wishes w " +
      "where w.member = :member";

    return entityManager.createQuery(query, Board.class)
      .setParameter("member",member)
      .setMaxResults(10)
      .getResultList();
  }

  @Override
  public Optional<Board> findById(Long id) {
    String query = "select b from Board b where b.id=:id";
    List<Board> boards = entityManager.createQuery(query, Board.class)
      .setParameter("id", id)
      .getResultList();
    return Optional.ofNullable(boards.isEmpty() ? null : boards.get(0));
  }

  @Override
  public void delete(Board board) {
    entityManager.remove(board);
  }

  @Override
  public List<Board> findAllByCondition(List<String> tags, boolean isLive, SortType sort, OrderType order, int offset, int limit) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT DISTINCT b FROM Board b");
    AtomicBoolean isFirst = new AtomicBoolean(true);
    tags.forEach(tag -> {
      if(isFirst.get()) {
        query.append(" join b.boardHashtags bh");
        query.append(" join bh.hashtag h");
        query.append(" where");
        if(isLive)
          query.append(" (");
        isFirst.set(false);
      }
      else
        query.append(" or");
      query.append(" h.name like").append(" '%").append(tag).append("%'");
    });

    if(isLive) {
      if(isFirst.get()) {
        query.append(" where");
        isFirst.set(false);
      } else {
        query.append(") and");
      }
      query.append(" b.isLive = true");
    }


    query.append(" ORDER BY");
    switch (sort) {
      case WISH:
        query.append(" b.wishCount");
        break;
      case DATE:
        query.append(" b.registDate");
        break;
      case VIEW:
        query.append(" b.viewCount");
        break;
    }

    switch (order) {
      case ASC:
        query.append(" ASC");
        break;
      case DESC:
        query.append(" DESC");
        break;
    }

    return entityManager.createQuery(query.toString(), Board.class)
      .setFirstResult(offset)
      .setMaxResults(limit)
      .getResultList();
  }

}