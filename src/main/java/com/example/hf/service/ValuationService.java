package com.example.hf.service;

import com.example.hf.entity.ValuationRun;
import com.example.hf.repository.ValuationRunRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class ValuationService {

  private final ValuationRunRepository valuationRunRepository;

  public ValuationService(ValuationRunRepository valuationRunRepository) {
    this.valuationRunRepository = valuationRunRepository;
  }

  /**
   * This is a starter stub.
   * In production, this method would:
   *  1) Validate TB import exists & belongs to node/tenant
   *  2) Compute NAV from TB (Assets - Liabilities)
   *  3) Apply dealing/orders to units outstanding
   *  4) Run management fee accrual and incentive fee logic (HWM/equalization)
   *  5) Persist class_nav, fee_accrual, unit_txn snapshots, GL postings, etc.
   */
  @Transactional
  public ValuationRun createOrUpdateRun(UUID tenantId, UUID nodeId, java.time.LocalDate navDate, UUID tbImportId) {
    var run = new ValuationRun();
    run.setValRunId(UUID.randomUUID());
    run.setTenantId(tenantId);
    run.setNodeId(nodeId);
    run.setNavDate(navDate);
    run.setRunStatus("DRAFT");
    run.setTbImportId(tbImportId);
    run.setCreatedAt(OffsetDateTime.now());
    return valuationRunRepository.save(run);
  }
}
