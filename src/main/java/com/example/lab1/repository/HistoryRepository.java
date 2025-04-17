package com.example.lab1.repository;
import com.example.lab1.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
public interface HistoryRepository extends JpaRepository<History, Long> {}
