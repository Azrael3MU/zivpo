package com.example.lab1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Project {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;

    @ManyToMany(mappedBy = "projects")
    @Builder.Default
    private Set<User> users = new HashSet<>();
}
