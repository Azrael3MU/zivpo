package com.example.lab1.service;

import com.example.lab1.entity.Role;
import com.example.lab1.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RoleService {

    private final RoleRepository repo;

    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }

    public List<Role> findAll() {
        return repo.findAll();
    }

    public Role findById(UUID id) {
        return repo.findById(id).orElseThrow();
    }

    public Role save(Role e) {
        return repo.save(e);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
