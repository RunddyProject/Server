package com.runndy.server.domain.course.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FacilityType {
  PARKING_LOT("주차장"),
  RESTROOM("화장실"),
  WATER_FOUNTAIN("음수대"),
  SHOWER("샤워시설"),
  FIRST_AID("응급처치시설"),
  TRAIL_MARKERS("트레일 표지판"),
  TRASH_BINS("쓰레기통"),
  CONVENIENCE_STORE("매점"),
  ETC("기타");

  private final String label;
}
