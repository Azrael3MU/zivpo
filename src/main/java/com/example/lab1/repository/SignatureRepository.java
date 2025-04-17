package com.example.lab1.repository;

import com.example.lab1.entity.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SignatureRepository extends JpaRepository<Signature, UUID> {
}
