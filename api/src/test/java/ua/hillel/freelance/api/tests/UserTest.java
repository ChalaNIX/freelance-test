package ua.hillel.freelance.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.user.UserController;
import ua.hillel.freelance.api.utils.DataProvider;
import ua.hillel.freelance.commons.entity.User;

public class UserTest {
    @Test
    public void userTest() {
        User user = DataProvider.getUser();

        String token = new AuthController().login(user);

        UserController userController = new UserController();
        User currentUser = userController.getCurrentUser(token);
        Assert.assertEquals(user.getUsername(), currentUser.getUsername());

        User userById = userController.getUserById(token, currentUser.getId());

        Assert.assertEquals(userById.getUsername(), currentUser.getUsername());

        userById.setName("User1");
        userById.setLastname("Test");

        User updatedUser = userController.updateUser(token, userById);

        Assert.assertEquals(updatedUser.getId(), userById.getId());
        Assert.assertEquals(updatedUser.getName(), userById.getName());
        Assert.assertEquals(updatedUser.getLastname(), userById.getLastname());
    }
}
