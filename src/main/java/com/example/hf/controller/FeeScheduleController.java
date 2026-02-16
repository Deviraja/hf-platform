package com.example.hf.controller;

import com.example.hf.entity.FeeSchedule;
import com.example.hf.repository.FeeScheduleRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fee-schedules")
public class FeeScheduleController {
  private final FeeScheduleRepository repo;

  public FeeScheduleController(FeeScheduleRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<FeeSchedule> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public FeeSchedule get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FeeSchedule not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FeeSchedule create(@Valid @RequestBody FeeSchedule body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public FeeSchedule update(@PathVariable UUID id, @Valid @RequestBody FeeSchedule body) {
    body.setFeeScheduleId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
