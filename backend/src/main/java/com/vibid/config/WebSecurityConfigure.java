package com.vibid.config;

import com.vibid.auth.domain.Role;
import com.vibid.auth.jwt.JwtAcessDeniedHandler;
import com.vibid.auth.jwt.JwtAuthenticationEntryPoint;
import com.vibid.auth.jwt.JwtFilter;
import com.vibid.auth.jwt.domain.Jwt;
import com.vibid.auth.jwt.domain.JwtTokenConfig;
import com.vibid.auth.service.OAuth2MemberService;
import com.vibid.config.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

  private final Jwt jwt;

  private final JwtTokenConfig jwtTokenConfig;

  private final JwtAcessDeniedHandler jwtAcessDeniedHandler;

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  private final OAuth2MemberService oAuth2MemberService;

  private final OAuth2SuccessHandler oAuth2SuccessHandler;

  @Bean
  public JwtFilter jwtFilter() {
    return new JwtFilter(jwt, jwtTokenConfig.getHeader());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
      .antMatchers(
        "/h2-console/**",
        "/favicon/ico",
        "/static/**"
      );
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf()
      .disable()
      .headers()
      .disable()

      .exceptionHandling()
      .accessDeniedHandler(jwtAcessDeniedHandler)
      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
      .and()

      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()

      .authorizeRequests()
      .requestMatchers(request -> CorsUtils.isPreFlightRequest(request)).permitAll()
      .antMatchers("/api/board/main").permitAll()
      .antMatchers("/api/auth/**").permitAll()
      .antMatchers("/api/user/changePassword").permitAll()
      .antMatchers("/api/user").permitAll()
      .antMatchers("/api/user/exist").permitAll()
      .antMatchers("/api/image/**").permitAll()
      .antMatchers("/api/**").authenticated()
      .anyRequest().permitAll()
      .and()

      .formLogin()
      .disable()

      .oauth2Login()      // Oauth2 로그인 기능에 대한 여러 설정의 진입점
      .userInfoEndpoint() // Oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정
      .userService(oAuth2MemberService)     // 소셜 로그인 성공 시 후속 조치를 진행할 userService 인터페이스 구현체 등록
      .and()                                           // 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시 가능

      .successHandler(oAuth2SuccessHandler);

    http
      .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
