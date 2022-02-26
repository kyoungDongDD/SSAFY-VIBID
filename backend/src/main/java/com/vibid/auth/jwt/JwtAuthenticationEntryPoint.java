package com.vibid.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibid.api.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
// 유효한 자격증명을 제공하지 않고 접근시도 하면 401 Unauthorized 리턴
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  static ApiResult<?> E401 = ApiResult.ERROR("Authentication error (cause: unauthorized)", HttpStatus.UNAUTHORIZED);

  private final ObjectMapper om;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setHeader("content-type", "application/json");
    response.getWriter().write(om.writeValueAsString(E401));
    response.getWriter().flush();
    response.getWriter().close();
  }
}
