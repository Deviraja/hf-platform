package com.example.hf.service;

import com.example.hf.dto.PostGlEntryRequest;
import com.example.hf.entity.GlEntry;
import com.example.hf.entity.GlEntryLine;
import com.example.hf.repository.GlEntryLineRepository;
import com.example.hf.repository.GlEntryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class AccountingService {

  private final GlEntryRepository glEntryRepository;
  private final GlEntryLineRepository glEntryLineRepository;

  public AccountingService(GlEntryRepository glEntryRepository, GlEntryLineRepository glEntryLineRepository) {
    this.glEntryRepository = glEntryRepository;
    this.glEntryLineRepository = glEntryLineRepository;
  }

  @Transactional
  public GlEntry postEntry(PostGlEntryRequest req) {
    var e = new GlEntry();
    e.setEntryId(UUID.randomUUID());
    e.setTenantId(req.tenantId());
    e.setNodeId(req.nodeId());
    e.setEntryDate(req.entryDate() == null ? LocalDate.now() : req.entryDate());
    e.setStatus("POSTED");
    e.setSourceType(req.sourceType());
    e.setSourceId(req.sourceId());
    e.setReference(req.reference());
    e.setCreatedAt(OffsetDateTime.now());
    var saved = glEntryRepository.save(e);

    if (req.lines() != null) {
      for (var l : req.lines()) {
        var line = new GlEntryLine();
        line.setLineId(UUID.randomUUID());
        line.setTenantId(req.tenantId());
        line.setEntryId(saved.getEntryId());
        line.setCoaId(l.coaId());
        line.setDebit(l.debit() == null ? java.math.BigDecimal.ZERO : l.debit());
        line.setCredit(l.credit() == null ? java.math.BigDecimal.ZERO : l.credit());
        line.setCurrency(l.currency());
        line.setFxRate(l.fxRate());
        line.setClassId(l.classId());
        line.setInvAcctId(l.invAcctId());
        line.setCreatedAt(OffsetDateTime.now());
        glEntryLineRepository.save(line);
      }
    }
    return saved;
  }
}
