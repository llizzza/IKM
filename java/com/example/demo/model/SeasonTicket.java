package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * Класс, описывающий абонемент фитнес клуба.
 *
 * Содержит информацию о специализации,
 * количестве доступных занятий и стоимости абонемента.
 */
@Entity
@Table(name = "season_tickets")
@Data
public class SeasonTicket {

    /**
     * Уникальный номер абонемента.
     *
     * Используется как идентификатор в базе данных
     * и создаётся автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketNumber;

    /**
     * Специализация, к которой относится абонемент.
     *
     * Например: йога, плавание и т.д.
     * Один абонемент относится к одной специализации.
     */
    @ManyToOne
    @JoinColumn(name = "specialization_number", nullable = false)
    private Specialization specialization;

    /**
     * Количество занятий, включённых в абонемент.
     *
     * Значение должно быть больше нуля.
     */
    @NotNull
    @Min(value = 1, message = "Количество занятий должно быть больше 0")
    private Integer sessionsCount;

    /**
     * Стоимость абонемента.
     *
     * Указывается в денежном формате.
     */
    @NotNull
    private BigDecimal price;

    /**
     * Список покупок данного абонемента.
     *
     * Связь "один абонемент — много покупок".
     * При удалении абонемента все связанные покупки
     * также удаляются.
     */
    @OneToMany(
            mappedBy = "seasonTicket",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TicketPurchase> purchases;
}
