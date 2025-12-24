package com.example.demo.repository;

import com.example.demo.model.TicketPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс репозитория для сущности TicketPurchase.
 */
@Repository
public interface TicketPurchaseRepository extends JpaRepository<TicketPurchase, Integer> {
}