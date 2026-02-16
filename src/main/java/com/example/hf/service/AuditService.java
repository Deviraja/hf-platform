package com.example.hf.service;

import com.example.hf.entity.AuditEvent;
import com.example.hf.repository.AuditEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class AuditService {

  private final AuditEventRepository auditRepo;

  public AuditService(AuditEventRepository auditRepo) {
    this.auditRepo = auditRepo;
  }

  @Transactional
  public AuditEvent record(UUID tenantId, UUID actorUserId, String entityType, UUID entityId, String action,
                           String beforeJson, String afterJson) {
    var a = new AuditEvent();
    a.setAuditId(UUID.randomUUID());
    a.setTenantId(tenantId);
    a.setActorUserId(actorUserId);
    a.setEventTs(OffsetDateTime.now());
    a.setEntityType(entityType);
    a.setEntityId(entityId);
    a.setAction(action);
    a.setBeforeJson(beforeJson);
    a.setAfterJson(afterJson);
    return auditRepo.save(a);
  }
}
