package com.example.lab1.controller;

import com.example.lab1.entity.Role;
import com.example.lab1.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping
    public List<Role> all() {
        return service.findAll(); // теперь с JOIN FETCH
    }

    @GetMapping("/{id}")
    public Role one(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role create(@RequestBody Role e) {
        return service.save(e);
    }

    @PutMapping("/{id}")
    public Role update(@PathVariable UUID id, @RequestBody Role e) {
        e.setId(id);
        return service.save(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
