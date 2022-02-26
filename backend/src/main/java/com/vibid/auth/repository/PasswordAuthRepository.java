package com.vibid.auth.repository;

import com.vibid.auth.domain.EmailAuthToken;
import com.vibid.auth.domain.PasswordAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PasswordAuthRepository {
  private final EntityManager entityManager;

  public PasswordAuthToken save(PasswordAuthToken passwordAuthToken) {
    entityManager.persist(passwordAuthToken);
    return passwordAuthToken;
  }

  public Optional<PasswordAuthToken> findByToken(String token) {
    log.debug("PasswordAuthRepository.findByToken : {}",token);
    PasswordAuthToken result = entityManager.find(PasswordAuthToken.class, token);
    return Optional.ofNullable(result);
  }

  public void delete(PasswordAuthToken token) {
    entityManager.remove(token);
  }

}
