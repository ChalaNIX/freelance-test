package ua.hillel.freelance.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ua.hillel.freelance.commons.entity.User;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {
    private final SelenideElement loginPageWrapper = $(".login-page");
    private final SelenideElement usernameInput = loginPageWrapper.$("input[ng-reflect-name='username']");
    private final SelenideElement passwordInput = loginPageWrapper.$("input[ng-reflect-name='password']");
    private final SelenideElement loginButton = loginPageWrapper.$("button");
    private final SelenideElement registerLink = loginPageWrapper.$(By.linkText("Register"));

    public LoginPage() {
        loginPageWrapper.should(Condition.exist);
    }

    @Step("Set username")
    public LoginPage setUsername(String username) {
        return setTextValue(usernameInput, username, this);
    }

    @Step("Set password")
    public LoginPage setPassword(String password) {
        return setTextValue(passwordInput, password, this);
    }

    @Step("Login")
    public MainPage login() {
        clickButton(loginButton);
        return new MainPage();
    }

    @Step("Login")
    public MainPage login(User user) {
        return setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .login();
    }

    @Step("Return to the Registration page")
    public RegisterPage goToRegisterPage() {
        clickButton(registerLink);
        return new RegisterPage();
    }
}
