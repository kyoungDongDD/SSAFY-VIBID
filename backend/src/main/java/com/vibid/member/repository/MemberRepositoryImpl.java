package com.vibid.member.repository;

import com.vibid.member.domain.Email;
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
public class MemberRepositoryImpl implements MemberRepository {

  private final EntityManager entityManager;


  @Override
  public Member save(Member member) {
    entityManager.persist(member);
    return member;
  }

  @Override
  public Optional<Member> findById(Long id) {
    String query = "SELECT m FROM Member m WHERE m.id = :id";
    List<Member> results = entityManager.createQuery(query, Member.class)
      .setParameter("id",id)
      .getResultList();
    return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
  }

  @Override
  public Optional<Member>  findByEmail(Email email) {
    String query = "select m from Member m where m.principal = :email";
    List<Member> result = entityManager.createQuery(query, Member.class)
      .setParameter("email", email)
      .getResultList();
    return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
  }

  @Override
  public Optional<Member> findByEmailAndVendor(Email email, String vendor) {
    String query = "select m from Member m where m.principal = :email and m.vendor = :vendor";
    List<Member> result = entityManager.createQuery(query, Member.class)
      .setParameter("email", email)
      .setParameter("vendor",vendor)
      .getResultList();
    return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
  }
}
