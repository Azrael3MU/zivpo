package com.example.lab1.repository;

import com.example.lab1.entity.Signature;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SignatureRepository extends JpaRepository<Signature, UUID> {
    List<Signature> findByStatus(Signature.Status status);
    List<Signature> findByUpdatedAtAfter(LocalDateTime since);
    List<Signature> findByIdIn(List<UUID> ids);
}
