package com.runndy.server.security.oauth;

import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  @Override
  public OAuth2User loadUser(OAuth2UserRequest req) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(req);

    String regId = req.getClientRegistration().getRegistrationId(); // google or kakao
    Map<String, Object> attrs = oAuth2User.getAttributes();

    String providerId = null;
    String email = null;
    String name = null;

    if ("google".equals(regId)) {

      providerId = (String) attrs.get("sub");
      email = (String) attrs.get("email");
      name = (String) attrs.get("name");

    } else if ("kakao".equals(regId)) { // kakao

      providerId = String.valueOf(attrs.get("id"));
      Map<String, Object> account = (Map<String, Object>) attrs.get("kakao_account");
      Map<String, Object> profile =
          account != null ? (Map<String, Object>) account.get("profile") : null;

      email = account != null ? (String) account.get("email") : null;
      name = profile != null ? (String) profile.get("nickname") : ("kakao_" + providerId);

    }

    List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_USER"));

    return new UserPrincipal(null, regId, providerId, email, name, roles);
  }
}
