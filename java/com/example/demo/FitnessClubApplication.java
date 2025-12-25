package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс запуска приложения фитнес клуба.
 *
 * Класс содержит метод main, который запускает Spring Boot приложение.
 */
@SpringBootApplication
public class FitnessClubApplication {

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки (необязательно)
     */
    public static void main(String[] args) {
        SpringApplication.run(FitnessClubApplication.class, args);
    }
}
