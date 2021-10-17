package ua.hillel.freelance.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.api.core.user.UserController;
import ua.hillel.freelance.commons.utils.UserProvider;
import ua.hillel.freelance.commons.entity.User;

public class UserTest {
    @Test(dependsOnGroups = {"apiAuth"})
    public void userTest() {
        User user = UserProvider.getUser();

        String token;
        AuthController authController = new AuthController();
        try {
            token = authController.login(user);
        } catch (ApiException e) {
            user = UserProvider.createUser();
            authController.registerUser(user);
            token = authController.login(user);
        }

        UserController userController = new UserController();
        User currentUser = userController.getCurrentUser(token);
        Assert.assertEquals(user.getUsername(), currentUser.getUsername(),
                "Username is incorrect");

        User userById = userController.getUserById(token, currentUser.getId());

        Assert.assertEquals(userById.getUsername(), currentUser.getUsername(),
                "Username is incorrect");

        userById.setName("User1");
        userById.setLastname("Test");

        User updatedUser = userController.updateUser(token, userById);

        Assert.assertEquals(updatedUser.getId(), userById.getId(),
                "User ID is incorrect");
        Assert.assertEquals(updatedUser.getName(), userById.getName(),
                "User name is incorrect");
        Assert.assertEquals(updatedUser.getLastname(), userById.getLastname(),
                "User lastname is incorrect");
    }
}
