package ua.hillel.freelance.ui.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ua.hillel.freelance.ui.pages.BasePage;
import ua.hillel.freelance.ui.pages.ProfilePage;

import static com.codeborne.selenide.Selenide.$;

public class EditUserInfoDialog extends BasePage {
    private final SelenideElement dialogWrapper = $("mat-dialog-container");
    private final SelenideElement editUserNameInput = dialogWrapper.$("input[formcontrolname='name']");
    private final SelenideElement editUserLastnameInput = dialogWrapper.$("input[formcontrolname='lastname']");
    private final SelenideElement cancelEditButton = dialogWrapper.$("button.mat-button-base:not(.mat-primary)");
    private final SelenideElement updateEditButton = dialogWrapper.$("button.mat-primary");

    public EditUserInfoDialog() {
        dialogWrapper.shouldBe(Condition.exist);
    }

    @Step("Set user name")
    public EditUserInfoDialog setName(String name) {
        return setTextValue(editUserNameInput.should(Condition.visible), name, this);
    }

    @Step("Set user lastname")
    public EditUserInfoDialog setLastname(String lastname) {
        return setTextValue(editUserLastnameInput.should(Condition.visible), lastname, this);
    }

    @Step("Save user info")
    public ProfilePage saveUserInfo() {
        clickButton(updateEditButton);
        updateEditButton.shouldNot(Condition.exist);
        return new ProfilePage();
    }

    @Step("Cancel changes")
    public ProfilePage cancelUpdate() {
        clickButton(cancelEditButton);
        cancelEditButton.shouldNot(Condition.exist);
        return new ProfilePage();
    }
}
