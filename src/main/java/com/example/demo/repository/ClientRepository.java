package com.example.demo.repository;

import com.example.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс репозитория для сущности Client.
 * Предоставляет методы для работы с таблицей клиентов.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}