package com.example.lab1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Signature {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String threatName;

    @Column(length = 16, nullable = false)
    private String firstBytes;

    @Column(nullable = false)
    private String remainderHash;

    private int remainderLength;

    private String fileType;

    private int offsetStart;
    private int offsetEnd;
}
