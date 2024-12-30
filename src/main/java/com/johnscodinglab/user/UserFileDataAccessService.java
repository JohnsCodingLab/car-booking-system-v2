package com.johnscodinglab.user;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserFileDataAccessService implements UserDAO {

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try (InputStream file = getClass().getClassLoader().getResourceAsStream("users.csv")) {
            if (file == null) {
                throw new IllegalStateException("File not found: users.csv");
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    String[] user = scanner.nextLine().split(",");
                    users.add(new User(UUID.fromString(user[0]), user[1]));
                }
            }

            return users;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
