package com.example.lab1.controller;

import com.example.lab1.entity.Signature;
import com.example.lab1.service.SignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/signatures")
@RequiredArgsConstructor
public class SignatureController {

    private final SignatureService service;

    @GetMapping
    public List<Signature> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Signature one(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Signature create(@RequestBody Signature e) {
        return service.save(e);
    }

    @PutMapping("/{id}")
    public Signature update(@PathVariable UUID id, @RequestBody Signature e) {
        e.setId(id);
        return service.save(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
