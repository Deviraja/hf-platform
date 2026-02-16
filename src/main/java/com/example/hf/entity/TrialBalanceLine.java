package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "trial_balance_line", schema = "hf")
@Getter
@Setter
public class TrialBalanceLine {
  @Id
  @Column(name = "tb_line_id", nullable = false)
  private UUID tbLineId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "tb_import_id", nullable = false)
  private UUID tbImportId;
  @Column(name = "src_account_code", nullable = false)
  private String srcAccountCode;
  @Column(name = "src_account_name")
  private String srcAccountName;
  @Column(name = "debit", nullable = false)
  private BigDecimal debit;
  @Column(name = "credit", nullable = false)
  private BigDecimal credit;
  @Column(name = "currency")
  private String currency;
  @Column(name = "fx_rate")
  private BigDecimal fxRate;
  @Column(name = "base_amount")
  private BigDecimal baseAmount;
  @Column(name = "mapped_coa_id")
  private UUID mappedCoaId;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
