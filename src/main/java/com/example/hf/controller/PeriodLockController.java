package com.example.hf.controller;

import com.example.hf.entity.PeriodLock;
import com.example.hf.repository.PeriodLockRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/period-locks")
public class PeriodLockController {
  private final PeriodLockRepository repo;

  public PeriodLockController(PeriodLockRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<PeriodLock> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public PeriodLock get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PeriodLock not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PeriodLock create(@Valid @RequestBody PeriodLock body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public PeriodLock update(@PathVariable UUID id, @Valid @RequestBody PeriodLock body) {
    body.setLockId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
