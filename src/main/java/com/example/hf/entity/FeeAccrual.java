package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fee_accrual", schema = "hf")
@Getter
@Setter
public class FeeAccrual {
  @Id
  @Column(name = "fee_accrual_id", nullable = false)
  private UUID feeAccrualId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "val_run_id", nullable = false)
  private UUID valRunId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "inv_acct_id")
  private UUID invAcctId;
  @Column(name = "fee_type", nullable = false)
  private String feeType;
  @Column(name = "nav_date", nullable = false)
  private LocalDate navDate;
  @Column(name = "base_amount", nullable = false)
  private BigDecimal baseAmount;
  @Column(name = "rate", nullable = false)
  private BigDecimal rate;
  @Column(name = "fee_amount", nullable = false)
  private BigDecimal feeAmount;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
