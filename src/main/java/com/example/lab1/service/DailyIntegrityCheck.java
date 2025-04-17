package com.example.lab1.service;

import com.example.lab1.entity.Signature;
import com.example.lab1.repository.AuditRepository;
import com.example.lab1.repository.SignatureRepository;
import com.example.lab1.util.AuditEntry;
import com.example.lab1.util.SignatureCrypto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class DailyIntegrityCheck {
    private final SignatureRepository repo;
    private final SignatureCrypto crypto;
    private final AuditRepository auditRepo;
    private LocalDateTime lastRun = LocalDateTime.MIN;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void verify() throws GeneralSecurityException {
        var changed = repo.findByUpdatedAtAfter(lastRun);
        lastRun = LocalDateTime.now();
        for(Signature s: changed){
            boolean ok = crypto.verify(
                (s.getThreatName()+"|"+s.getFirstBytes()+"|"+s.getRemainderHash()+"|"+
                 s.getRemainderLength()+"|"+s.getFileType()+"|"+s.getOffsetStart()+"|"+s.getOffsetEnd()).getBytes(),
                s.getDigitalSignature());
            if(!ok){
                s.setStatus(Signature.Status.CORRUPTED);
                auditRepo.save(AuditEntry.corrupted(s.getId()));
            }
        }
    }
}
