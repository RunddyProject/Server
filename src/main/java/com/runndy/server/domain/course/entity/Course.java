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

  @Column(name = "crs_nm", nullable = false)
  protected String courseName; // 코스 이름

  @Column(name = "crs_typ", nullable = false)
  protected String courseType; // 코스 유형 (CourseType)

  @Column(name = "crs_env_typ")
  protected String courseEnvType; // 코스 환경 정보 (CourseEnvType)

  @Column(name = "crs_shp_typ")
  protected String courseShapeType; // 코스 모양 (CourseShapeType)

  @Column(name = "crs_grd")
  protected Integer courseGrade; // 코스 난이도

  @Column(name = "strt_addr", nullable = false)
  protected String startAddress; // 시작 주소

  @Column(name = "end_addr", nullable = false)
  protected String endAddress; // 종료 주소

  @Column(name = "tot_dist")
  protected BigDecimal totalDistance; // 총 거리(m)

  @Column(name = "tot_ele")
  protected BigDecimal totalElevation; // 총 고저차(m)

  @Column(name = "rcm_cnt")
  protected Long recommendCount; // 추천수
}

