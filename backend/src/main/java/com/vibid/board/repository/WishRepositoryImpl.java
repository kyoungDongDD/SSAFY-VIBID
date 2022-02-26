package com.vibid.board.repository;

import com.vibid.board.domain.Board;
import com.vibid.board.domain.Wish;
import com.vibid.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class WishRepositoryImpl implements WishRepository {

  private final EntityManager entityManager;

  @Override
  public Optional<Wish> findByBoardAndMember(Board board, Member member) {
    String query = "SELECT w FROM Wish w WHERE w.board = :board and w.member = :member";
    List<Wish> results = entityManager.createQuery(query, Wish.class)
      .setParameter("board",board)
      .setParameter("member",member)
      .getResultList();
    return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
  }

  @Override
  public void delete(Wish wish) {
    entityManager.remove(wish);
  }

  @Override
  public Wish save(Wish wish) {
    entityManager.persist(wish);
    return wish;
  }

  // TODO
  @Override
  public List<Member> findAllMemberByBoard(Board board) {
    String query = "SELECT m FROM Wish w INNER JOIN w.member m WHERE w.board = :board";
    return entityManager.createQuery(query, Member.class)
      .setParameter("board",board)
      .getResultList();
  }
}
