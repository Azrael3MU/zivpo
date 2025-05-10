package com.example.lab1.repository;

import com.example.lab1.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.users")
    List<Role> findAllWithUsers();
}
