package com.example.demo.controller;

import com.example.demo.model.TicketPurchase;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

/**
 * Контроллер для работы с покупками абонементов.
 *
 * Отвечает за просмотр списка покупок, добавление новой покупки и удаление существующей.
 */
@Controller
@RequestMapping("/purchases")
public class TicketPurchaseController {

    /**
     * Репозиторий для работы с покупками абонементов.
     */
    @Autowired
    private TicketPurchaseRepository purchaseRepo;

    /**
     * Репозиторий для получения списка клиентов.
     */
    @Autowired
    private ClientRepository clientRepo;

    /**
     * Репозиторий для получения списка абонементов.
     */
    @Autowired
    private SeasonTicketRepository ticketRepo;

    /**
     * Показывает список всех покупок абонементов.
     *
     * Загружает данные из базы и передаёт их на страницу со списком покупок.
     *
     * @param model объект для передачи данных в представление
     * @return страница со списком покупок
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("purchases", purchaseRepo.findAll());
        return "purchases/list";
    }

    /**
     * Открывает форму добавления новой покупки.
     *
     * В форму передаётся пустой объект покупки, а также списки клиентов и абонементов
     * для выбора.
     *
     * @param model объект для передачи данных в представление
     * @return страница формы покупки
     */
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("purchase", new TicketPurchase());
        model.addAttribute("clients", clientRepo.findAll());
        model.addAttribute("tickets", ticketRepo.findAll());
        return "purchases/form";
    }

    /**
     * Сохраняет данные о покупке абонемента.
     *
     * Если дата покупки не указана,
     * она автоматически устанавливается
     * как текущая дата.
     *
     * @param purchase объект покупки с данными формы
     * @return перенаправление на список покупок
     */
    @PostMapping("/save")
    public String save(@ModelAttribute TicketPurchase purchase) {
        if (purchase.getPurchaseDate() == null) {
            purchase.setPurchaseDate(LocalDate.now());
        }
        purchaseRepo.save(purchase);
        return "redirect:/purchases";
    }

    /**
     * Удаляет покупку по её идентификатору.
     *
     * @param id идентификатор покупки
     * @return перенаправление на список покупок
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        purchaseRepo.deleteById(id);
        return "redirect:/purchases";
    }
}
