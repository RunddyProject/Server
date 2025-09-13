package com.runndy.server.domain.user.service.dto;

import com.runndy.server.domain.user.enums.SocialType;
import com.runndy.server.domain.user.enums.UserType;
import com.runndy.server.domain.user.repository.dto.response.SelectLoginUserResult;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserInfoDto {

  private String email;
  private String userName;
  private SocialType socialType;
  private String socialId;
  private UserType userType;

  public static LoginUserInfoDto of(SocialType socialType, String socialId) {
    return LoginUserInfoDto.builder()
                           .socialType(socialType)
                           .socialId(socialId)
                           .build();
  }

  public static LoginUserInfoDto of(String email, String userName, SocialType socialType,
      String socialId) {
    return LoginUserInfoDto.builder()
                           .email(email)
                           .userName(userName)
                           .socialType(socialType)
                           .socialId(socialId)
                           .build();
  }

  public static LoginUserInfoDto from(SelectLoginUserResult responseDto) {
    return LoginUserInfoDto.builder()
                           .email(responseDto.getEmail())
                           .userName(responseDto.getUserName())
                           .socialType(SocialType.valueOf(responseDto.getSocialType().toUpperCase()))
                           .socialId(responseDto.getSocialId())
                           .userType(UserType.valueOf(responseDto.getUserType().toUpperCase()))
                           .build();
  }
}
