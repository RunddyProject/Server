package com.runndy.server.security.jwt;

import java.time.Duration;
import java.util.Set;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenStore {
  private final StringRedisTemplate redis;

  public TokenStore(StringRedisTemplate redis) { this.redis = redis; }

  public void blacklist(String jti, Duration ttl) {
    redis.opsForValue().set("blacklist:" + jti, "1", ttl);
  }

  public boolean isBlacklisted(String jti) {
    return Boolean.TRUE.equals(redis.hasKey("blacklist:" + jti));
  }

  public void saveRefresh(String subject, String jti, Duration ttl) {
    redis.opsForValue().set("refresh:" + subject + ":" + jti, "1", ttl);
  }

  public boolean existsRefresh(String subject, String jti) {
    return Boolean.TRUE.equals(redis.hasKey("refresh:" + subject + ":" + jti));
  }

  public void revokeAllRefresh(String subject) {
    // 데모에선 간단히 패턴 스캔
    Set<String> keys = redis.keys("refresh:" + subject + ":*");
    if (keys != null && !keys.isEmpty()) redis.delete(keys);
  }
}

