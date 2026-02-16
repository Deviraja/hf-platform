package com.example.hf.controller;

import com.example.hf.entity.EqualizationTxn;
import com.example.hf.repository.EqualizationTxnRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/equalization/txns")
public class EqualizationTxnController {
  private final EqualizationTxnRepository repo;

  public EqualizationTxnController(EqualizationTxnRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<EqualizationTxn> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public EqualizationTxn get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "EqualizationTxn not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EqualizationTxn create(@Valid @RequestBody EqualizationTxn body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public EqualizationTxn update(@PathVariable UUID id, @Valid @RequestBody EqualizationTxn body) {
    body.setEqTxnId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
