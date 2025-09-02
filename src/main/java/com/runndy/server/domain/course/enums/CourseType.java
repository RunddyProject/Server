package com.runndy.server.domain.course.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseType {
  OFFICIAL("공식"),
  UNOFFICIAL("비공식"),
  ETC("기타");

  private final String label;
}
