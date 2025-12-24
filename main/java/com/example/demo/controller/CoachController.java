package com.example.demo.controller;

import com.example.demo.model.Coach;
import com.example.demo.repository.CoachRepository;
import com.example.demo.repository.SpecializationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * Контроллер для управления тренерами фитнес-центра.
 * </p>
 *
 * <p>
 * Обеспечивает обработку HTTP-запросов, связанных с сущностью {@link Coach},
 * включая просмотр списка тренеров, создание новых записей,
 * редактирование существующих и удаление.
 * </p>
 *
 * <p>
 * Взаимодействует с репозиториями {@link CoachRepository} и
 * {@link SpecializationRepository} для работы с базой данных и
 * справочником специализаций тренеров.
 * </p>
 *
 * <p>
 * Контроллер использует шаблонизатор Thymeleaf для отображения данных
 * на веб-страницах.
 * </p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-01-01
 */
@Controller
@RequestMapping("/coaches")
public class CoachController {

    /**
     * Репозиторий для выполнения CRUD-операций над сущностью {@link Coach}.
     */
    @Autowired
    private CoachRepository coachRepository;

    /**
     * Репозиторий для доступа к списку специализаций тренеров.
     */
    @Autowired
    private SpecializationRepository specializationRepository;

    /**
     * <p>
     * Отображает список всех тренеров фитнес-центра.
     * </p>
     *
     * <p>
     * Метод извлекает данные о тренерах из базы данных и
     * передаёт их в модель для отображения на странице.
     * </p>
     *
     * @param model объект {@link Model} для передачи данных в представление
     * @return имя HTML-шаблона со списком тренеров
     */
    @GetMapping
    public String listCoaches(Model model) {
        model.addAttribute("coaches", coachRepository.findAll());
        return "coaches/list";
    }

    /**
     * <p>
     * Отображает форму добавления нового тренера.
     * </p>
     *
     * <p>
     * В модель добавляется пустой объект {@link Coach},
     * а также список доступных специализаций,
     * необходимых для заполнения формы.
     * </p>
     *
     * @param model объект {@link Model} для передачи данных в представление
     * @return имя HTML-шаблона формы тренера
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("coach", new Coach());
        model.addAttribute("specializations", specializationRepository.findAll());
        return "coaches/form";
    }

    /**
     * <p>
     * Отображает форму редактирования существующего тренера.
     * </p>
     *
     * <p>
     * Тренер извлекается из базы данных по идентификатору.
     * Также в модель передаётся список специализаций
     * для корректного отображения формы.
     * </p>
     *
     * @param id идентификатор тренера
     * @param model объект {@link Model} для передачи данных в представление
     * @return имя HTML-шаблона формы тренера
     * @throws java.util.NoSuchElementException если тренер не найден
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Coach coach = coachRepository.findById(id).orElseThrow();
        model.addAttribute("coach", coach);
        model.addAttribute("specializations", specializationRepository.findAll());
        return "coaches/form";
    }

    /**
     * <p>
     * Сохраняет данные тренера (создание или обновление).
     * </p>
     *
     * <p>
     * Перед сохранением выполняется валидация данных.
     * При наличии ошибок пользователь возвращается
     * на страницу формы с сохранением списка специализаций.
     * </p>
     *
     * @param coach объект {@link Coach}, заполненный данными формы
     * @param result объект {@link BindingResult}, содержащий результаты валидации
     * @param model объект {@link Model} для передачи данных в представление
     * @return перенаправление на список тренеров или возврат формы при ошибках
     */
    @PostMapping("/save")
    public String saveCoach(
            @Valid @ModelAttribute("coach") Coach coach,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("specializations", specializationRepository.findAll());
            return "coaches/form";
        }
        coachRepository.save(coach);
        return "redirect:/coaches";
    }

    /**
     * <p>
     * Удаляет тренера по идентификатору.
     * </p>
     *
     * <p>
     * Операция выполняется в рамках транзакции.
     * Если тренер с указанным идентификатором найден,
     * он будет удалён из базы данных.
     * </p>
     *
     * @param id идентификатор тренера
     * @return перенаправление на страницу со списком тренеров
     */
    @Transactional
    @GetMapping("/delete/{id}")
    public String deleteCoach(@PathVariable("id") Integer id) {
        coachRepository.findById(id)
                .ifPresent(coach -> coachRepository.delete(coach));
        return "redirect:/coaches";
    }
}
