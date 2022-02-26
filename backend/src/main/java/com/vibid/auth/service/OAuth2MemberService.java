package com.vibid.auth.service;

import com.vibid.auth.domain.Authority;
import com.vibid.auth.domain.OAuthAttributes;
import com.vibid.auth.domain.Role;
import com.vibid.member.domain.Email;
import com.vibid.member.domain.Member;
import com.vibid.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final MemberRepository memberRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();


    OAuthAttributes attributes = OAuthAttributes.of(registrationId,
      userNameAttributeName,
      oAuth2User.getAttributes());

    saveOrUpdate(registrationId,attributes);

    return new DefaultOAuth2User(
      Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
      attributes.getAttributes(),
      attributes.getNameAttributeKey());
  }

  private Member saveOrUpdate(String registrationId, OAuthAttributes attributes) {
    Member member = memberRepository.findByEmailAndVendor(Email.from( attributes.getEmail()), registrationId)
      .map(entity -> entity.update(attributes.getEmail(), attributes.getPicture()))
      .orElseGet(() -> {
        Member entity = Member.of(
          registrationId,
          Email.from(attributes.getEmail()),
          attributes.getName(),
          attributes.getPicture());
        Authority authority = Authority.from(Role.USER);
        authority.setMember(entity);
        return memberRepository.save(entity);
      });

    return member;
  }
}
