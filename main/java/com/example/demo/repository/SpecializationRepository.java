package com.example.demo.repository;

import com.example.demo.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс репозитория для сущности Specialization.
 */
@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
}