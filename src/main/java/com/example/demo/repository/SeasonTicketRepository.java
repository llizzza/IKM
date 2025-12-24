package com.example.demo.repository;

import com.example.demo.model.SeasonTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс репозитория для сущности SeasonTicket.
 */
@Repository
public interface SeasonTicketRepository extends JpaRepository<SeasonTicket, Integer> {
}