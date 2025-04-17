package com.example.lab1.service;

import com.example.lab1.entity.Project;
import com.example.lab1.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo) {
        this.repo = repo;
    }

    public List<Project> findAll() {
        return repo.findAll();
    }

    public Project findById(UUID id) {
        return repo.findById(id).orElseThrow();
    }

    public Project save(Project e) {
        return repo.save(e);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
