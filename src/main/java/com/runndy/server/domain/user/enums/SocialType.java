package com.runndy.server.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
  NAVER("NAVER"),
  KAKAO("KAKAO")
  ;

  private final String label;

  @Override
  public String toString() {
    return this.label;
  }
}
