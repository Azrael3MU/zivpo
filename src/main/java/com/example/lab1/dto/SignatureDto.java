package com.example.lab1.dto;
import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SignatureDto {
    private UUID id;
    private String threatName;
    private String firstBytes;
    private String remainderHash;
    private int    remainderLength;
    private String fileType;
    private int    offsetStart;
    private int    offsetEnd;
}
