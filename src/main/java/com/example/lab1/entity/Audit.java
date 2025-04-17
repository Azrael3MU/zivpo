package com.example.lab1.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Audit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;
    private UUID signatureId;
    private String changedBy;
    private String changeType;
    private LocalDateTime changedAt;
    @Lob private String fieldsChanged;
}
