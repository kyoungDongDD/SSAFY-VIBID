package com.vibid.notify.repository;

import com.vibid.notify.domain.Notify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotifyRepositoryImpl implements NotifyRepository {

  private final EntityManager entityManager;

  @Override
  public Notify save(Notify notify) {
    entityManager.persist(notify);
    return notify;
  }

  @Override
  public Optional<Notify> findById(Long notifyId) {
    return Optional.ofNullable(entityManager.find(Notify.class, notifyId));
  }

  @Override
  public List<Notify> findAllById(Long memberId) {
    String query = "SELECT n FROM Notify n WHERE n.member.id = :id ORDER BY n.registDate DESC";
    return entityManager.createQuery(query, Notify.class)
      .setParameter("id",memberId)
      .setMaxResults(10)
      .getResultList();
  }

  @Override
  public void delete(Notify notify) {
    entityManager.remove(notify);
  }

  @Override
  public void deleteAllById(Long memberId) {
    String query = "DELETE FROM Notify n where n.member.id = :id ";
    entityManager.createQuery(query)
      .setParameter("id",memberId);
  }
}
