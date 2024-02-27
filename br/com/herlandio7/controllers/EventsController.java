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

    /**
     * The `menu` function in Java displays a menu of options and executes
     * corresponding actions based
     * on user input using a map of options mapped to Runnable functions.
     */
    public void menu() {
        eventView.menu();
        int option = scanner.nextInt();
        Map<Integer, Runnable> optionsMap = new HashMap<>();
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
        optionsMap.put(0, () -> eventService.saveEventInFile());
        optionsMap.getOrDefault(option, () -> System.out.println("Opção inválida. Tente novamente.")).run();
    }

    /**
     * The `saveEvent` method in Java prompts the user to input event details,
     * creates an Event object,
     * saves it, and notifies users about the event.
     * 
     * @param users The `saveEvent` method you provided is used to save an event and
     *              notify a list of
     *              users about the event. The `users` parameter is a list of `User`
     *              objects that will be notified
     *              about the event once it is saved.
     */
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

    /**
     * The `saveUser` function takes user input for name, city, and age, creates a
     * new User object with
     * the input data, and saves it using a UserService.
     */
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

    /**
     * This Java function allows a user to attend an event by selecting a user and
     * an event from
     * provided lists.
     * 
     * @param scanner      The `Scanner` object is used to read input from the user
     *                     in the console. It
     *                     allows you to read different types of input such as
     *                     strings, integers, and more.
     * @param listOfUsers  A list containing all the users in the system.
     * @param listOfEvents The `listOfEvents` parameter is a List that contains
     *                     Event objects. It is
     *                     used in the `attendAnEvent` method to display a list of
     *                     events for the user to choose from and
     *                     to filter events based on the user's input event name.
     */
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

    /**
     * This Java function allows the user to view confirmed events for a specific
     * user from a list of
     * users.
     * 
     * @param scanner     Scanner is a class in Java that allows you to read input
     *                    from various sources such
     *                    as the console or a file. In this context, the `Scanner
     *                    scanner` parameter in the
     *                    `viewConfirmedEvents` method is used to read user input
     *                    for the name of the user whose confirmed
     *                    events need to be viewed
     * @param listOfUsers A list containing all the users in the system. Each user
     *                    object in the list
     *                    contains information such as name, confirmed events, etc.
     */
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

    /**
     * This Java function allows a user to cancel their participation in an event by
     * entering their
     * name and the event name.
     * 
     * @param scanner      The `Scanner` object is used to read input from the user
     *                     in the console. It
     *                     allows you to read different types of input such as
     *                     strings, integers, etc. In the
     *                     `cancelParticipation` method, the `Scanner` object is
     *                     used to read the user's input for the
     *                     user's
     * @param listOfUsers  A list containing all the users participating in events.
     * @param listOfEvents The `listOfEvents` parameter in the `cancelParticipation`
     *                     method is a List
     *                     that contains Event objects. This list is used to store
     *                     all the events that users can
     *                     participate in. The method takes this list as a parameter
     *                     to be able to search for a specific
     *                     event that the user wants to
     */
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
