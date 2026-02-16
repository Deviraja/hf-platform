package com.example.hf.controller;

import com.example.hf.entity.CrystallizationEvent;
import com.example.hf.repository.CrystallizationEventRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/crystallizations")
public class CrystallizationEventController {
  private final CrystallizationEventRepository repo;

  public CrystallizationEventController(CrystallizationEventRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<CrystallizationEvent> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public CrystallizationEvent get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CrystallizationEvent not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CrystallizationEvent create(@Valid @RequestBody CrystallizationEvent body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public CrystallizationEvent update(@PathVariable UUID id, @Valid @RequestBody CrystallizationEvent body) {
    body.setCrystalId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
