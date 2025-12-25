package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.List;

/**
 * Класс, описывающий тренера фитнес клуба.
 *
 * Содержит основную информацию о тренере:
 * имя, специализацию, контактные данные,
 * а также список проведённых посещений.
 */
@Entity
@Table(name = "coaches")
@Data
public class Coach {

    /**
     * Уникальный номер тренера.
     *
     * Используется как идентификатор в базе данных
     * и создаётся автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coachNumber;

    /**
     * Полное имя тренера.
     */
    @NotBlank(message = "Имя обязательно")
    private String fullName;

    /**
     * Специализация тренера.
     *
     * Связь "многие тренеры — одна специализация".
     */
    @ManyToOne
    @JoinColumn(name = "specialization_number")
    private Specialization specialization;

    /**
     * Номер телефона тренера.
     */
    @Pattern(regexp = "^.*$", message = "Ошибка формата")
    private String phone;

    /**
     * Адрес электронной почты тренера.
     */
    private String email;

    /**
     * Список посещений, которые проводит тренер.
     *
     * Связь "один тренер — много посещений".
     * При удалении тренера все связанные посещения
     * также удаляются.
     */
    @OneToMany(
            mappedBy = "coach",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Visit> visits;
}
