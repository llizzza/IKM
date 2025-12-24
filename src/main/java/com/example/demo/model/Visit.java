package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

/**
 * Класс, описывающий посещение фитнес-центра клиентом.
 *
 * Содержит информацию о клиенте, тренере,
 * дате и времени посещения, а также отметку о присутствии.
 */
@Entity
@Table(name = "visits")
@Data
public class Visit {

    /**
     * Уникальный номер посещения.
     *
     * Используется как идентификатор в базе данных
     * и создаётся автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer visitNumber;

    /**
     * Клиент, который посещает фитнес-центр.
     *
     * Связь "много посещений — один клиент".
     */
    @ManyToOne
    @JoinColumn(name = "client_number")
    private Client client;

    /**
     * Тренер, проводящий занятие.
     *
     * Связь "много посещений — один тренер".
     */
    @ManyToOne
    @JoinColumn(name = "coach_number")
    private Coach coach;

    /**
     * Дата и время посещения.
     *
     * Формат: yyyy-MM-dd'T'HH:mm.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime visitDate;

    /**
     * Отметка о посещении (например, "присутствовал" или "отсутствовал").
     */
    private String attended;
}
