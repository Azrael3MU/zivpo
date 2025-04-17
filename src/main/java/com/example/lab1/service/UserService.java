package com.example.lab1.service;

import com.example.lab1.dto.UserDto;
import com.example.lab1.entity.Project;
import com.example.lab1.entity.Role;
import com.example.lab1.entity.User;
import com.example.lab1.repository.ProjectRepository;
import com.example.lab1.repository.RoleRepository;
import com.example.lab1.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final ProjectRepository projectRepo;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, ProjectRepository projectRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.projectRepo = projectRepo;
    }

    public List<UserDto> findAll() {
        return userRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserDto findById(UUID id) {
        return toDto(userRepo.findById(id).orElseThrow());
    }

    public UUID create(UserDto dto) {
        User u = new User();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());

        if (dto.getRoleId() != null) {
            Role role = roleRepo.findById(dto.getRoleId()).orElseThrow();
            u.setRole(role);
        }

        if (dto.getProjectIds() != null && !dto.getProjectIds().isEmpty()) {
            Set<Project> projects = new HashSet<>(projectRepo.findAllById(dto.getProjectIds()));
            u.setProjects(projects);
        }

        userRepo.save(u);
        return u.getId();
    }

    public void update(UUID id, UserDto dto) {
        User u = userRepo.findById(id).orElseThrow();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());

        if (dto.getRoleId() != null) {
            Role role = roleRepo.findById(dto.getRoleId()).orElseThrow();
            u.setRole(role);
        } else {
            u.setRole(null);
        }

        if (dto.getProjectIds() != null) {
            Set<Project> projects = new HashSet<>(projectRepo.findAllById(dto.getProjectIds()));
            u.setProjects(projects);
        } else {
            u.getProjects().clear();
        }

        userRepo.save(u);
    }

    public void delete(UUID id) {
        userRepo.deleteById(id);
    }

    private UserDto toDto(User u) {
        return UserDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .roleId(u.getRole() != null ? u.getRole().getId() : null)
                .projectIds(u.getProjects().stream().map(Project::getId).collect(Collectors.toSet()))
                .build();
    }
}
