package com.runndy.server.domain.user.repository.dto.request;

import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectLoginUserQuery {

  private String email;
  private String socialType;
  private String socialId;

  public static SelectLoginUserQuery from(LoginUserInfoDto loginUserInfoDto) {
    return SelectLoginUserQuery.builder()
                               .email(loginUserInfoDto.getEmail())
                               .socialType(loginUserInfoDto.getSocialType().toString())
                               .socialId(loginUserInfoDto.getSocialId())
                               .build();
  }
}
