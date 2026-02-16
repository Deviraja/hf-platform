package com.example.hf.controller;

import com.example.hf.entity.OrgTenant;
import com.example.hf.repository.OrgTenantRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/Tenants")
public class OrgTenantController {
  private final OrgTenantRepository repo;

  public OrgTenantController(OrgTenantRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<OrgTenant> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public OrgTenant get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OrgTenant not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OrgTenant create(@Valid @RequestBody OrgTenant body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public OrgTenant update(@PathVariable UUID id, @Valid @RequestBody OrgTenant body) {
    body.setTenantId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
