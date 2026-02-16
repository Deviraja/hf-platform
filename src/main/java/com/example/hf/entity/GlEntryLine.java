package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "gl_entry_line", schema = "hf")
@Getter
@Setter
public class GlEntryLine {
  @Id
  @Column(name = "line_id", nullable = false)
  private UUID lineId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "entry_id", nullable = false)
  private UUID entryId;
  @Column(name = "coa_id", nullable = false)
  private UUID coaId;
  @Column(name = "debit", nullable = false)
  private BigDecimal debit;
  @Column(name = "credit", nullable = false)
  private BigDecimal credit;
  @Column(name = "currency")
  private String currency;
  @Column(name = "fx_rate")
  private BigDecimal fxRate;
  @Column(name = "base_debit")
  private BigDecimal baseDebit;
  @Column(name = "base_credit")
  private BigDecimal baseCredit;
  @Column(name = "class_id")
  private UUID classId;
  @Column(name = "inv_acct_id")
  private UUID invAcctId;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
