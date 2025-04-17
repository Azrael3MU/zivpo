package com.example.lab1.controller;

import com.example.lab1.dto.UserDto;
import com.example.lab1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserDto> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserDto one(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@RequestBody UserDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody UserDto dto) {
        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
