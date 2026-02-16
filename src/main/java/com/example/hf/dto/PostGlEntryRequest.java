package com.example.hf.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PostGlEntryRequest(
    @NotNull UUID tenantId,
    @NotNull UUID nodeId,
    LocalDate entryDate,
    @NotBlank String sourceType,   // TB|DEALING|FEES|ALLOCATION|MANUAL
    UUID sourceId,
    String reference,
    @Valid List<PostGlLine> lines
) {
  public record PostGlLine(
      @NotNull UUID coaId,
      BigDecimal debit,
      BigDecimal credit,
      String currency,
      BigDecimal fxRate,
      UUID classId,
      UUID invAcctId
  ) {}
}
