package com.vibid.config.handler;

import com.vibid.auth.jwt.domain.Jwt;
import com.vibid.exception.NotFoundException;
import com.vibid.member.domain.Email;
import com.vibid.member.domain.Member;
import com.vibid.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final Jwt jwt;

  private final MemberRepository memberRepository;

  @Value("${vibid.login-redirect}")
  private String address;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    Map<String, Object> attributes = ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes();
    String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
    Email email = getEmail(registrationId,attributes);
    Member member = memberRepository.findByEmailAndVendor(email,registrationId)
      .orElseThrow(() -> new NotFoundException(Member.class, email, registrationId));
    String token = member.newApiToken(jwt,authentication);

    getRedirectStrategy().sendRedirect(request,response,
      address+"?jwtToken="+token);
  }

  private Email getEmail(String registrationId, Map<String, Object> attributes) {
    if(registrationId.equals("kakao")) {
      Map<String, Object> accounts = (Map<String, Object>) attributes.get("kakao_account");
      return Email.from((String) accounts.get("email"));
    } else {
      return Email.from((String) attributes.get("email"));
    }
  }


}
