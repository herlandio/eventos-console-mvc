package br.com.herlandio7.repositories;

import java.util.ArrayList;
import java.util.List;

import br.com.herlandio7.models.User;

public class UserRepository {

    private final List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public void save(User event) {
        this.users.add(event);
    }

    public List<User> all() {
        return users;
    }
}
