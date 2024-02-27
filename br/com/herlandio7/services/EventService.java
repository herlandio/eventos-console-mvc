package br.com.herlandio7.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import br.com.herlandio7.models.Event;
import br.com.herlandio7.models.EventCategory;
import br.com.herlandio7.models.User;
import br.com.herlandio7.repositories.EventRepository;

public class EventService {

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * This Java function saves an Event object using the eventRepository.
     * 
     * @param event The `event` parameter is an object of type `Event`, which
     *              contains information
     *              about a specific event that needs to be saved in the database.
     */
    public void save(Event event) {
        eventRepository.save(event);
    }

    /**
     * The `all()` function returns a list of all events from the event repository.
     * 
     * @return A List of Event objects is being returned.
     */
    public List<Event> all() {
        return eventRepository.all();
    }

    /**
     * The function `orderEventsBySchedule` sorts a list of events by their
     * scheduled time.
     */
    public void orderEventsBySchedule() {
        System.out.println("Eventos ordenados por horário.");
        Collections.sort(all(), Comparator.comparing(e -> e.getHour().toLocalTime()));
    }

    /**
     * The function checks if any events are currently occurring within a two-hour
     * window and prints
     * out their names if so.
     */
    public void checkEventsOccurring() {
        LocalDateTime now = LocalDateTime.now();
        for (Event event : all()) {
            LocalDateTime start = event.getHour();
            LocalDateTime end = start.plusHours(2);
            if (now.isAfter(start) && now.isBefore(end)) {
                System.out.println("Evento ocorrendo agora: " + event.getName());
            }
        }
    }

    /**
     * The function `checkPastEvents` iterates through all events and prints the
     * names of those that
     * have already occurred.
     */
    public void checkPastEvents() {
        LocalDateTime now = LocalDateTime.now();
        for (Event event : all()) {
            if (now.isAfter(event.getHour())) {
                System.out.println("Evento passado: " + event.getName());
            }
        }
    }

    /**
     * The `saveEventInFile` function writes event data to a file in a specific
     * format and handles any
     * IOException that may occur.
     */
    public void saveEventInFile() {
        try (FileWriter fileWriter = new FileWriter("br/com/herlandio7/models/events.data");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (Event event : all()) {
                String data = String.format(
                        "%s;%s;%s;%s;%s%n",
                        event.getName(),
                        event.getCity(),
                        event.getCategory(),
                        event.getHour(),
                        event.getDescription());
                bufferedWriter.write(data);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos no arquivo.");
            e.printStackTrace();
        }
        System.exit(0);
    }

    /**
     * The function `loadEventInFile` reads event data from a file and creates a
     * list of Event objects
     * based on the data.
     * 
     * @return The method `loadEventInFile` returns a List of Event objects that are
     *         read from a file
     *         named "events.data".
     */
    public static List<Event> loadEventInFile() {
        List<Event> events = new ArrayList<>();
        try (FileReader fileReader = new FileReader("br/com/herlandio7/models/events.data");
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                String[] parts = data.split(";");
                String name = parts[0];
                String address = parts[1];
                EventCategory category = EventCategory.valueOf(parts[2]);
                LocalDateTime hour = LocalDateTime.parse(parts[3],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                String description = parts[4];

                Event event = new Event(name, address, category, description, hour);
                events.add(event);
            }
        } catch (IOException e) {
            System.out.println("Arquivo de eventos não encontrado");
        }
        return events;
    }

    /**
     * The function filters a list of events by name and returns the first event
     * with a matching name,
     * or null if none is found.
     * 
     * @param listOfEvents A list of Event objects containing various events.
     * @param eventName    The `eventName` parameter is a `String` representing the
     *                     name of the event that
     *                     you want to filter from the list of events.
     * @return The method `filterEvents` returns an `Event` object that matches the
     *         given `eventName`
     *         from the `listOfEvents`. If no matching event is found, it returns
     *         `null`.
     */
    public Event filterEvents(List<Event> listOfEvents, String eventName) {
        Optional<Event> result = listOfEvents.stream()
                .filter(u -> u.getName().equalsIgnoreCase(eventName))
                .findFirst();
        return result.orElse(null);
    }

    /**
     * The function `attendAnEvent` adds a user to a specified event's participant
     * list if the event
     * exists and the user is not already a participant.
     * 
     * @param listEvent The `listEvent` parameter is an object of the `Event` class,
     *                  which represents
     *                  an event that users can attend.
     * @param user      The `user` parameter represents a user who is attending an
     *                  event.
     */
    public void attendAnEvent(Event listEvent, User user) {
        if (listEvent != null) {
            if (!listEvent.getParticipants().contains(user)) {
                listEvent.getParticipants().add(user);
                System.out.println("Você está participando do evento: " + listEvent.getName());
                user.getConfirmedEvents().add(listEvent);
            } else {
                System.out.println("Você já está participando deste evento.");
            }
        } else {
            System.out.println("Evento não encontrado.");
        }
    }

    /**
     * The function notifies users in the same city as the event about the event.
     * 
     * @param event The `event` parameter represents an event for which users are
     *              being notified. It
     *              likely contains information such as the event name, location
     *              (city), date, and other details.
     * @param users A list of User objects representing the users who should be
     *              notified about the
     *              event.
     */
    public void notifyUserAboutEvent(Event event, List<User> users) {
        users.stream()
                .filter(user -> user.getCity().equalsIgnoreCase(event.getCity()))
                .forEach(user -> {
                    System.out.println("Notificando usuário " + user.getName() + " sobre o evento: " + event.getName());
                    notifyEvent(List.of(event), user);
                });
    }

    /**
     * The function notifies the user of events happening in their city by filtering
     * events based on
     * the user's city and printing event details.
     * 
     * @param events A list of Event objects containing information about various
     *               events.
     * @param user   The `user` parameter is an object of the `User` class. It
     *               likely contains
     *               information about a specific user, such as their city.
     */
    public void notifyEvent(List<Event> events, User user) {
        System.out.println("Notificações de Eventos:");
        events.stream()
                .filter(event -> event.getCity().equalsIgnoreCase(user.getCity()))
                .forEach(event -> {
                    System.out.println("Evento na sua cidade: " + event.getName() + ", Local: " + event.getCity());
                });
        System.out.println();
    }

    /**
     * The function removes a user from an event and prints a message confirming the
     * cancellation.
     * 
     * @param event Event object representing the event from which the user will be
     *              removed.
     * @param user  The `user` parameter represents a user who is being removed from
     *              an event.
     */
    public void removeUserFromEvent(Event event, User user) {
        if (event != null) {
            user.getConfirmedEvents().remove(event);
            System.out.println("Participação cancelada no evento: " + event.getName());
            event.getParticipants().remove(user);
        } else {
            System.out.println("Evento não encontrado na lista de participações confirmadas.");
        }
    }
}
