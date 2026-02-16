package com.example.hf.controller;

import com.example.hf.entity.ClassNav;
import com.example.hf.repository.ClassNavRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/class-nav")
public class ClassNavController {
  private final ClassNavRepository repo;

  public ClassNavController(ClassNavRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<ClassNav> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public ClassNav get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ClassNav not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ClassNav create(@Valid @RequestBody ClassNav body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public ClassNav update(@PathVariable UUID id, @Valid @RequestBody ClassNav body) {
    body.setClassNavId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
