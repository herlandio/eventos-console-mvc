package br.com.herlandio7.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String city;
    private EventCategory category;
    private String description;
    private LocalDateTime hour;
    private List<User> participants = new ArrayList<>();

    public Event(String name, String city, EventCategory category, String description, LocalDateTime hour) {
        this.name = name;
        this.city = city;
        this.category = category;
        this.description = description;
        this.hour = hour;
    }

    public Event() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public List<User> getParticipants() {
        return participants;
    }
}
