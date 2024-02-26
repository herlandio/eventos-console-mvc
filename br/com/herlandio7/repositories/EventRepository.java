package br.com.herlandio7.repositories;

import java.util.List;

import br.com.herlandio7.models.Event;

public class EventRepository {

    private final List<Event> events;

    public EventRepository(List<Event> event) {
        this.events = event;
    }

    public void save(Event event) {
        this.events.add(event);
    }

    public List<Event> all() {
        return events;
    }

}
