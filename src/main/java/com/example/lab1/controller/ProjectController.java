package com.example.lab1.controller;

import com.example.lab1.entity.Project;
import com.example.lab1.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @GetMapping
    public List<Project> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Project one(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@RequestBody Project e) {
        return service.save(e);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable UUID id, @RequestBody Project e) {
        e.setId(id);
        return service.save(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
