package com.example.demo.repository;

import com.example.demo.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс репозитория для сущности Coach.
 */
@Repository
public interface CoachRepository extends JpaRepository<Coach, Integer> {
}