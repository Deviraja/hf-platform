package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hwm_tracker", schema = "hf")
@Getter
@Setter
public class HwmTracker {
  @Id
  @Column(name = "hwm_id", nullable = false)
  private UUID hwmId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "inv_acct_id", nullable = false)
  private UUID invAcctId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "as_of_date", nullable = false)
  private LocalDate asOfDate;
  @Column(name = "hwm_nav_per_unit", nullable = false)
  private BigDecimal hwmNavPerUnit;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
