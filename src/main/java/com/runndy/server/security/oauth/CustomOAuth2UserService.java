package com.runndy.server.security.oauth;

import com.runndy.server.domain.user.entity.SocialType;
import com.runndy.server.domain.user.service.UserService;
import com.runndy.server.domain.user.service.dto.request.SelectLoginUserRequestDto;
import com.runndy.server.domain.user.service.dto.response.SelectLoginUserResponseDto;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserService userService;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest req) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(req);

    String regId = req.getClientRegistration().getRegistrationId(); // kakao, naver
    Map<String, Object> attrs = oAuth2User.getAttributes();

    String providerId = null;
    String email = null;
    String name = null;

    var socialType = SocialType.valueOf(regId.toUpperCase());

    switch (socialType) {
      case KAKAO -> {
        providerId = String.valueOf(attrs.get("id"));
        Map<String, Object> account = (Map<String, Object>) attrs.get("kakao_account");
        Map<String, Object> profile =
            account != null ? (Map<String, Object>) account.get("profile") : null;

        email = (String) account.get("email");
        name = profile != null ? (String) profile.get("nickname") : ("kakao_" + providerId);
      }
    }

    // 유저 정보 조회
    SelectLoginUserRequestDto userRequestDto = SelectLoginUserRequestDto.of(
        email, name, socialType.toString(), providerId
    );

    SelectLoginUserResponseDto userDto = userService.getOrCreateUser(userRequestDto);

    // TODO: 추후 관리자 분기처리
    return UserPrincipal.of(userDto);
  }
}
