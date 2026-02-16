package com.example.hf.controller;

import com.example.hf.entity.FundNode;
import com.example.hf.repository.FundNodeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fund-nodes")
public class FundNodeController {
  private final FundNodeRepository repo;

  public FundNodeController(FundNodeRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<FundNode> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public FundNode get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FundNode not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FundNode create(@Valid @RequestBody FundNode body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public FundNode update(@PathVariable UUID id, @Valid @RequestBody FundNode body) {
    body.setNodeId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
