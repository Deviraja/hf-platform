package com.example.hf.controller;

import com.example.hf.entity.Fund;
import com.example.hf.repository.FundRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/funds")
public class FundController {
  private final FundRepository repo;

  public FundController(FundRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Fund> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public Fund get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fund not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Fund create(@Valid @RequestBody Fund body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public Fund update(@PathVariable UUID id, @Valid @RequestBody Fund body) {
    body.setFundId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
