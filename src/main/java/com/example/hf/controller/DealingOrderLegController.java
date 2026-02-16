package com.example.hf.controller;

import com.example.hf.entity.DealingOrderLeg;
import com.example.hf.repository.DealingOrderLegRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order-legs")
public class DealingOrderLegController {
  private final DealingOrderLegRepository repo;

  public DealingOrderLegController(DealingOrderLegRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<DealingOrderLeg> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public DealingOrderLeg get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DealingOrderLeg not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DealingOrderLeg create(@Valid @RequestBody DealingOrderLeg body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public DealingOrderLeg update(@PathVariable UUID id, @Valid @RequestBody DealingOrderLeg body) {
    body.setLegId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
