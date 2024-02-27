package br.com.herlandio7.repositories;

import java.util.ArrayList;
import java.util.List;

import br.com.herlandio7.models.User;

public class UserRepository {

    private final List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    /**
     * The `save` function adds a User object to a list of users.
     * 
     * @param event The parameter "event" in the "save" method is of type User,
     *              which represents a user
     *              object that is being saved to a collection or database.
     */
    public void save(User event) {
        this.users.add(event);
    }

    /**
     * The above function returns a list of all users.
     * 
     * @return A List of User objects is being returned.
     */
    public List<User> all() {
        return users;
    }
}
