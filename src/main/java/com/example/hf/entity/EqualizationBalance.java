package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "equalization_balance", schema = "hf")
@Getter
@Setter
public class EqualizationBalance {
  @Id
  @Column(name = "eq_bal_id", nullable = false)
  private UUID eqBalId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "inv_acct_id", nullable = false)
  private UUID invAcctId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "balance_amount", nullable = false)
  private BigDecimal balanceAmount;
  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

}
