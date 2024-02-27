package br.com.herlandio7.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String city;
    private int age;
    private List<Event> confirmEvents = new ArrayList<>();

    public User(String name, String city, int age) {
        this.name = name;
        this.city = city;
        this.age = age;
    }

    public User() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Event> getConfirmedEvents() {
        return confirmEvents;
    }
}
