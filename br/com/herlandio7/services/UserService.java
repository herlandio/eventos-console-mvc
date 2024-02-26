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

    public void save(User event) {
        userRepository.save(event);
    }

    public List<User> all() {
        return userRepository.all();
    }

    public User filterUsers(List<User> listOfUsers, String userName) {
        Optional<User> result = listOfUsers.stream()
                .filter(u -> u.getName().equalsIgnoreCase(userName))
                .findFirst();

        return result.orElse(null);
    }
}
