package com.example.hf.controller;

import com.example.hf.entity.GlEntryLine;
import com.example.hf.repository.GlEntryLineRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gl/lines")
public class GlEntryLineController {
  private final GlEntryLineRepository repo;

  public GlEntryLineController(GlEntryLineRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<GlEntryLine> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public GlEntryLine get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GlEntryLine not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GlEntryLine create(@Valid @RequestBody GlEntryLine body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public GlEntryLine update(@PathVariable UUID id, @Valid @RequestBody GlEntryLine body) {
    body.setLineId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
