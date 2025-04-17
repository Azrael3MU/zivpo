package com.example.lab1.service;

import com.example.lab1.entity.Signature;
import com.example.lab1.repository.SignatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SignatureService {

    private final SignatureRepository repo;

    public SignatureService(SignatureRepository repo) {
        this.repo = repo;
    }

    public List<Signature> findAll() {
        return repo.findAll();
    }

    public Signature findById(UUID id) {
        return repo.findById(id).orElseThrow();
    }

    public Signature save(Signature e) {
        return repo.save(e);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
