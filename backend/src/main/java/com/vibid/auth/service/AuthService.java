package com.vibid.auth.service;

import com.vibid.auth.domain.EmailAuthToken;
import com.vibid.auth.domain.PasswordAuthToken;
import com.vibid.auth.repository.EmailAuthRepository;
import com.vibid.auth.repository.PasswordAuthRepository;
import com.vibid.exception.NotFoundException;
import com.vibid.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final JavaMailSender javaMailSender;

  private final EmailAuthRepository emailAuthRepository;

  private final PasswordAuthRepository passwordAuthRepository;

  @Value("${vibid.address}")
  private String address;

  @Value("${spring.mail.emailExpiryDay}")
  private Long emailExprityDay;

  @Value("${spring.mail.passwordExpiryMintues}")
  private Long passwordExprityMinutes;

  @Value("${vibid.server}")
  private String serverAddress;

  public EmailAuthToken createEmailToken(Member member) {

    EmailAuthToken token = EmailAuthToken.of(emailExprityDay);
    token.setMember(member);
    emailAuthRepository.save(token);

    MimeMessagePreparator mail = createEmailAuthMessage(member.getPrincipal().toString(), token.getToken());

    sendAuthEmail(mail);

    return token;
  }

  public PasswordAuthToken createPasswordToken(Member member) {

    PasswordAuthToken token = PasswordAuthToken.of(passwordExprityMinutes);
    token.setMember(member);
    passwordAuthRepository.save(token);

    MimeMessagePreparator mail = createPasswordAuthMessage(member.getPrincipal().toString(), token.getToken());

    sendAuthEmail(mail);

    return token;
  }

  // javaMailSender 객체를 사용해 Async 방식으로 이메일을 전송한다.
  @Async
  public void sendAuthEmail(MimeMessagePreparator mimeMessagePreparator) {
    javaMailSender.send(mimeMessagePreparator);
  }

  private MimeMessagePreparator createEmailAuthMessage(String email, String token) {
    return new MimeMessagePreparator() {
      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setTo(email);
        message.setSubject("VIBID 회원 가입 인증");
        message.addInline("myLogo", new ClassPathResource("./imgs/logo.png"));
        message.setText("<p><a href=\""+serverAddress+"/api/auth/confirm-email?token=" + token + "\" target=\"_blank\"> 바로가기 </a></p>", true);
      }
    };
  }

  private MimeMessagePreparator createPasswordAuthMessage(String email, String token) {
    return new MimeMessagePreparator() {
      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setTo(email);
        message.setSubject("VIBID 비밀번호 변경");
        message.addInline("myLogo", new ClassPathResource("./imgs/logo.png"));
        message.setText("<div><h1> " +  token + "</h1></div>", true);
      }
    };
  }

  public boolean confirmEmail(String token) {
    EmailAuthToken result = emailAuthRepository.findByToken(token)
      .orElseThrow(
        () -> new NotFoundException(EmailAuthToken.class, token)
      );

    try {
      // TODO Custom exception
      if (result.getExpiration().isAfter(LocalDateTime.now()))
        result.getMember().emailAuthentication();
      else
        throw new IllegalArgumentException("만료된 토큰입니다.");
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    } finally {
      emailAuthRepository.delete(result);
    }
  }

  public Optional<Member> confirmPassword(String token) {
    PasswordAuthToken result = passwordAuthRepository.findByToken(token)
      .orElseThrow(
        () -> new NotFoundException(EmailAuthToken.class, token)
      );
    if (result.getExpiration().isBefore(LocalDateTime.now()))
      throw new IllegalArgumentException("만료된 토큰입니다.");

    passwordAuthRepository.delete(result);

    return Optional.ofNullable(result.getMember());

  }

}
