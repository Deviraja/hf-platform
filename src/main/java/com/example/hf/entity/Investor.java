package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "investor", schema = "hf")
@Getter
@Setter
public class Investor {
  @Id
  @Column(name = "investor_id", nullable = false)
  private UUID investorId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "investor_type", nullable = false)
  private String investorType;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "country_code")
  private String countryCode;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
