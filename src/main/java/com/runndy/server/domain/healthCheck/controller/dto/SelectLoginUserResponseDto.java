package com.runndy.server.domain.healthCheck.controller.dto;

import com.runndy.server.domain.user.enums.SocialType;
import com.runndy.server.domain.user.enums.UserType;
import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectLoginUserResponseDto {

  private String email;
  private String userName;
  private SocialType socialType;
  private String socialId;
  private UserType userType;

  public static SelectLoginUserResponseDto from(LoginUserInfoDto loginUserInfoDto) {
    return SelectLoginUserResponseDto.builder()
                                     .email(loginUserInfoDto.getEmail())
                                     .userName(loginUserInfoDto.getUserName())
                                     .socialType(loginUserInfoDto.getSocialType())
                                     .socialId(loginUserInfoDto.getSocialId())
                                     .userType(loginUserInfoDto.getUserType())
                                     .build();
  }
}