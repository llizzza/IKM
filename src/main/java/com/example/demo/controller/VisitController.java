package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

/**
 * Контроллер для работы с посещениями фитнес-центра.
 *
 * Отвечает за просмотр списка посещений,
 * добавление нового посещения и удаление записей.
 *
 * Также проверяет, что у клиента есть купленный абонемент
 * и что дата посещения указана корректно.
 */
@Controller
@RequestMapping("/visits")
public class VisitController {

    /**
     * Репозиторий для работы с посещениями.
     */
    @Autowired
    private VisitRepository visitRepo;

    /**
     * Репозиторий для получения списка тренеров.
     */
    @Autowired
    private CoachRepository coachRepo;

    /**
     * Репозиторий для получения покупок абонементов.
     */
    @Autowired
    private TicketPurchaseRepository purchaseRepo;

    /**
     * Показывает список всех посещений.
     *
     * Загружает данные из базы данных
     * и передаёт их на страницу со списком посещений.
     *
     * @param model объект для передачи данных в представление
     * @return страница со списком посещений
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("visits", visitRepo.findAll());
        return "visits/list";
    }

    /**
     * Открывает форму добавления нового посещения.
     *
     * В форму передаётся пустой объект посещения,
     * а также списки абонементов и тренеров.
     *
     * @param model объект для передачи данных в представление
     * @return страница формы посещения
     */
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("visit", new Visit());
        model.addAttribute("purchases", purchaseRepo.findAll());
        model.addAttribute("coaches", coachRepo.findAll());
        return "visits/form";
    }

    /**
     * Сохраняет данные о посещении.
     *
     * Перед сохранением выполняются проверки:
     * <ul>
     *   <li>у клиента должен быть купленный абонемент;</li>
     *   <li>дата посещения не может быть раньше даты покупки абонемента;</li>
     *   <li>нельзя указывать будущую дату посещения.</li>
     * </ul>
     *
     * Если проверка не пройдена, форма отображается повторно
     * с сообщением об ошибке.
     *
     * @param visit объект посещения с данными формы
     * @param model объект для передачи данных в представление
     * @return перенаправление на список посещений или возврат формы с ошибкой
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Visit visit, Model model) {

        TicketPurchase purchase = purchaseRepo.findAll().stream()
                .filter(p -> p.getClient().getClientNumber()
                        .equals(visit.getClient().getClientNumber()))
                .findFirst()
                .orElse(null);

        if (purchase == null) {
            model.addAttribute("error", "⚠️ У клиента нет купленного абонемента!");
            return reloadForm(model, visit);
        }

        if (visit.getVisitDate() != null) {
            LocalDate visitDate = visit.getVisitDate().toLocalDate();
            LocalDate today = LocalDate.now();

            if (visitDate.isBefore(purchase.getPurchaseDate())) {
                model.addAttribute(
                        "error",
                        "⛔ Ошибка: Абонемент куплен " + purchase.getPurchaseDate()
                                + ". Нельзя записать на более раннюю дату."
                );
                return reloadForm(model, visit);
            }

            if (visitDate.isAfter(today)) {
                model.addAttribute(
                        "error",
                        "⛔ Ошибка: Нельзя записывать визиты на будущие даты!"
                );
                return reloadForm(model, visit);
            }
        }

        visitRepo.save(visit);
        return "redirect:/visits";
    }

    /**
     * Повторно загружает форму посещения.
     *
     * Используется при возникновении ошибок,
     * чтобы сохранить введённые данные
     * и заново показать форму.
     *
     * @param model объект для передачи данных в представление
     * @param visit объект посещения
     * @return страница формы посещения
     */
    private String reloadForm(Model model, Visit visit) {
        model.addAttribute("visit", visit);
        model.addAttribute("purchases", purchaseRepo.findAll());
        model.addAttribute("coaches", coachRepo.findAll());
        return "visits/form";
    }

    /**
     * Удаляет посещение по его идентификатору.
     *
     * @param id идентификатор посещения
     * @return перенаправление на список посещений
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        visitRepo.deleteById(id);
        return "redirect:/visits";
    }
}
