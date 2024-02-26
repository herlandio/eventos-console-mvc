package br.com.herlandio7.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String address;
    private int age;
    private List<Event> confirmEvents = new ArrayList<>();

    public User(String name, String address, int age) {
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
