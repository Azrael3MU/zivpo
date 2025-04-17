package com.example.lab1.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class History {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;
    private UUID signatureId;
    private LocalDateTime versionCreatedAt;
    private String threatName;
    private String firstBytes;
    private String remainderHash;
    private int    remainderLength;
    private String fileType;
    private int    offsetStart;
    private int    offsetEnd;
    @Lob private byte[] digitalSignature;
    private String status;
    private LocalDateTime updatedAt;
}
