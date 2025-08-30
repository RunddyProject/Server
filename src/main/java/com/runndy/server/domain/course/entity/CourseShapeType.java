package com.runndy.server.domain.course.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseShapeType {
  LOOP("순환형"),
  OUT_AND_BACK("왕복형"),
  POINT_TO_POINT("직선형"),
  ART("예술형"),
  ETC("기타")
  ;

  private final String label;
}
