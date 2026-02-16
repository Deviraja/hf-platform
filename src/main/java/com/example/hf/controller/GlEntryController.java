package com.example.hf.controller;

import com.example.hf.entity.GlEntry;
import com.example.hf.repository.GlEntryRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gl/entries")
public class GlEntryController {
  private final GlEntryRepository repo;

  public GlEntryController(GlEntryRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<GlEntry> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public GlEntry get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GlEntry not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GlEntry create(@Valid @RequestBody GlEntry body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public GlEntry update(@PathVariable UUID id, @Valid @RequestBody GlEntry body) {
    body.setEntryId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
