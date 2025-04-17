package com.example.lab1.util;
import com.example.lab1.entity.History;
import com.example.lab1.entity.Signature;
import java.time.LocalDateTime;

public class HistoryEntry {
    public static History from(Signature s){
        return History.builder()
                .signatureId(s.getId())
                .versionCreatedAt(LocalDateTime.now())
                .threatName(s.getThreatName())
                .firstBytes(s.getFirstBytes())
                .remainderHash(s.getRemainderHash())
                .remainderLength(s.getRemainderLength())
                .fileType(s.getFileType())
                .offsetStart(s.getOffsetStart())
                .offsetEnd(s.getOffsetEnd())
                .digitalSignature(s.getDigitalSignature())
                .status(s.getStatus().name())
                .updatedAt(s.getUpdatedAt())
                .build();
    }
}
