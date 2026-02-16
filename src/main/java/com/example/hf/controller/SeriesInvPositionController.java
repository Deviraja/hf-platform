package com.example.hf.controller;

import com.example.hf.entity.SeriesInvPosition;
import com.example.hf.repository.SeriesInvPositionRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/series-positions")
public class SeriesInvPositionController {
  private final SeriesInvPositionRepository repo;

  public SeriesInvPositionController(SeriesInvPositionRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<SeriesInvPosition> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public SeriesInvPosition get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SeriesInvPosition not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SeriesInvPosition create(@Valid @RequestBody SeriesInvPosition body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public SeriesInvPosition update(@PathVariable UUID id, @Valid @RequestBody SeriesInvPosition body) {
    body.setSipId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
