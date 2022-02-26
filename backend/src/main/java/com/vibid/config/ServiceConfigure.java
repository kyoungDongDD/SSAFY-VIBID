package com.vibid.config;

import com.vibid.auth.jwt.domain.Jwt;
import com.vibid.auth.jwt.domain.JwtTokenConfig;
import com.vibid.utils.MessageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

@Configuration
public class ServiceConfigure {
  
  @Bean
  public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
    MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource);
    MessageUtils.setMessageSourceAccessor(messageSourceAccessor);
    return messageSourceAccessor;
  }

  @Bean
  public Jwt jwt(JwtTokenConfig jwtTokenConfig) {
    return new Jwt(jwtTokenConfig);
  }



}
