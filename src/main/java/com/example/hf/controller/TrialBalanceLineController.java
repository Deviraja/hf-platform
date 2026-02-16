package com.example.hf.controller;

import com.example.hf.entity.TrialBalanceLine;
import com.example.hf.repository.TrialBalanceLineRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trial-balance/lines")
public class TrialBalanceLineController {
  private final TrialBalanceLineRepository repo;

  public TrialBalanceLineController(TrialBalanceLineRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<TrialBalanceLine> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public TrialBalanceLine get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TrialBalanceLine not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TrialBalanceLine create(@Valid @RequestBody TrialBalanceLine body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public TrialBalanceLine update(@PathVariable UUID id, @Valid @RequestBody TrialBalanceLine body) {
    body.setTbLineId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
