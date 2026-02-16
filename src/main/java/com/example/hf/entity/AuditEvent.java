package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "audit_event", schema = "hf")
@Getter
@Setter
public class AuditEvent {
  @Id
  @Column(name = "audit_id", nullable = false)
  private UUID auditId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "actor_user_id")
  private UUID actorUserId;
  @Column(name = "event_ts", nullable = false)
  private OffsetDateTime eventTs;
  @Column(name = "entity_type", nullable = false)
  private String entityType;
  @Column(name = "entity_id", nullable = false)
  private UUID entityId;
  @Column(name = "action", nullable = false)
  private String action;
  @Column(name = "before_json", columnDefinition = "jsonb")
  private String beforeJson;
  @Column(name = "after_json", columnDefinition = "jsonb")
  private String afterJson;

}
