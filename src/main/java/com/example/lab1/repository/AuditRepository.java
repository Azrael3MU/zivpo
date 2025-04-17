package com.example.lab1.repository;
import com.example.lab1.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditRepository extends JpaRepository<Audit, Long> {}
