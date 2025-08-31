package com.runndy.server.domain.record.entity;

import com.runndy.server.domain.cmn.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_record",
            columnNames = {"usr_id", "crs_id"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record extends BaseEntity {

  @Column(name = "crs_id", nullable = false)
  protected Long courseId; // 코스 id

  @Column(name = "usr_id", nullable = false)
  protected Long userId; // 유저 id

  @Column(name = "rec_nm", nullable = false)
  protected String recordName; // 기록 이름

  @Column(name = "strt_tm")
  protected Timestamp startTime; // 시작 시간

  @Column(name = "end_tm")
  protected Timestamp endTime; // 종료 시간

  @Column(name = "tot_tm")
  protected Long totalTime; // 총 시간(sec)

  @Column(name = "tot_dist")
  protected BigDecimal totalDistance; // 총 거리(m)

  @Column(name = "tot_ele")
  protected BigDecimal totalElevation;  // 총 고도(m)

  @Column(name = "avg_pace")
  protected BigDecimal averagePace; // 평균 페이스(sec/km)

}
