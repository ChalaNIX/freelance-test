package ua.hillel.freelance.ui.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.comment.CommentController;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.api.core.job.JobController;
import ua.hillel.freelance.commons.entity.Comment;
import ua.hillel.freelance.commons.entity.Job;
import ua.hillel.freelance.commons.entity.User;
import ua.hillel.freelance.commons.utils.UserProvider;
import ua.hillel.freelance.ui.components.MyJobs;
import ua.hillel.freelance.ui.pages.HomePage;

import java.time.LocalDateTime;
import java.util.Random;

public class FL_UC_004 extends BaseTest {
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

        job = new JobController().createJob(token, job);

        Comment comment = new Comment();
        comment.setMessage("autotest comment");
        new CommentController().createComment(token, job.getId(), comment);
    }

    @Test(dependsOnGroups = {"auth"},
            description = "User can open profile and delete on of his job")
    public void deleteJobTest() {
        MyJobs myJobs = new HomePage().goToLoginPage()
                .login(user)
                .header.goToProfile()
                .myJobs;

        Assert.assertTrue(myJobs.isJobDisplayed(job.getTitle()),
                "Job should be displayed");

        Assert.assertEquals(myJobs.getJobComments(job.getTitle()), 1,
                "Job should have comment");

        myJobs.deleteJob(job.getTitle());
        wait(2);
        Assert.assertFalse(myJobs.isJobDisplayed(job.getTitle()),
                "Job should be deleted");
    }
}
