package com.example.hf.controller;

import com.example.hf.entity.SeriesRollEvent;
import com.example.hf.repository.SeriesRollEventRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/series-rolls")
public class SeriesRollEventController {
  private final SeriesRollEventRepository repo;

  public SeriesRollEventController(SeriesRollEventRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<SeriesRollEvent> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public SeriesRollEvent get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SeriesRollEvent not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SeriesRollEvent create(@Valid @RequestBody SeriesRollEvent body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public SeriesRollEvent update(@PathVariable UUID id, @Valid @RequestBody SeriesRollEvent body) {
    body.setRollId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
