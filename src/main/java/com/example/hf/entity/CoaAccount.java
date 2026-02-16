package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coa_account", schema = "hf")
@Getter
@Setter
public class CoaAccount {
  @Id
  @Column(name = "coa_id", nullable = false)
  private UUID coaId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "account_code", nullable = false)
  private String accountCode;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "account_type", nullable = false)
  private String accountType;
  @Column(name = "normal_balance", nullable = false)
  private String normalBalance;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
