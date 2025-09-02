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
    name = "record_point",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_record_point",
            columnNames = {"rec_id", "pt_seq"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordPoint extends BaseEntity {

  @Column(name = "rec_id", nullable = false)
  protected Long recordId; // 기록 id

  @Column(name = "pt_seq", nullable = false)
  protected Long pointSeq; // 포인트 순서

  @Column(name = "lat")
  protected BigDecimal latitude; // 위도

  @Column(name = "lon")
  protected BigDecimal longitude; // 경도

  @Column(name = "ele")
  protected BigDecimal elevation; // 고도

  @Column(name = "tm")
  protected Timestamp time; // 시간
}
