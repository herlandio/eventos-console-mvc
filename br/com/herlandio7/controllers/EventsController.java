package br.com.herlandio7.controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import br.com.herlandio7.models.Event;
import br.com.herlandio7.models.EventCategory;
import br.com.herlandio7.models.User;
import br.com.herlandio7.services.EventService;
import br.com.herlandio7.services.UserService;
import br.com.herlandio7.views.EventView;

public class EventsController {

    private final Scanner scanner;
    private final UserService userService;
    private final EventService eventService;
    private final EventView eventView;

    public EventsController(Scanner scanner, UserService userService, EventService eventService, EventView eventView) {
        this.scanner = scanner;
        this.userService = userService;
        this.eventService = eventService;
        this.eventView = eventView;
    }

    public void menu() {
        eventView.menu();

        int option = scanner.nextInt();

        Map<Integer, Runnable> optionsMap = new HashMap<>();
        optionsMap.put(0, () -> eventService.saveEventInFile());
        optionsMap.put(1, () -> saveUser());
        optionsMap.put(2, () -> saveEvent(userService.all()));
        optionsMap.put(3, () -> eventView.allUsers(userService.all()));
        optionsMap.put(4, () -> eventView.allEvents(eventService.all(), true));
        optionsMap.put(5, () -> eventService.orderEventsBySchedule());
        optionsMap.put(6, () -> eventService.checkEventsOccurring());
        optionsMap.put(7, () -> eventService.checkPastEvents());
        optionsMap.put(8, () -> attendAnEvent(scanner, userService.all(), eventService.all()));
        optionsMap.put(9, () -> viewConfirmedEvents(scanner, userService.all()));
        optionsMap.put(10, () -> cancelParticipation(scanner, userService.all(), eventService.all()));

        optionsMap.getOrDefault(option, () -> System.out.println("Opção inválida. Tente novamente.")).run();
    }

    public void saveEvent(List<User> users) {
        System.out.println("Cadastro de Evento:");
        System.out.print("Nome do evento: ");
        String name = scanner.next();
        System.out.print("Endereço: ");
        String address = scanner.next();
        System.out.print("Categoria (FESTA, ESPORTIVO, SHOW, OUTRO): ");
        EventCategory category = EventCategory.valueOf(scanner.next().toUpperCase());
        System.out.print("Digite o horário do evento (formato HH:mm): ");
        String inputHour = scanner.next();
        LocalTime formatHour = LocalTime.parse(inputHour, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime currentHour = LocalDateTime.now();
        LocalDateTime hour = LocalDateTime.of(currentHour.getYear(), currentHour.getMonth(),
                currentHour.getDayOfMonth(), formatHour.getHour(), formatHour.getMinute());
        System.out.print("Descrição: ");
        String description = scanner.next();
        scanner.nextLine();

        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setHour(hour);
        event.setCity(address);
        event.setCategory(category);
        eventService.save(event);
        eventService.notifyUserAboutEvent(event, users);

        System.out.println("Evento cadastrado com sucesso!\n");
    }

    public void saveUser() {
        System.out.println("Cadastro de Usuário:");
        System.out.print("Nome: ");
        String name = scanner.next();
        System.out.print("Cidade: ");
        String city = scanner.next();
        System.out.print("Idade: ");
        int age = scanner.nextInt();

        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setCity(city);
        userService.save(user);
        System.out.println("Usuário cadastrado com sucesso!\n");
    }

    public void attendAnEvent(Scanner scanner, List<User> listOfUsers, List<Event> listOfEvents) {
        System.out.print("Digite o nome do usuário: ");
        String inputUserName = scanner.next();
        var user = userService.filterUsers(listOfUsers, inputUserName);

        if (user != null) {

            System.out.println("Escolha um evento para participar:");
            eventView.allEvents(listOfEvents, false);

            System.out.print("Digite o nome do evento: ");
            String eventName = scanner.next();

            var listEvent = eventService.filterEvents(listOfEvents, eventName);
            eventService.attendAnEvent(listEvent, user);

        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    public void viewConfirmedEvents(Scanner scanner, List<User> listOfUsers) {
        System.out.print("Digite o nome do usuário: ");
        String name = scanner.next();

        var user = userService.filterUsers(listOfUsers, name);
        if (user != null) {
            System.out.println("Eventos confirmados para " + user.getName() + ":");
            eventView.allEvents(user.getConfirmedEvents(), false);
        } else {
            System.out.println("Usuário não encontrado");
        }
    }

    public void cancelParticipation(Scanner scanner, List<User> listOfUsers, List<Event> listOfEvents) {
        System.out.print("Digite o nome do usuário: ");
        String name = scanner.next();

        var user = userService.filterUsers(listOfUsers, name);
        if (user != null) {
            eventView.allEvents(user.getConfirmedEvents(), false);

            System.out.print("Digite o nome do evento para cancelar a participação: ");
            String eventName = scanner.next();
            var event = eventService.filterEvents(listOfEvents, eventName);
            eventService.removeUserFromEvent(event, user);

        } else {
            System.out.println("Usuário não encontrado");
        }
    }
}
