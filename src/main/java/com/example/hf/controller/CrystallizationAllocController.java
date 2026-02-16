package com.example.hf.controller;

import com.example.hf.entity.CrystallizationAlloc;
import com.example.hf.repository.CrystallizationAllocRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/crystallization-allocs")
public class CrystallizationAllocController {
  private final CrystallizationAllocRepository repo;

  public CrystallizationAllocController(CrystallizationAllocRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<CrystallizationAlloc> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public CrystallizationAlloc get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CrystallizationAlloc not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CrystallizationAlloc create(@Valid @RequestBody CrystallizationAlloc body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public CrystallizationAlloc update(@PathVariable UUID id, @Valid @RequestBody CrystallizationAlloc body) {
    body.setCrystalAllocId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
