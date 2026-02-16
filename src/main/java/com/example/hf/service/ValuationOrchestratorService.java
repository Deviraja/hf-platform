package com.example.hf.service;

import com.example.hf.dto.RunValuationResult;
import com.example.hf.entity.ClassNav;
import com.example.hf.entity.TrialBalanceLine;
import com.example.hf.entity.ValuationRun;
import com.example.hf.repository.ClassNavRepository;
import com.example.hf.repository.ShareClassRepository;
import com.example.hf.repository.TrialBalanceLineRepository;
import com.example.hf.repository.ValuationRunRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Valuation Orchestrator (starter).
 * In production this coordinates:
 *  - TB validation and mapping
 *  - NAV calc per node
 *  - NAV allocation to share classes
 *  - dealing impact (units outstanding)
 *  - fee accruals (mgmt/perf)
 *  - postings and snapshots
 */
@Service
public class ValuationOrchestratorService {

  private final ValuationRunRepository valuationRunRepository;
  private final TrialBalanceLineRepository trialBalanceLineRepository;
  private final ShareClassRepository shareClassRepository;
  private final ClassNavRepository classNavRepository;

  public ValuationOrchestratorService(
      ValuationRunRepository valuationRunRepository,
      TrialBalanceLineRepository trialBalanceLineRepository,
      ShareClassRepository shareClassRepository,
      ClassNavRepository classNavRepository
  ) {
    this.valuationRunRepository = valuationRunRepository;
    this.trialBalanceLineRepository = trialBalanceLineRepository;
    this.shareClassRepository = shareClassRepository;
    this.classNavRepository = classNavRepository;
  }

  @Transactional
  public RunValuationResult run(UUID tenantId, UUID nodeId, java.time.LocalDate navDate, UUID tbImportId) {
    // 1) Create valuation run
    var run = new ValuationRun();
    run.setValRunId(UUID.randomUUID());
    run.setTenantId(tenantId);
    run.setNodeId(nodeId);
    run.setNavDate(navDate);
    run.setRunStatus("RUNNING");
    run.setTbImportId(tbImportId);
    run.setCreatedAt(OffsetDateTime.now());
    run = valuationRunRepository.save(run);

    // 2) Very simplified NAV calc from TB: sum(debit-credit) = net assets (placeholder)
    BigDecimal navTotal = BigDecimal.ZERO;
    if (tbImportId != null) {
      var lines = trialBalanceLineRepository.findByTbImportId(tbImportId);
      for (TrialBalanceLine l : lines) {
        var d = l.getDebit() == null ? BigDecimal.ZERO : l.getDebit();
        var c = l.getCredit() == null ? BigDecimal.ZERO : l.getCredit();
        navTotal = navTotal.add(d.subtract(c));
      }
    }

    // 3) Allocate NAV equally across share classes for the node (placeholder)
    var classes = shareClassRepository.findByNodeId(nodeId);
    var results = new ArrayList<RunValuationResult.ClassNavResult>();
    if (!classes.isEmpty()) {
      var perClassNav = navTotal.divide(BigDecimal.valueOf(classes.size()), 10, java.math.RoundingMode.HALF_UP);
      for (var sc : classes) {
        var unitsOutstanding = new BigDecimal("1000.0000000000"); // placeholder
        var navPerUnit = unitsOutstanding.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
            : perClassNav.divide(unitsOutstanding, 10, java.math.RoundingMode.HALF_UP);

        var cn = new ClassNav();
        cn.setClassNavId(UUID.randomUUID());
        cn.setTenantId(tenantId);
        cn.setValRunId(run.getValRunId());
        cn.setClassId(sc.getClassId());
        cn.setNavTotal(perClassNav);
        cn.setNavPerUnit(navPerUnit);
        cn.setUnitsOutstanding(unitsOutstanding);
        cn.setSubsAmount(BigDecimal.ZERO);
        cn.setRedsAmount(BigDecimal.ZERO);
        cn.setCreatedAt(OffsetDateTime.now());
        classNavRepository.save(cn);

        results.add(new RunValuationResult.ClassNavResult(sc.getClassId(), perClassNav, navPerUnit, unitsOutstanding));
      }
    }

    run.setRunStatus("COMPLETE");
    run.setCompletedAt(OffsetDateTime.now());
    valuationRunRepository.save(run);

    return new RunValuationResult(run.getValRunId(), tenantId, nodeId, navDate, run.getRunStatus(), results);
  }
}
