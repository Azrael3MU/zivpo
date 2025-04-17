package com.example.lab1.dto;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private UUID roleId;
    private Set<UUID> projectIds;
}
