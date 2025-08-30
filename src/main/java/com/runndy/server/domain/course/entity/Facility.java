package com.runndy.server.domain.course.entity;

import com.runndy.server.domain.cmn.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
    name = "facility",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_facility",
            columnNames = {"crs_id", "pt_seq"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Facility extends BaseEntity {

  @Column(name="crs_id", nullable = false)
  protected Long crsId; // 코스 id

  @Column(name="pt_seq", nullable = false)
  @Enumerated(value = EnumType.STRING)
  protected FacilityType facilityType; // 코스 타입

  @Column(nullable = false)
  protected Boolean fcltYn; // 시설유무

  @Column
  protected String fcltAddr; // 시설 주소
}
