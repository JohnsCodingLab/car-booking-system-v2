package com.johnscodinglab.user;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    public User getUser(UUID id) {
        List<User> users = getUsers();
        return users.stream().filter(user -> user.getID().equals(id)).findFirst().orElse(null);
    }
}
