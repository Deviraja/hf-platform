package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "investor_account", schema = "hf")
@Getter
@Setter
public class InvestorAccount {
  @Id
  @Column(name = "inv_acct_id", nullable = false)
  private UUID invAcctId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "investor_id", nullable = false)
  private UUID investorId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "account_code", nullable = false)
  private String accountCode;
  @Column(name = "open_date", nullable = false)
  private LocalDate openDate;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
