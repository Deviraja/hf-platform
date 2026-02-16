package com.example.hf.controller;

import com.example.hf.entity.Series;
import com.example.hf.repository.SeriesRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/series")
public class SeriesController {
  private final SeriesRepository repo;

  public SeriesController(SeriesRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Series> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public Series get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Series create(@Valid @RequestBody Series body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public Series update(@PathVariable UUID id, @Valid @RequestBody Series body) {
    body.setSeriesId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
