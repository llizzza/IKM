package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

/**
 * Класс, описывающий клиента фитнес-центра.
 *
 * Содержит основную информацию о клиенте:
 * имя, дату рождения, контактные данные,
 * а также связанные с ним посещения и покупки абонементов.
 */
@Entity
@Table(name = "clients")
@Data
public class Client {

    /**
     * Уникальный номер клиента.
     *
     * Используется как идентификатор в базе данных
     * и автоматически создаётся системой.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientNumber;

    /**
     * Полное имя клиента.
     */
    private String fullName;

    /**
     * Дата рождения клиента.
     */
    private LocalDate birthDate;

    /**
     * Номер телефона клиента.
     */
    private String phone;

    /**
     * Адрес электронной почты клиента.
     */
    private String email;

    /**
     * Список посещений клиента.
     *
     * Связь "один клиент — много посещений".
     * При удалении клиента все его посещения
     * также удаляются.
     */
    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Visit> visits;

    /**
     * Список покупок абонементов клиента.
     *
     * Связь "один клиент — много покупок".
     * При удалении клиента все связанные покупки
     * удаляются автоматически.
     */
    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TicketPurchase> purchases;
}
