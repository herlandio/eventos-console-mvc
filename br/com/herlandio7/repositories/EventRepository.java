package br.com.herlandio7.repositories;

import java.util.List;

import br.com.herlandio7.models.Event;

public class EventRepository {

    private final List<Event> events;

    public EventRepository(List<Event> event) {
        this.events = event;
    }

    /**
     * The `save` function adds an `Event` object to a list of events.
     * 
     * @param event The `save` method takes an `Event` object as a parameter and
     *              adds it to a
     *              collection called `events`.
     */
    public void save(Event event) {
        this.events.add(event);
    }

    /**
     * The `all()` function in Java returns a list of all events.
     * 
     * @return A List of Event objects is being returned.
     */
    public List<Event> all() {
        return events;
    }

}
