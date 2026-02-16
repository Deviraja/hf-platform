package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "gl_entry", schema = "hf")
@Getter
@Setter
public class GlEntry {
  @Id
  @Column(name = "entry_id", nullable = false)
  private UUID entryId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "node_id", nullable = false)
  private UUID nodeId;
  @Column(name = "entry_date", nullable = false)
  private LocalDate entryDate;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "source_type", nullable = false)
  private String sourceType;
  @Column(name = "source_id")
  private UUID sourceId;
  @Column(name = "reference")
  private String reference;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
