package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * Контроллер для управления клиентами фитнес клуба.
 * </p>
 *
 * <p>
 * Обрабатывает HTTP-запросы, связанные с CRUD-операциями над сущностью {@link Client}:
 * просмотр списка клиентов, добавление, редактирование и удаление.
 * </p>
 *
 * <p>
 * Использует шаблоны представлений Thymeleaf и репозиторий {@link ClientRepository}
 * для взаимодействия с базой данных.
 * </p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-01-01
 */
@Controller
@RequestMapping("/clients")
public class ClientController {

    /**
     * Репозиторий для работы с сущностью {@link Client}.
     * Обеспечивает доступ к операциям сохранения, поиска и удаления клиентов.
     */
    @Autowired
    private ClientRepository clientRepository;

    /**
     * <p>
     * Отображает список всех клиентов фитнес-центра.
     * </p>
     *
     * <p>
     * Метод извлекает всех клиентов из базы данных и передаёт их
     * в модель для отображения на странице.
     * </p>
     *
     * @param model объект {@link Model}, используемый для передачи данных в представление
     * @return имя HTML-шаблона со списком клиентов
     */
    @GetMapping
    public String listClients(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "clients/list";
    }

    /**
     * <p>
     * Отображает форму для добавления нового клиента.
     * </p>
     *
     * <p>
     * В модель добавляется пустой объект {@link Client},
     * который будет заполнен пользователем.
     * </p>
     *
     * @param model объект {@link Model} для передачи данных в представление
     * @return имя HTML-шаблона формы клиента
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        return "clients/form";
    }

    /**
     * <p>
     * Отображает форму редактирования существующего клиента.
     * </p>
     *
     * <p>
     * Клиент извлекается из базы данных по идентификатору.
     * Если клиент не найден, выбрасывается исключение.
     * </p>
     *
     * @param id идентификатор клиента
     * @param model объект {@link Model} для передачи данных в представление
     * @return имя HTML-шаблона формы клиента
     * @throws IllegalArgumentException если клиент с указанным id не найден
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client Id: " + id));
        model.addAttribute("client", client);
        return "clients/form";
    }

    /**
     * <p>
     * Сохраняет данные клиента (добавление или обновление).
     * </p>
     *
     * <p>
     * Перед сохранением выполняется валидация данных.
     * В случае ошибок пользователь возвращается на форму ввода.
     * </p>
     *
     * @param client объект {@link Client}, заполненный данными формы
     * @param result объект {@link BindingResult}, содержащий результаты валидации
     * @return перенаправление на список клиентов или возврат формы при ошибках
     */
    @PostMapping("/save")
    public String saveClient(
            @Valid @ModelAttribute("client") Client client,
            BindingResult result) {

        if (result.hasErrors()) {
            return "clients/form";
        }
        clientRepository.save(client);
        return "redirect:/clients";
    }

    /**
     * <p>
     * Удаляет клиента по идентификатору.
     * </p>
     *
     * <p>
     * После удаления выполняется перенаправление
     * на страницу со списком клиентов.
     * </p>
     *
     * @param id идентификатор клиента
     * @return перенаправление на список клиентов
     */
    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Integer id) {
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }
}
