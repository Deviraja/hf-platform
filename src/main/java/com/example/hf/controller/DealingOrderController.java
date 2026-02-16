package com.example.hf.controller;

import com.example.hf.entity.DealingOrder;
import com.example.hf.repository.DealingOrderRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class DealingOrderController {
  private final DealingOrderRepository repo;

  public DealingOrderController(DealingOrderRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<DealingOrder> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public DealingOrder get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DealingOrder not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DealingOrder create(@Valid @RequestBody DealingOrder body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public DealingOrder update(@PathVariable UUID id, @Valid @RequestBody DealingOrder body) {
    body.setOrderId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
