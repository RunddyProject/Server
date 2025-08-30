package com.runndy.server.domain.course.entity;

import com.runndy.server.domain.cmn.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
  protected String crsTyp; // 코스 유형 (CourseType)

  @Column
  protected String crsEnvTyp; // 코스 환경 정보 (CourseEnvType)

  @Column
  protected String crsShpTyp; // 코스 모양 (CourseShapeType)

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
  protected Long rcmCnt; // 추천수
}

