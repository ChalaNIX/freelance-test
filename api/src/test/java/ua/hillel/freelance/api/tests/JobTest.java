package ua.hillel.freelance.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.api.core.job.JobController;
import ua.hillel.freelance.commons.utils.UserProvider;
import ua.hillel.freelance.commons.entity.Job;
import ua.hillel.freelance.commons.entity.User;

import java.util.List;

public class JobTest {
    @Test(dependsOnGroups = {"apiAuth"})
    public void jobTest() {
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

        Job newJob = new Job();
        newJob.setTitle("Test job");
        newJob.setDescription("Test job description");
        newJob.setPrice(500);

        JobController jobController = new JobController();
        Job createdJob = jobController.createJob(token, newJob);

        Assert.assertEquals(createdJob.getTitle(), newJob.getTitle(),
                "Job title is incorrect");
        Assert.assertEquals(createdJob.getDescription(), newJob.getDescription(),
                "Job description is incorrect");
        Assert.assertEquals(createdJob.getPrice(), newJob.getPrice(),
                "Job price is incorrect");

        Job jobById = jobController.getJobById(token, createdJob.getId());

        Assert.assertEquals(jobById.getId(), createdJob.getId(), "Job ID is incorrect");
        Assert.assertEquals(jobById.getTitle(), createdJob.getTitle(),
                "Job title is incorrect");
        Assert.assertEquals(jobById.getDescription(), createdJob.getDescription(),
                "Job description is incorrect");
        Assert.assertEquals(jobById.getPrice(), createdJob.getPrice(),
                "Job price is incorrect");

        List<Job> allJobs = jobController.getAllJobs(token);
        Assert.assertTrue(allJobs.size() >= 1,
                "Number of saved jobs is incorrect");

        List<Job> allUserJobs = jobController.getAllUserJobs(token);

        Assert.assertTrue(allUserJobs.size() >=  1,
                "Job should be present");

        String result = jobController.deleteJob(token, jobById.getId());
        Assert.assertEquals(result, "Job is deleted");
    }
}
