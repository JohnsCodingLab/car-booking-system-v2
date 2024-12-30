package com.johnscodinglab.user;

import java.util.Objects;
import java.util.UUID;

public class User {
    private String name;
    private final UUID ID;

    public User(UUID ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(ID, user.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ID);
    }
}
