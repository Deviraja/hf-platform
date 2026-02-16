package com.example.hf.controller;

import com.example.hf.entity.TrialBalanceImport;
import com.example.hf.repository.TrialBalanceImportRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trial-balance/imports")
public class TrialBalanceImportController {
  private final TrialBalanceImportRepository repo;

  public TrialBalanceImportController(TrialBalanceImportRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<TrialBalanceImport> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public TrialBalanceImport get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TrialBalanceImport not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TrialBalanceImport create(@Valid @RequestBody TrialBalanceImport body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public TrialBalanceImport update(@PathVariable UUID id, @Valid @RequestBody TrialBalanceImport body) {
    body.setTbImportId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
