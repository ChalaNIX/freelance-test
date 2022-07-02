package ua.hillel.freelance.ui.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.api.core.job.JobController;
import ua.hillel.freelance.commons.entity.Job;
import ua.hillel.freelance.commons.entity.User;
import ua.hillel.freelance.commons.utils.UserProvider;
import ua.hillel.freelance.ui.pages.HomePage;
import ua.hillel.freelance.ui.pages.JobDetailPage;

import java.time.LocalDateTime;
import java.util.Random;

public class FL_UC_003 extends BaseTest {
    private User user;
    private Job job;

    @BeforeClass
    public void setData() {
        user = UserProvider.getUser();
        AuthController authController = new AuthController();
        String token;
        try {
             token = authController.login(user);
        } catch (ApiException e) {
            user = UserProvider.createUser();
            authController.registerUser(user);
            token = authController.login(user);
        }

        job = new Job();
        job.setTitle("New job " + LocalDateTime.now());
        job.setDescription("New job created by autotest");
        job.setPrice(new Random().nextInt(20) * 100.0);

        new JobController().createJob(token, job);
    }

    @Test(dependsOnGroups = {"auth"},
            description = "User can open job details and leave a comment for it")
    public void commentJobTest() {
        String comment = "auto comment " + LocalDateTime.now();

        JobDetailPage jobDetailPage = new HomePage().goToLoginPage()
                .login(user)
                .viewJobDetails(job.getTitle())
                .leaveComment(comment);

        wait(2);
        Assert.assertEquals(jobDetailPage.getNumberOfComments(), 1,
                "Comment should be displayed");
        Assert.assertEquals(jobDetailPage.getComment(1), comment,
                "Wrong comment is displayed");
    }
}
