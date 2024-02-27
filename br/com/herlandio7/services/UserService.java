package br.com.herlandio7.services;

import java.util.List;
import java.util.Optional;

import br.com.herlandio7.models.User;
import br.com.herlandio7.repositories.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This Java function saves a User object using a repository.
     * 
     * @param event The "event" parameter in the "save" method is an object of type
     *              User, which
     *              represents a user entity that you want to save in the database.
     */
    public void save(User event) {
        userRepository.save(event);
    }

    /**
     * The `all()` function returns a list of all users from the repository.
     * 
     * @return A List of User objects is being returned.
     */
    public List<User> all() {
        return userRepository.all();
    }

    /**
     * The function filters a list of users by a specified username and returns the
     * first matching user
     * or null if no match is found.
     * 
     * @param listOfUsers A list of User objects containing user information.
     * @param userName    The `userName` parameter is a `String` representing the
     *                    name of the user that
     *                    you want to filter from the list of users. The
     *                    `filterUsers` method takes a list of `User`
     *                    objects and filters out the user whose name matches the
     *                    provided `userName` (case-insensitive
     *                    comparison).
     * @return The method `filterUsers` returns a `User` object that matches the
     *         provided `userName`
     *         from the list of users. If no matching user is found, it returns
     *         `null`.
     */
    public User filterUsers(List<User> listOfUsers, String userName) {
        Optional<User> result = listOfUsers.stream()
                .filter(u -> u.getName().equalsIgnoreCase(userName))
                .findFirst();
        return result.orElse(null);
    }
}
