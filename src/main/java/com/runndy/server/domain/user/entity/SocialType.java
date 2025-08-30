package com.runndy.server.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
  NAVER("네이버"),
  KAKAO("카카오");

  private final String label;
}
