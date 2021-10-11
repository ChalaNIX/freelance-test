package ua.hillel.freelance.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.job.JobController;
import ua.hillel.freelance.api.utils.DataProvider;
import ua.hillel.freelance.commons.entity.Job;
import ua.hillel.freelance.commons.entity.User;

import java.util.List;

public class JobTest {
    @Test
    public void jobTest() {
        User user = DataProvider.getUser();

        String token = new AuthController().login(user);

        Job newJob = new Job();
        newJob.setTitle("Test job");
        newJob.setDescription("Test job description");
        newJob.setPrice(500);

        JobController jobController = new JobController();
        Job createdJob = jobController.createJob(token, newJob);

        Assert.assertEquals(createdJob.getTitle(), newJob.getTitle());
        Assert.assertEquals(createdJob.getDescription(), newJob.getDescription());
        Assert.assertEquals(createdJob.getPrice(), newJob.getPrice());

        Job jobById = jobController.getJobById(token, createdJob.getId());

        Assert.assertEquals(jobById.getId(), createdJob.getId());
        Assert.assertEquals(jobById.getTitle(), createdJob.getTitle());
        Assert.assertEquals(jobById.getDescription(), createdJob.getDescription());
        Assert.assertEquals(jobById.getPrice(), createdJob.getPrice());

        List<Job> allJobs = jobController.getAllJobs(token);
        Assert.assertTrue(allJobs.size() >= 1);

        List<Job> allUserJobs = jobController.getAllUserJobs(token);

        Assert.assertTrue(allUserJobs.size() >=  1);

        String result = jobController.deleteJob(token, jobById.getId());
        Assert.assertEquals(result, "Job is deleted");
    }
}
