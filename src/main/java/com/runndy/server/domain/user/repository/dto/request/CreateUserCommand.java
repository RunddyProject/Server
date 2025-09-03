package com.runndy.server.domain.user.repository.dto.request;

import com.runndy.server.domain.user.enums.UserType;
import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserCommand {

  private String email;
  private String userName;
  private String socialType;
  private String socialId;
  private String userType;

  public static CreateUserCommand from(LoginUserInfoDto loginUserInfoDto) {
    return CreateUserCommand.builder()
                            .email(loginUserInfoDto.getEmail())
                            .userName(loginUserInfoDto.getUserName())
                            .socialType(loginUserInfoDto.getSocialType().toString())
                            .socialId(loginUserInfoDto.getSocialId())
                            .userType(UserType.USER.toString())
                            .build();
  }
}
