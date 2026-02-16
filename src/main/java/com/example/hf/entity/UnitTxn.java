package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "unit_txn", schema = "hf")
@Getter
@Setter
public class UnitTxn {
  @Id
  @Column(name = "unit_txn_id", nullable = false)
  private UUID unitTxnId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "inv_acct_id", nullable = false)
  private UUID invAcctId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "nav_date", nullable = false)
  private LocalDate navDate;
  @Column(name = "txn_type", nullable = false)
  private String txnType;
  @Column(name = "units", nullable = false)
  private BigDecimal units;
  @Column(name = "price")
  private BigDecimal price;
  @Column(name = "amount")
  private BigDecimal amount;
  @Column(name = "order_id")
  private UUID orderId;
  @Column(name = "val_run_id")
  private UUID valRunId;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
