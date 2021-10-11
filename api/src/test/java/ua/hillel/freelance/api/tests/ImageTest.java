package ua.hillel.freelance.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.hillel.freelance.api.core.auth.AuthController;
import ua.hillel.freelance.api.core.image.ImageController;
import ua.hillel.freelance.api.utils.DataProvider;
import ua.hillel.freelance.commons.entity.Image;
import ua.hillel.freelance.commons.entity.User;

import java.io.File;
import java.net.URISyntaxException;

public class ImageTest {
    @Test
    public void imageTest() throws URISyntaxException {
        User user = DataProvider.getUser();

        String token = new AuthController().login(user);

        ImageController imageController = new ImageController();

        File uploadImage = new File(this.getClass().getClassLoader().getResource("image.jpg").toURI());
        imageController.uploadImage(token, uploadImage);

        Image image = imageController.getUserImage(token);

        Assert.assertEquals(image.getName(), uploadImage.getName());
    }
}
