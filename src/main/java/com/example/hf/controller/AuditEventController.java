package com.example.hf.controller;

import com.example.hf.entity.AuditEvent;
import com.example.hf.repository.AuditEventRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/audit-events")
public class AuditEventController {
  private final AuditEventRepository repo;

  public AuditEventController(AuditEventRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<AuditEvent> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public AuditEvent get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AuditEvent not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AuditEvent create(@Valid @RequestBody AuditEvent body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public AuditEvent update(@PathVariable UUID id, @Valid @RequestBody AuditEvent body) {
    body.setAuditId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
