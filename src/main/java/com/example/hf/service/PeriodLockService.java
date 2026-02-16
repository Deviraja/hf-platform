package com.example.hf.service;

import com.example.hf.dto.LockPeriodRequest;
import com.example.hf.entity.PeriodLock;
import com.example.hf.repository.PeriodLockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class PeriodLockService {

  private final PeriodLockRepository repo;

  public PeriodLockService(PeriodLockRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public PeriodLock lock(LockPeriodRequest req) {
    var l = new PeriodLock();
    l.setLockId(UUID.randomUUID());
    l.setTenantId(req.tenantId());
    l.setNodeId(req.nodeId());
    l.setPeriodStart(req.periodStart());
    l.setPeriodEnd(req.periodEnd());
    l.setLockedBy(req.lockedBy());
    l.setLockedAt(OffsetDateTime.now());
    return repo.save(l);
  }
}
