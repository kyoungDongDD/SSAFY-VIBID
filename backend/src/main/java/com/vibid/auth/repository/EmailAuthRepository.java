package com.vibid.auth.repository;

import com.vibid.auth.domain.EmailAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EmailAuthRepository {

  private final EntityManager entityManager;

  public EmailAuthToken save(EmailAuthToken emailAuthToken) {
    entityManager.persist(emailAuthToken);
    return emailAuthToken;
  }

  public Optional<EmailAuthToken> findByToken(String token) {
    log.debug("EmailAuthRepository.findByToken : {}",token);
    EmailAuthToken result = entityManager.find(EmailAuthToken.class, token);
    return Optional.ofNullable(result);
  }

  public void delete(EmailAuthToken token) {
    entityManager.remove(token);
  }

}
