package com.example.hf.controller;

import com.example.hf.entity.FeeAccrual;
import com.example.hf.repository.FeeAccrualRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fee-accruals")
public class FeeAccrualController {
  private final FeeAccrualRepository repo;

  public FeeAccrualController(FeeAccrualRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<FeeAccrual> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public FeeAccrual get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FeeAccrual not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FeeAccrual create(@Valid @RequestBody FeeAccrual body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public FeeAccrual update(@PathVariable UUID id, @Valid @RequestBody FeeAccrual body) {
    body.setFeeAccrualId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
