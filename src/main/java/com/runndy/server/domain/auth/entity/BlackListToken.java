package com.runndy.server.domain.auth.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "blacklist", timeToLive = 1000)
public class BlackListToken {

  @Id
  private String token;

  public static BlackListToken of(String token) {
    return BlackListToken.builder()
                         .token(token)
                         .build();
  }
}
