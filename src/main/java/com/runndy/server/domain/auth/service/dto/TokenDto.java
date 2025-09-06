package com.runndy.server.domain.auth.service.dto;

import java.time.Duration;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {

  private String accessToken;
  private String refreshToken;
  private Duration ttl;

  public static TokenDto of(String accessToken, String refreshToken, Duration ttl) {
    return TokenDto.builder()
                   .accessToken(accessToken)
                   .refreshToken(refreshToken)
                   .ttl(ttl)
                   .build();
  }
}
