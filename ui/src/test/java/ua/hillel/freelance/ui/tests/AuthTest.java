package ua.hillel.freelance.ui.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.hillel.freelance.commons.entity.User;
import ua.hillel.freelance.commons.utils.UserProvider;
import ua.hillel.freelance.ui.pages.HomePage;
import ua.hillel.freelance.ui.pages.MainPage;

public class AuthTest extends BaseTest {
    private User user;

    @BeforeClass
    public void userSetup() {
        user = UserProvider.getUser();
    }

    @Test(groups = {"auth"}, description = "Registration and Login test")
    public void registerTest() {
        MainPage mainPage = new HomePage().goToRegisterPage()
                .register(user)
                .login(user);

        Assert.assertTrue(mainPage.getAllJobs() > 0,
                "User should be logged and see list of jobs");
    }
}
