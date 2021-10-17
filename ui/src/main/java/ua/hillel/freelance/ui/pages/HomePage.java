package ua.hillel.freelance.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    private final SelenideElement homePageWrapper = $(".home-page");
    private final SelenideElement loginLink = homePageWrapper.$(By.linkText("Log in"));
    private final SelenideElement registerLink = homePageWrapper.$(By.linkText("Create account"));

    public HomePage() {
        homePageWrapper.should(Condition.exist);
    }

    @Step("Go to Login page")
    public LoginPage goToLoginPage() {
        loginLink.click();
        return new LoginPage();
    }

    @Step("Go to user registration page")
    public RegisterPage goToRegisterPage() {
        registerLink.click();
        return new RegisterPage();
    }
}
