package com.example.hf.dto;

import java.time.LocalDate;
import java.util.UUID;

public record FundResponse(
    UUID fundId,
    UUID tenantId,
    String fundCode,
    String fundName,
    String baseCcy,
    LocalDate inceptionDate,
    String status
) {}
