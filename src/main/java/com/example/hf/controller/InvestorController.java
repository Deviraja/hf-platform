package com.example.hf.controller;

import com.example.hf.entity.Investor;
import com.example.hf.repository.InvestorRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/investors")
public class InvestorController {
  private final InvestorRepository repo;

  public InvestorController(InvestorRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Investor> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public Investor get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Investor not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Investor create(@Valid @RequestBody Investor body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public Investor update(@PathVariable UUID id, @Valid @RequestBody Investor body) {
    body.setInvestorId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
