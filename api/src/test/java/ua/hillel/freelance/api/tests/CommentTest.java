package ua.hillel.freelance.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.comment.CommentController;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.api.core.job.JobController;
import ua.hillel.freelance.commons.utils.UserProvider;
import ua.hillel.freelance.commons.entity.Comment;
import ua.hillel.freelance.commons.entity.Job;
import ua.hillel.freelance.commons.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class CommentTest {
    @Test(dependsOnGroups = {"apiAuth"})
    public void commentTest() {
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

        Job job = new Job();
        job.setTitle("Job title " + LocalDateTime.now());
        job.setDescription("Job description " + LocalDateTime.now());
        job.setPrice(new Random().doubles(100, 1000).findFirst().getAsDouble());
        job = new JobController().createJob(token, job);

        CommentController commentController = new CommentController();
        List<Comment> comments = commentController.getAllCommentsForJob(token, job.getId());
        Assert.assertEquals(comments.size(), 0,
                "Comments should not be present");

        Comment newComment = new Comment();
        newComment.setMessage("Comment message " + LocalDateTime.now());

        Comment createComment = commentController.createComment(token, job.getId(), newComment);

        Assert.assertEquals(createComment.getMessage(), newComment.getMessage(),
                "Comment is incorrect");
        Assert.assertNotNull(createComment.getCommentDate(),
                "Comment date should be saved");
        Assert.assertNotNull(createComment.getUsername(),
                "Comment username should be saved");

        Assert.assertEquals(commentController.getAllCommentsForJob(token, job.getId()).size(), comments.size() + 1,
                "Comment should be present");
    }
}
