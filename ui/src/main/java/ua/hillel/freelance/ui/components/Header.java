package ua.hillel.freelance.ui.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ua.hillel.freelance.ui.pages.BasePage;
import ua.hillel.freelance.ui.pages.HomePage;
import ua.hillel.freelance.ui.pages.MainPage;
import ua.hillel.freelance.ui.pages.ProfilePage;

import static com.codeborne.selenide.Selenide.$;

public class Header extends BasePage {
    private SelenideElement headerWrapper = $("mat-toolbar");
    private SelenideElement mainPageLink = headerWrapper.$("span[routerlink]");
    private SelenideElement profileButton = headerWrapper.$("button[mattooltip='Profile']");
    private SelenideElement profileMenuItem = $(".mat-menu-content button:nth-of-type(1)");
    private SelenideElement logoutMenuItem = $(".mat-menu-content button:nth-of-type(2)");

    public Header() {
        headerWrapper.should(Condition.exist);
    }

    @Step("Return to the Main page")
    public MainPage returnToMainPage() {
        clickButton(mainPageLink);
        return new MainPage();
    }

    @Step("Logout")
    public HomePage logout() {
        clickButton(profileButton);
        clickButton(logoutMenuItem.should(Condition.exist));
        return new HomePage();
    }

    @Step("Go to the Profile")
    public ProfilePage goToProfile() {
        clickButton(profileButton);
        clickButton(profileMenuItem.should(Condition.exist));
        return new ProfilePage();
    }
}
