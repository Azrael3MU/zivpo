package com.example.lab1.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Signature {
    @Id @GeneratedValue
    private UUID id;
    @Column(nullable=false) private String threatName;
    @Column(length=16, nullable=false) private String firstBytes;
    @Column(nullable=false) private String remainderHash;
    private int remainderLength;
    private String fileType;
    private int offsetStart;
    private int offsetEnd;
    @Lob private byte[] digitalSignature;
    @UpdateTimestamp private LocalDateTime updatedAt;
    @Builder.Default @Enumerated(EnumType.STRING) private Status status = Status.ACTUAL;
    public enum Status { ACTUAL, DELETED, CORRUPTED }

    public void updateFrom(Signature src){
        threatName      = src.threatName;
        firstBytes      = src.firstBytes;
        remainderHash   = src.remainderHash;
        remainderLength = src.remainderLength;
        fileType        = src.fileType;
        offsetStart     = src.offsetStart;
        offsetEnd       = src.offsetEnd;
    }
}
