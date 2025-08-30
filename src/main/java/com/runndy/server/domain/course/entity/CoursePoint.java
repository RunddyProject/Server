package com.runndy.server.domain.course.entity;

import com.runndy.server.domain.cmn.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
    name = "course_point",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_course_point",
            columnNames = {"crs_id", "pt_seq"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoursePoint extends BaseEntity {

  @Column(name="crs_id", nullable = false)
  protected Long crsId; // 코스 id

  @Column(name="pt_seq", nullable = false)
  protected Long ptSeq; // 포인트 순서

  @Column(nullable = false)
  protected BigDecimal lat; // 위도

  @Column(nullable = false)
  protected BigDecimal lon; // 경도

  @Column
  protected BigDecimal ele; // 고도
}
