package com.example.hf.controller;

import com.example.hf.entity.HwmTracker;
import com.example.hf.repository.HwmTrackerRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hwm")
public class HwmTrackerController {
  private final HwmTrackerRepository repo;

  public HwmTrackerController(HwmTrackerRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<HwmTracker> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public HwmTracker get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "HwmTracker not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public HwmTracker create(@Valid @RequestBody HwmTracker body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public HwmTracker update(@PathVariable UUID id, @Valid @RequestBody HwmTracker body) {
    body.setHwmId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
