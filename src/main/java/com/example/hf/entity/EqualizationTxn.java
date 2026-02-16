package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "equalization_txn", schema = "hf")
@Getter
@Setter
public class EqualizationTxn {
  @Id
  @Column(name = "eq_txn_id", nullable = false)
  private UUID eqTxnId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "inv_acct_id", nullable = false)
  private UUID invAcctId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "val_run_id")
  private UUID valRunId;
  @Column(name = "nav_date", nullable = false)
  private LocalDate navDate;
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;
  @Column(name = "reason_code", nullable = false)
  private String reasonCode;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
