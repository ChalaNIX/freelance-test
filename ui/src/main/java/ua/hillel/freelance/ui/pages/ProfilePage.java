package ua.hillel.freelance.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ua.hillel.freelance.ui.components.AddJobForm;
import ua.hillel.freelance.ui.components.EditUserInfoDialog;
import ua.hillel.freelance.ui.components.MyJobs;

import static com.codeborne.selenide.Selenide.$;

public class ProfilePage extends BasePage {
    private final SelenideElement profilePageWrapper = $(".user-profile");
    private final ElementsCollection buttons = profilePageWrapper.$$("button");
    private final SelenideElement userName = profilePageWrapper.$("h3");

    public final MyJobs myJobs;

    public ProfilePage() {
        profilePageWrapper.should(Condition.exist);
        myJobs = new MyJobs();
    }

    public EditUserInfoDialog editInfo() {
        clickButton(getButton("Edit Info"));
        return new EditUserInfoDialog();
    }

    @Step("Close profile and return to the Main page")
    public MainPage closeProfile() {
        clickButton(getButton("Close Profile"));
        return new MainPage();
    }

    @Step("Get current user name and lastname")
    public String getUserName() {
        return userName.text();
    }

    @Step("Click Add Job button and open form")
    public AddJobForm addJob() {
        clickButton(getButton("Add Job"));
        return new AddJobForm();
    }

    private SelenideElement getButton(String buttonName) {
        return buttons.snapshot().filter(Condition.text(buttonName)).get(0);
    }
}
