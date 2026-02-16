package com.example.hf.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RunValuationResult(
    UUID valuationRunId,
    UUID tenantId,
    UUID nodeId,
    LocalDate navDate,
    String status,
    List<ClassNavResult> classNavs
) {
  public record ClassNavResult(
      UUID classId,
      BigDecimal navTotal,
      BigDecimal navPerUnit,
      BigDecimal unitsOutstanding
  ) {}
}
