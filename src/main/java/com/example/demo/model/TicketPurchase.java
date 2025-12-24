package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * Класс, описывающий покупку абонемента клиентом.
 *
 * Содержит информацию о клиенте, абонементе и дате покупки.
 */
@Entity
@Table(name = "ticket_purchases")
@Data
public class TicketPurchase {

    /**
     * Уникальный номер покупки.
     *
     * Используется как идентификатор в базе данных
     * и создаётся автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseNumber;

    /**
     * Клиент, который купил абонемент.
     *
     * Связь "много покупок — один клиент".
     */
    @ManyToOne
    @JoinColumn(name = "client_number")
    private Client client;

    /**
     * Абонемент, который был куплен.
     *
     * Связь "много покупок — один абонемент".
     */
    @ManyToOne
    @JoinColumn(name = "ticket_number")
    private SeasonTicket seasonTicket;

    /**
     * Дата покупки абонемента.
     *
     * Если не указана, может быть установлена автоматически при сохранении.
     */
    private LocalDate purchaseDate;
}
