package ua.hillel.freelance.ui.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.commons.entity.User;
import ua.hillel.freelance.commons.utils.UserProvider;
import ua.hillel.freelance.ui.pages.HomePage;
import ua.hillel.freelance.ui.pages.ProfilePage;

public class FL_UC_001 extends BaseTest {
    private User user;

    @BeforeClass
    public void setUser() {
        user = UserProvider.getUser();
        AuthController authController = new AuthController();
        try {
            authController.login(user);
        } catch (ApiException e) {
            user = UserProvider.createUser();
            authController.registerUser(user);
        }
    }

    @Test(dependsOnGroups = {"auth"},
            description = "Test that user can navigate to Profile and update Info")
    public void loginAndUpdateUserInfoTest() {
        ProfilePage profilePage = new HomePage().goToLoginPage()
                .login(user)
                .header.goToProfile();

        user = UserProvider.createUser();
        profilePage.editInfo()
                .setName(user.getName())
                .setLastname(user.getLastname())
                .saveUserInfo();

        Assert.assertEquals(profilePage.getUserName(),
                String.format("%s %s", user.getName(), user.getLastname()),
                "New name and last name should be displayed");
    }
}
