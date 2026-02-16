package com.example.hf.controller;

import com.example.hf.entity.UnitTxn;
import com.example.hf.repository.UnitTxnRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/unit-txns")
public class UnitTxnController {
  private final UnitTxnRepository repo;

  public UnitTxnController(UnitTxnRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<UnitTxn> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public UnitTxn get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UnitTxn not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UnitTxn create(@Valid @RequestBody UnitTxn body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public UnitTxn update(@PathVariable UUID id, @Valid @RequestBody UnitTxn body) {
    body.setUnitTxnId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
