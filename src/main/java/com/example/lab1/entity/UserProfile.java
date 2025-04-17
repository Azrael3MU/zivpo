package com.example.lab1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfile {

    @Id
    @GeneratedValue
    private UUID id;

    private String address;
    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
