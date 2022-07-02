package ua.hillel.freelance.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ua.hillel.freelance.commons.entity.User;

import static com.codeborne.selenide.Selenide.$;

public class RegisterPage extends BasePage {
    private final SelenideElement registerPageWrapper = $(".register-page");
    private final SelenideElement usernameInput = registerPageWrapper.$("input[formcontrolname='username']");
    private final SelenideElement nameInput = registerPageWrapper.$("input[formcontrolname='name']");
    private final SelenideElement lastnameInput = registerPageWrapper.$("input[formcontrolname='lastname']");
    private final SelenideElement passwordInput = registerPageWrapper.$("input[formcontrolname='password']");
    private final SelenideElement confirmPasswordInput = registerPageWrapper.$("input[formcontrolname='confirmPassword']");
    private final SelenideElement registerButton = registerPageWrapper.$("button");
    private final SelenideElement signInLink = registerPageWrapper.$(By.linkText("Sign In"));

    public RegisterPage() {
        registerPageWrapper.should(Condition.exist);
    }

    @Step("Set username")
    public RegisterPage setUsername(String username) {
        return setTextValue(usernameInput, username, this);
    }

    @Step("Set name")
    public RegisterPage setName(String name) {
        return setTextValue(nameInput, name, this);
    }

    @Step("Set lastname")
    public RegisterPage setLastname(String lastname) {
        return setTextValue(lastnameInput, lastname, this);
    }

    @Step("Set password")
    public RegisterPage setPassword(String password) {
        return setTextValue(passwordInput, password, this);
    }

    @Step("Confirm password")
    public RegisterPage confirmPassword(String confirmPassword) {
        return setTextValue(confirmPasswordInput, confirmPassword, this);
    }

    @Step("Register")
    public LoginPage register() {
        clickButton(registerButton);
        return new LoginPage();
    }

    @Step("Register user")
    public LoginPage register(User user) {
        return setUsername(user.getUsername())
                .setName(user.getName())
                .setLastname(user.getLastname())
                .setPassword(user.getPassword())
                .confirmPassword(user.getConfirmPassword())
                .register();
    }

    @Step("Return to the Login page")
    public LoginPage goToLoginPage() {
        clickButton(signInLink);
        return new LoginPage();
    }
}

