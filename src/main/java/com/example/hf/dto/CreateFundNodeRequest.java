package com.example.hf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateFundNodeRequest(
    @NotNull UUID tenantId,
    @NotNull UUID fundId,
    @NotBlank String nodeType,   // MASTER|FEEDER|SUBFUND
    @NotBlank String nodeName,
    @NotBlank String nodeCcy
) {}
