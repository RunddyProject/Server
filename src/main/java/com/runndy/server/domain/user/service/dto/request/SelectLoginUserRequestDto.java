package com.runndy.server.domain.user.service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectLoginUserRequestDto {

  private String email;
  private String name;
  private String socialType;
  private String socialId;

  public static SelectLoginUserRequestDto of(String email, String name, String socialType,
      String socialId) {
    return SelectLoginUserRequestDto.builder()
                                    .email(email)
                                    .name(name)
                                    .socialType(socialType)
                                    .socialId(socialId)
                                    .build();
  }
}
