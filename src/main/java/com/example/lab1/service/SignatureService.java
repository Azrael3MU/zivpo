package com.example.lab1.service;

import com.example.lab1.dto.SignatureDto;
import com.example.lab1.entity.Signature;
import com.example.lab1.repository.*;
import com.example.lab1.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignatureService {
    private final SignatureRepository repo;
    private final HistoryRepository historyRepo;
    private final AuditRepository auditRepo;
    private final SignatureCrypto crypto;

    @Transactional
    public UUID create(SignatureDto dto,String user){
        Signature s = toEntity(dto);
        try { s.setDigitalSignature(crypto.sign(bytes(s))); } catch(GeneralSecurityException e){throw new RuntimeException(e);}
        repo.save(s); auditRepo.save(AuditEntry.created(s,user)); return s.getId();
    }

    @Transactional
    public void update(UUID id,SignatureDto dto,String user){
        Signature cur = repo.findById(id).orElseThrow();
        historyRepo.save(HistoryEntry.from(cur));
        Signature fresh = toEntity(dto);
        Map<String,Object[]> diff = DiffUtil.diff(cur,fresh);
        cur.updateFrom(fresh); cur.setUpdatedAt(LocalDateTime.now());
        try { cur.setDigitalSignature(crypto.sign(bytes(cur))); }catch(GeneralSecurityException e){throw new RuntimeException(e);}
        repo.save(cur); auditRepo.save(AuditEntry.updated(id,user,diff));
    }

    @Transactional
    public void softDelete(UUID id,String user){
        Signature s = repo.findById(id).orElseThrow();
        historyRepo.save(HistoryEntry.from(s));
        s.setStatus(Signature.Status.DELETED);
        repo.save(s); auditRepo.save(AuditEntry.deleted(id,user));
    }

    /* helpers */
    private byte[] bytes(Signature s){
        return (s.getThreatName()+"|"+s.getFirstBytes()+"|"+s.getRemainderHash()+"|"+
                s.getRemainderLength()+"|"+s.getFileType()+"|"+s.getOffsetStart()+"|"+s.getOffsetEnd()).getBytes();
    }
    private Signature toEntity(SignatureDto d){
        return Signature.builder()
                .threatName(d.getThreatName()).firstBytes(d.getFirstBytes())
                .remainderHash(d.getRemainderHash()).remainderLength(d.getRemainderLength())
                .fileType(d.getFileType()).offsetStart(d.getOffsetStart()).offsetEnd(d.getOffsetEnd())
                .build();
    }
}
