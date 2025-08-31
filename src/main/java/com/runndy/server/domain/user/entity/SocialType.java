package com.runndy.server.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
  NAVER("naver"),
  KAKAO("kakao")
  ;

  private final String label;

  @Override
  public String toString() {
    return this.label;
  }
}
