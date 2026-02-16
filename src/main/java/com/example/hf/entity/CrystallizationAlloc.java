package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "crystallization_alloc", schema = "hf")
@Getter
@Setter
public class CrystallizationAlloc {
  @Id
  @Column(name = "crystal_alloc_id", nullable = false)
  private UUID crystalAllocId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "crystal_id", nullable = false)
  private UUID crystalId;
  @Column(name = "inv_acct_id", nullable = false)
  private UUID invAcctId;
  @Column(name = "perf_fee_amount", nullable = false)
  private BigDecimal perfFeeAmount;
  @Column(name = "basis_units")
  private BigDecimal basisUnits;
  @Column(name = "basis_profit")
  private BigDecimal basisProfit;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
