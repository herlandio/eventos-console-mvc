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

    public void save(Event event) {
        eventRepository.save(event);
    }

    public List<Event> all() {
        return eventRepository.all();
    }

    public void orderEventsBySchedule() {
        System.out.println("Eventos ordenados por horário.");
        Collections.sort(all(), Comparator.comparing(e -> e.getHour().toLocalTime()));
    }

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

    public void checkPastEvents() {
        LocalDateTime now = LocalDateTime.now();
        for (Event event : all()) {
            if (now.isAfter(event.getHour())) {
                System.out.println("Evento passado: " + event.getName());
            }
        }
    }

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

    public Event filterEvents(List<Event> listOfEvents, String eventName) {
        Optional<Event> result = listOfEvents.stream()
                .filter(u -> u.getName().equalsIgnoreCase(eventName))
                .findFirst();

        return result.orElse(null);
    }

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

    public void notifyUserAboutEvent(Event event, List<User> users) {
        users.stream()
                .filter(user -> user.getCity().equalsIgnoreCase(event.getCity()))
                .forEach(user -> {
                    System.out.println("Notificando usuário " + user.getName() + " sobre o evento: " + event.getName());
                    notifyEvent(List.of(event), user);
                });
    }

    public void notifyEvent(List<Event> events, User user) {
        System.out.println("Notificações de Eventos:");
        events.stream()
                .filter(event -> event.getCity().equalsIgnoreCase(user.getCity()))
                .forEach(event -> {
                    System.out.println("Evento na sua cidade: " + event.getName() + ", Local: " + event.getCity());
                });
        System.out.println();
    }

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
