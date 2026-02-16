package com.example.hf.service;

import com.example.hf.dto.CreateTrialBalanceImportRequest;
import com.example.hf.dto.TrialBalanceLineDTO;
import com.example.hf.entity.TrialBalanceImport;
import com.example.hf.entity.TrialBalanceLine;
import com.example.hf.repository.TrialBalanceImportRepository;
import com.example.hf.repository.TrialBalanceLineRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class TrialBalanceService {

  private final TrialBalanceImportRepository importRepository;
  private final TrialBalanceLineRepository lineRepository;

  public TrialBalanceService(TrialBalanceImportRepository importRepository, TrialBalanceLineRepository lineRepository) {
    this.importRepository = importRepository;
    this.lineRepository = lineRepository;
  }

  @Transactional
  public TrialBalanceImport createImport(CreateTrialBalanceImportRequest req) {
    var imp = new TrialBalanceImport();
    imp.setTbImportId(UUID.randomUUID());
    imp.setTenantId(req.tenantId());
    imp.setNodeId(req.nodeId());
    imp.setSourceSystem(req.sourceSystem());
    imp.setAsOfDate(req.asOfDate());
    imp.setStatus("IMPORTED");
    imp.setFileRef(req.fileRef());
    imp.setImportedAt(OffsetDateTime.now());
    var saved = importRepository.save(imp);

    if (req.lines() != null) {
      for (TrialBalanceLineDTO l : req.lines()) {
        persistLine(saved.getTbImportId(), req.tenantId(), l);
      }
    }
    return saved;
  }

  private void persistLine(UUID tbImportId, UUID tenantId, TrialBalanceLineDTO l) {
    var line = new TrialBalanceLine();
    line.setTbLineId(UUID.randomUUID());
    line.setTenantId(tenantId);
    line.setTbImportId(tbImportId);
    line.setSrcAccountCode(l.srcAccountCode());
    line.setSrcAccountName(l.srcAccountName());
    line.setDebit(z(l.debit()));
    line.setCredit(z(l.credit()));
    line.setCurrency(l.currency());
    line.setFxRate(l.fxRate());
    line.setBaseAmount(l.baseAmount());
    line.setCreatedAt(OffsetDateTime.now());
    lineRepository.save(line);
  }

  private BigDecimal z(BigDecimal v) {
    return v == null ? BigDecimal.ZERO : v;
  }
}
