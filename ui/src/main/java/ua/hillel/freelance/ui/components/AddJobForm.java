package ua.hillel.freelance.ui.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ua.hillel.freelance.ui.pages.BasePage;
import ua.hillel.freelance.ui.pages.ProfilePage;

import static com.codeborne.selenide.Selenide.$;

public class AddJobForm extends BasePage {
    private final SelenideElement addJobWrapper = $("app-add-job");
    private final SelenideElement titleInput = addJobWrapper.$("input[formcontrolname='title']");
    private final SelenideElement descTextarea = addJobWrapper.$("textarea[formcontrolname='description']");
    private final SelenideElement priceInput = addJobWrapper.$("input[formcontrolname='price']");
    private final SelenideElement addJobButton = addJobWrapper.$("form button");

    public AddJobForm() {
        addJobWrapper.should(Condition.exist);
    }

    @Step("Set job title")
    public AddJobForm setTitle(String title) {
        return setTextValue(titleInput, title, this);
    }

    @Step("Set job description")
    public AddJobForm setDescription(String description) {
        return setTextValue(descTextarea, description, this);
    }

    @Step("Set job price")
    public AddJobForm setPrice(double price) {
        return setTextValue(priceInput, String.valueOf(price), this);
    }

    @Step("Save new job")
    public ProfilePage addJob() {
        clickButton(addJobButton);
        return new ProfilePage();
    }
}
