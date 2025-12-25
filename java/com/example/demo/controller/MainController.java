package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер главной страницы приложения.
 *
 * Отвечает за открытие стартовой страницы сайта
 * при переходе пользователя по корневому адресу.
 */
@Controller
public class MainController {

    /**
     * Открывает главную страницу приложения.
     *
     * Метод обрабатывает GET-запрос по адресу "/"
     * и возвращает страницу index.
     *
     * @return имя шаблона главной страницы
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
