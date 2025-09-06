package com.runndy.server.security.jwt;

import java.time.Duration;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenStore {

  private final StringRedisTemplate redis;

  // 현재 세션의 유효 리프레시: rt:{sub}:{sid}
  private String rtKey(String sub, String sid) {
    return "rt:%s:%s".formatted(sub, sid);
  }

  // 블랙리스트된 액세스 토큰: bl:acc:{jti} -> 블랙 리스트 시 "1"
  private String blKey(String jti) {
    return "bl:acc:%s".formatted(jti);
  }

  // 사용자 보유 세션 목록: sids:{sub}
  private String sidsKey(String sub) {
    return "sids:%s".formatted(sub);
  }


  // 최초 로그인: 세션 등록 + 리프레시 jti 기록
  public void registerSession(String sub, String sid, String jti, Duration ttl) {
    String key = rtKey(sub, sid);
    redis.opsForValue().set(key, jti, ttl);           // rt:{sub}:{sid} = jti
    redis.opsForSet().add(sidsKey(sub), sid);         // sids:{sub} += sid
  }

  // 현재 세션의 jti가 맞는지 확인 (재사용 공격 방지)
  public boolean isCurrentRefresh(String sub, String sid, String jti) {
    String current = redis.opsForValue().get(rtKey(sub, sid));
    return current != null && current.equals(jti);
  }

  // 리프레시 회전(rotate): 새로운 jti로 교체(원자적)
  public void rotateRefresh(String sub, String sid, String newJti, Duration newTtl) {
    String key = rtKey(sub, sid);
    redis.opsForValue().set(key, newJti, newTtl);
  }

  // 세션 제거(로그아웃)
  public void revokeSession(String sub, String sid) {
    redis.delete(rtKey(sub, sid));
    redis.opsForSet().remove(sidsKey(sub), sid);
  }

  // 사용자 모든 세션 해지(로그아웃 올)
  public void revokeAllSessions(String sub) {
    Set<String> sids = redis.opsForSet().members(sidsKey(sub));
    if (sids != null) {
      sids.forEach(sid -> redis.delete(rtKey(sub, sid)));
      redis.delete(sidsKey(sub));
    }
  }

  // 액세스 토큰 블랙리스트
  public void blacklistAccess(String jti, Duration ttl) {
    redis.opsForValue().set(blKey(jti), "1", ttl);
  }

  public boolean isAccessBlacklisted(String jti) {
    return Boolean.TRUE.equals(redis.hasKey(blKey(jti)));
  }
}

