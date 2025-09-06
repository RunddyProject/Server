package com.runndy.server.domain.auth.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 7)
public class RefreshToken {

  private Long userId;
  @Id
  private String refreshToken;

  public static RefreshToken of(Long userId, String refreshToken) {
    return RefreshToken.builder()
                       .userId(userId)
                       .refreshToken(refreshToken)
                       .build();
  }
}
