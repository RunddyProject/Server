package com.runndy.server.domain.course.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseEnvType {
  PARK("공원"),
  TRAIL("산책로"),
  TRACK("트랙"),
  URBAN("도심"),
  BEACH("해변"),
  MOUNTAIN("산"),
  FOREST("숲"),
  ETC("기타")
  ;

  private final String label;

}
