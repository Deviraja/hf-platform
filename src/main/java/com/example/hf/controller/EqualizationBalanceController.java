package com.example.hf.controller;

import com.example.hf.entity.EqualizationBalance;
import com.example.hf.repository.EqualizationBalanceRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/equalization/balances")
public class EqualizationBalanceController {
  private final EqualizationBalanceRepository repo;

  public EqualizationBalanceController(EqualizationBalanceRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<EqualizationBalance> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public EqualizationBalance get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "EqualizationBalance not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EqualizationBalance create(@Valid @RequestBody EqualizationBalance body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public EqualizationBalance update(@PathVariable UUID id, @Valid @RequestBody EqualizationBalance body) {
    body.setEqBalId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
