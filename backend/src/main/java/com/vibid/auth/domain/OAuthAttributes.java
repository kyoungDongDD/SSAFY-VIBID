package com.vibid.auth.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                         String name, String email, String picture) {
   this.attributes = attributes;
   this.name = name;
   this.nameAttributeKey = nameAttributeKey;
   this.email = email;
   this.picture = picture;
  }

  public static OAuthAttributes of(String registedId, String userNameAttributeName,
                                   Map<String, Object> attributes) {
    if(registedId.equals("naver")) {
      return ofNaver("id",attributes);
    } else if (registedId.equals("kakao")) {
      return ofKakao("id",attributes);
    } else if (registedId.equals("facebook")) {
      return ofFacebook("id",attributes);
    } else {
      return ofGoogle(userNameAttributeName, attributes);
    }
  }

  private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                          Map<String, Object> attributes) {
    return OAuthAttributes.builder()
      .name((String) attributes.get("name"))
      .email((String) attributes.get("email"))
      .picture((String) attributes.get("picture"))
      .attributes(attributes)
      .nameAttributeKey(userNameAttributeName)
      .build();
  }

  private static OAuthAttributes ofNaver(String userNameAttributeName,
                                         Map<String, Object> attributes) {

    Map<String, Object> response = (Map<String, Object>) attributes.get("response");
    return OAuthAttributes.builder()
      .name((String) response.get("nickname"))
      .email((String) response.get("email"))
      .picture((String) response.get("profile_image"))
      .attributes(response)
      .nameAttributeKey(userNameAttributeName)
      .build();
  }

  private static OAuthAttributes ofKakao(String userNameAttributeName,
                                         Map<String, Object> attributes) {

    Map<String, Object> accounts = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
    return OAuthAttributes.builder()
      .name((String) properties.get("nickname"))
      .email((String) accounts.get("email"))
      .picture((String) properties.get("thumbnail_image"))
      .attributes(attributes)
      .nameAttributeKey(userNameAttributeName)
      .build();
  }

  private static OAuthAttributes ofFacebook(String userNameAttributeName,
                                         Map<String, Object> attributes) {

    return OAuthAttributes.builder()
      .name((String) attributes.get("name"))
      .email((String) attributes.get("email"))
      .picture((String) attributes.get("imageUrl"))
      .attributes(attributes)
      .nameAttributeKey(userNameAttributeName)
      .build();
  }
}
