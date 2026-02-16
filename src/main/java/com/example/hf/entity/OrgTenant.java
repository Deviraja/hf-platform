package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "org_tenant", schema = "hf")
@Getter
@Setter
public class OrgTenant {
  @Id
  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;

  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
