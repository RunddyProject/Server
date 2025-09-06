package com.runndy.server.domain.auth.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAccessTokenResponseDto {

  private String accessToken;

  public static CreateAccessTokenResponseDto of(String accessToken) {
    return CreateAccessTokenResponseDto.builder()
                                       .accessToken(accessToken)
                                       .build();
  }
}
