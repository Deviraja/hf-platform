package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fee_schedule", schema = "hf")
@Getter
@Setter
public class FeeSchedule {
  @Id
  @Column(name = "fee_schedule_id", nullable = false)
  private UUID feeScheduleId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "effective_from", nullable = false)
  private LocalDate effectiveFrom;
  @Column(name = "effective_to")
  private LocalDate effectiveTo;
  @Column(name = "mgmt_rate_annual", nullable = false)
  private BigDecimal mgmtRateAnnual;
  @Column(name = "mgmt_base", nullable = false)
  private String mgmtBase;
  @Column(name = "accrual_freq", nullable = false)
  private String accrualFreq;
  @Column(name = "perf_rate", nullable = false)
  private BigDecimal perfRate;
  @Column(name = "hurdle_rate")
  private BigDecimal hurdleRate;
  @Column(name = "hwm_enabled", nullable = false)
  private Boolean hwmEnabled;
  @Column(name = "crystallization_freq", nullable = false)
  private String crystallizationFreq;
  @Column(name = "equalization_method", nullable = false)
  private String equalizationMethod;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
