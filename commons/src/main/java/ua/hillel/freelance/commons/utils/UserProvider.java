package ua.hillel.freelance.commons.utils;

import com.github.javafaker.Faker;
import ua.hillel.freelance.commons.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserProvider {
    private static UserProvider instance;

    List<User> users;

    private UserProvider() {
        users = new ArrayList<>();
    }

    public static UserProvider getInstance() {
        if (instance == null) {
            instance = new UserProvider();
        }
        return instance;
    }

    public static User getUser() {
        if (getInstance().users.size() == 0) {
            createUser();
        }
        return getInstance().users.get(getInstance().users.size()-1);
    }

    public static User createUser() {
        User user = new User();
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String username = name + "_" + lastname;
        String password = "pass12345";

        user.setUsername(username);
        user.setName(name);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setConfirmPassword(password);

        getInstance().users.add(user);

        return user;
    }
}
