package com.example.hf.service;

import com.example.hf.entity.FeeAccrual;
import com.example.hf.repository.FeeAccrualRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class FeeEngineService {

  private final FeeAccrualRepository feeAccrualRepository;

  public FeeEngineService(FeeAccrualRepository feeAccrualRepository) {
    this.feeAccrualRepository = feeAccrualRepository;
  }

  /**
   * Stub: Persist a fee accrual line (MGMT or PERF).
   * Real engine would:
   *  - pull effective fee_schedule
   *  - compute mgmt accrual daily/monthly
   *  - compute perf fee w/ HWM + equalization + crystallization windows
   */
  @Transactional
  public FeeAccrual accrue(UUID tenantId, UUID valRunId, UUID classId, UUID invAcctId,
                           String feeType, java.time.LocalDate navDate,
                           BigDecimal baseAmount, BigDecimal rate, BigDecimal feeAmount) {

    var fa = new FeeAccrual();
    fa.setFeeAccrualId(UUID.randomUUID());
    fa.setTenantId(tenantId);
    fa.setValRunId(valRunId);
    fa.setClassId(classId);
    fa.setInvAcctId(invAcctId);
    fa.setFeeType(feeType);
    fa.setNavDate(navDate);
    fa.setBaseAmount(baseAmount);
    fa.setRate(rate);
    fa.setFeeAmount(feeAmount);
    fa.setCreatedAt(OffsetDateTime.now());
    return feeAccrualRepository.save(fa);
  }
}
