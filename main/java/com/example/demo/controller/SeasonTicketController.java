package com.example.demo.controller;

import com.example.demo.model.SeasonTicket;
import com.example.demo.repository.SeasonTicketRepository;
import com.example.demo.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с абонементами фитнес-центра.
 *
 * Отвечает за просмотр списка абонементов,
 * добавление новых и удаление существующих.
 */
@Controller
@RequestMapping("/tickets")
public class SeasonTicketController {

    /**
     * Репозиторий для работы с абонементами.
     */
    @Autowired
    private SeasonTicketRepository ticketRepo;

    /**
     * Репозиторий для получения списка специализаций.
     */
    @Autowired
    private SpecializationRepository specRepo;

    /**
     * Показывает список всех абонементов.
     *
     * Загружает данные из базы и передаёт их
     * на страницу со списком абонементов.
     *
     * @param model объект для передачи данных в представление
     * @return страница со списком абонементов
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("tickets", ticketRepo.findAll());
        return "tickets/list";
    }

    /**
     * Открывает форму добавления нового абонемента.
     *
     * В форму передаётся пустой объект абонемента
     * и список специализаций для выбора.
     *
     * @param model объект для передачи данных в представление
     * @return страница формы абонемента
     */
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("ticket", new SeasonTicket());
        model.addAttribute("specs", specRepo.findAll());
        return "tickets/form";
    }

    /**
     * Удаляет абонемент по его идентификатору.
     *
     * Если абонемент найден в базе данных,
     * он будет удалён.
     *
     * @param id идентификатор абонемента
     * @return перенаправление на список абонементов
     */
    @Transactional
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        ticketRepo.findById(id)
                .ifPresent(ticket -> ticketRepo.delete(ticket));
        return "redirect:/tickets";
    }

    /**
     * Сохраняет данные абонемента.
     *
     * Используется как для добавления нового,
     * так и для обновления существующего абонемента.
     *
     * @param ticket объект абонемента с данными формы
     * @return перенаправление на список абонементов
     */
    @PostMapping("/save")
    public String save(@ModelAttribute SeasonTicket ticket) {
        ticketRepo.save(ticket);
        return "redirect:/tickets";
    }
}
