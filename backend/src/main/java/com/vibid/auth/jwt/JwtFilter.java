package com.vibid.auth.jwt;

import com.vibid.auth.jwt.domain.Jwt;
import com.vibid.auth.jwt.domain.JwtAuthentication;
import com.vibid.auth.jwt.domain.JwtAuthenticationToken;
import com.vibid.auth.jwt.domain.JwtTokenConfig;
import com.vibid.member.domain.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

  private final Jwt jwt;
  private final String HEADER;

  @Override
  // 실제 필터링 로직 : 토큰의 인증 정보를 Security context에 저장
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    String jwtToken = resolveToekn(httpServletRequest);
    String requestURI = httpServletRequest.getRequestURI();

    // 토큰 유효성 검증
    if(SecurityContextHolder.getContext().getAuthentication() == null) {
      if (StringUtils.hasText(jwtToken)) {
        Jwt.Claims claims = verify(jwtToken);
        log.debug("Jwt parse result : {}",claims);

        // 만료 10분 전
        if(canRefresh(claims, 600L)) {
          String refreshToken = jwt.refreshToken(jwtToken);
          ((HttpServletResponse)servletResponse).setHeader(HEADER,refreshToken);
        }

        Long id = claims.getId();
        Email principal = claims.getEmail();
        String nickname = claims.getNickname();
        List<GrantedAuthority> authorities = obtainAuthorities(claims);
        JwtAuthenticationToken authentication =
          new JwtAuthenticationToken(authorities, JwtAuthentication.of(id,principal,nickname), null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) servletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri : {}",
          authentication.getName(), requestURI);
      } else {
        log.debug("유효한 JWT 토큰이 없습니다. uri : {}", requestURI);
      }
    } else {
      log.debug("SecurityContextHolder not populated with security token, as it already contained: '{}'",
      SecurityContextHolder.getContext().getAuthentication());
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  private boolean canRefresh(Jwt.Claims claims, long seconds) {
    long exp = claims.exp();
    if(exp > 0) {
      long remain = exp - System.currentTimeMillis();
      return remain < seconds;
    }
    return false;
  }

  private Jwt.Claims verify(String jwtToken) {
    return jwt.verify(jwtToken);
  }

  // Request Header에서 토큰 정보를 꺼내오는 메서드
  private String resolveToekn(HttpServletRequest request) {
    String bearerToken = request.getHeader(HEADER);
    if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  private List<GrantedAuthority> obtainAuthorities(Jwt.Claims claims) {
    String[] roles = claims.getRoles();
    return roles == null || roles.length == 0 ?
      Collections.emptyList() :
      Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(toList());
  }
}
