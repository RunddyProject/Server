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

  @Column(name ="rec_id", nullable = false)
  protected Long recId; // 기록 id

  @Column(name="pt_seq", nullable = false)
  protected Long ptSeq; // 포인트 순서

  @Column
  protected BigDecimal lat; // 위도

  @Column
  protected BigDecimal lon; // 경도

  @Column
  protected BigDecimal ele; // 고도

  @Column
  protected Timestamp tm; // 시간
}
