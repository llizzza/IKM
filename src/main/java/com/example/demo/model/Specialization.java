package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Класс, описывающий специализацию тренеров или абонементов.
 */
@Entity
@Table(name = "specializations")
@Data
public class Specialization {

    /**
     * Уникальный номер специализации.
     *
     * Используется как идентификатор в базе данных
     * и создаётся автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specializationNumber;

    /**
     * Название специализации.
     *
     * Обязательное поле.
     */
    @Column(nullable = false)
    private String name;
}
