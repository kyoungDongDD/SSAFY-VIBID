package com.vibid.auth.jwt;

import com.vibid.auth.domain.dto.AuthenticationRequest;
import com.vibid.auth.jwt.domain.*;
import com.vibid.exception.NotFoundException;
import com.vibid.member.domain.Email;
import com.vibid.member.domain.Member;
import com.vibid.member.domain.Password;
import com.vibid.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.ClassUtils.isAssignable;

@Component
@RequiredArgsConstructor
@Slf4j
// 토큰 생성, 유효성 검증 담당
public class JwtTokenProvider implements AuthenticationProvider {

  private final Jwt jwt;

  private final MemberService memberService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // Authentication에는 추가 기능이 많아서 principal, credentials만 전송
    JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
    return processUserAuthentication(new AuthenticationRequest(
      String.valueOf(authenticationToken.getPrincipal()),
      authenticationToken.getCredentials()));
  }

  /**
   * {@link org.springframework.security.authentication.ProviderManager#authenticate} 메소드에서 호출된다.
   *
   * true 를 리턴하면 현재 Provider 에서 인증 처리를 할 수 있음을 의미한다.
   * 본 Provider 에서는 {@link com.vibid.auth.jwt.domain.JwtAuthenticationToken} 타입의 {@link Authentication} 를 처리할 수 있다.
   */
  @Override
  public boolean supports(Class<?> authentication) {
    return isAssignable(JwtAuthenticationToken.class, authentication);
  }

  private Authentication processUserAuthentication(AuthenticationRequest request) {
    try {
      Member member = memberService.login(Email.from(request.getPrincipal()), Password.from(request.getCredentials()));
      JwtAuthenticationToken authentication =
        // 응답용 Authentication 인스턴스를 생성한다.
        // JwtAuthenticationToken.principal 부분에는 JwtAuthentication 인스턴스가 set 된다.
        // 로그인 완료 전 JwtAuthenticationToken.principal 부분은 Email 인스턴스가 set 되어 있었다.

        new JwtAuthenticationToken(member.getGrantedAuthorities(), JwtAuthentication.of(member.getId(), member.getPrincipal(), member.getNickname()), null);
      // JWT 값을 생성한다.
      // 권한은 ROLE_USER 를 부여한다.
      String apiToken = member.newApiToken(jwt,authentication);
      authentication.setDetails(AuthenticationResult.from(apiToken, member));
      return authentication;
    } catch (NotFoundException e) {
      throw new UsernameNotFoundException(e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new BadCredentialsException(e.getMessage());
    } catch (DataAccessException e) {
      throw new AuthenticationServiceException(e.getMessage(), e);
    }
  }
}
