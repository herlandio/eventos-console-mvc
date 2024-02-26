package br.com.herlandio7;

import java.util.Scanner;

import br.com.herlandio7.controllers.EventsController;
import br.com.herlandio7.repositories.EventRepository;
import br.com.herlandio7.repositories.UserRepository;
import br.com.herlandio7.services.EventService;
import br.com.herlandio7.services.UserService;
import br.com.herlandio7.views.EventView;

public class EventsManagement {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService(new UserRepository());
        var eventos = EventService.loadEventInFile();
        EventService eventService = new EventService(new EventRepository(eventos));
        EventView eventView = new EventView();

        while (true) {
            new EventsController(scanner, userService, eventService, eventView)
                    .menu();
        }
    }
}
