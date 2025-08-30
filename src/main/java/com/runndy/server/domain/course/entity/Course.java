package com.runndy.server.domain.course.entity;

import com.runndy.server.domain.cmn.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseEntity {

  @Column(nullable = false)
  protected String crsNm; // 코스 이름

  @Column(nullable = false)
  protected CourseType crsTyp; // 코스 유형 (공식/비공식)

  @Column
  @Enumerated(value = EnumType.STRING)
  protected CourseEnvType crsEnvTyp; // 코스 환경 정보

  @Column
  @Enumerated(value = EnumType.STRING)
  protected CourseShapeType crsShpTyp; // 코스 모양

  @Column
  protected Integer crsGrd; // 코스 난이도

  @Column(nullable = false)
  protected String strtAddr; // 시작 주소

  @Column(nullable = false)
  protected String endAddr; // 종료 주소

  @Column
  protected BigDecimal totDist; // 총 거리(m)

  @Column
  protected BigDecimal totEle; // 총 고저차(m)

  @Column
  protected Long recomCnt; // 추천수
}

