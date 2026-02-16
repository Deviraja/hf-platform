package com.example.hf.controller;

import com.example.hf.entity.FundEdge;
import com.example.hf.repository.FundEdgeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fund-edges")
public class FundEdgeController {
  private final FundEdgeRepository repo;

  public FundEdgeController(FundEdgeRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<FundEdge> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public FundEdge get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FundEdge not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FundEdge create(@Valid @RequestBody FundEdge body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public FundEdge update(@PathVariable UUID id, @Valid @RequestBody FundEdge body) {
    body.setEdgeId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
