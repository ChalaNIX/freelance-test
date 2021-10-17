package ua.hillel.freelance.ui.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.commons.entity.Job;
import ua.hillel.freelance.commons.entity.User;
import ua.hillel.freelance.commons.utils.UserProvider;
import ua.hillel.freelance.ui.pages.HomePage;
import ua.hillel.freelance.ui.pages.ProfilePage;

import java.time.LocalDateTime;
import java.util.Random;

public class FL_UC_002 extends BaseTest {
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
            description = "User can open Profile page and create new job, this job should be displayed under My Jobs section")
    public void createNewJobTest() {
        String jobTitle = "New job " + LocalDateTime.now();
        Job job = new Job();
        job.setTitle(jobTitle);
        job.setDescription("Job created by autotest");
        job.setPrice(new Random().nextInt(20) * 100.0);

        ProfilePage profilePage = new HomePage().goToLoginPage()
                .login(user)
                .header.goToProfile()
                .addJob()
                .setTitle(job.getTitle())
                .setDescription(job.getDescription())
                .setPrice(job.getPrice())
                .addJob();

        Assert.assertTrue(profilePage.myJobs.isJobDisplayed(job.getTitle()),
                "Newly created job should be displayed");
    }
}
