package com.example.hf.controller;

import com.example.hf.entity.ValuationRun;
import com.example.hf.repository.ValuationRunRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/valuation-runs")
public class ValuationRunController {
  private final ValuationRunRepository repo;

  public ValuationRunController(ValuationRunRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<ValuationRun> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public ValuationRun get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ValuationRun not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ValuationRun create(@Valid @RequestBody ValuationRun body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public ValuationRun update(@PathVariable UUID id, @Valid @RequestBody ValuationRun body) {
    body.setValRunId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
