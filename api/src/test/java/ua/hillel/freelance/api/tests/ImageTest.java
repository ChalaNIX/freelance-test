package ua.hillel.freelance.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.exception.ApiException;
import ua.hillel.freelance.api.core.image.ImageController;
import ua.hillel.freelance.commons.utils.UserProvider;
import ua.hillel.freelance.commons.entity.Image;
import ua.hillel.freelance.commons.entity.User;

import java.io.File;
import java.net.URISyntaxException;

public class ImageTest {
    @Test(dependsOnGroups = {"apiAuth"})
    public void imageTest() throws URISyntaxException {
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

        ImageController imageController = new ImageController();

        File uploadImage = new File(this.getClass().getClassLoader().getResource("image.jpg").toURI());
        imageController.uploadImage(token, uploadImage);

        Image image = imageController.getUserImage(token);

        Assert.assertEquals(image.getName(), uploadImage.getName(),
                "Image should be saved");
    }
}
