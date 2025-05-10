package com.example.lab1.repository;

import com.example.lab1.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    /** аудит по конкретной сигнатуре */
    List<Audit> findBySignatureId(UUID signatureId);

    /** аудит по типу операции (CREATED, UPDATED, DELETE, CORRUPTED) */
    List<Audit> findByChangeType(String changeType);

    /** аудит за интервал времени */
    List<Audit> findByChangedAtBetween(LocalDateTime from, LocalDateTime to);
}
