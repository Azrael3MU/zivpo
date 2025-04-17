package com.example.lab1.dto;

import lombok.*;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SignatureScanResult {
    private UUID   signatureId;
    private String threatName;
    private long   offsetFromStart;
    private long   offsetFromEnd;
    private boolean matched;
}
