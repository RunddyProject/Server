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

  @Column(name="usr_id", nullable = false)
  protected Long usrId; // 유저 id

  @Column(name="crs_id", nullable = false)
  protected Long crsId; // 코스 id

  @Column
  protected String recNm; // 기록 이름

  @Column
  protected Timestamp strtTm; // 시작 시간

  @Column
  protected Timestamp endTm; // 종료 시간

  @Column
  protected Long totTm; // 총 시간(sec)

  @Column
  protected BigDecimal totDist; // 총 거리(m)

  @Column
  protected BigDecimal totEle;  // 총 고도(m)

  @Column
  protected BigDecimal avgPace; // 평균 페이스(sec/km)

}
