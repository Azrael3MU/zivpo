package com.example.lab1.dto;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RoleDto {
    private UUID id;
    private String name;
}
