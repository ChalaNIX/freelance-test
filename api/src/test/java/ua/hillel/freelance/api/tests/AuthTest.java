package ua.hillel.freelance.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.commons.utils.UserProvider;
import ua.hillel.freelance.commons.entity.User;

public class AuthTest {

    @Test(groups = {"apiAuth"})
    public void authTest() {
        User user = UserProvider.getUser();

        AuthController authController = new AuthController();
        authController.registerUser(user);
        String token = authController.login(user);

        Assert.assertTrue(token != null && !token.isEmpty(),
                "Api login should be successful");
    }


}
