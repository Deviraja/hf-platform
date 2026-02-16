package com.example.hf.controller;

import com.example.hf.dto.RunValuationRequest;
import com.example.hf.entity.ValuationRun;
import com.example.hf.service.ValuationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
public class ValuationWorkflowController {

  private final ValuationService valuationService;

  public ValuationWorkflowController(ValuationService valuationService) {
    this.valuationService = valuationService;
  }

  @PostMapping("/valuation-runs")
  @ResponseStatus(HttpStatus.CREATED)
  public ValuationRun createValuationRun(@Valid @RequestBody RunValuationRequest req) {
    return valuationService.createOrUpdateRun(req.tenantId(), req.nodeId(), req.navDate(), req.tbImportId());
  }
}
