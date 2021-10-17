package ua.hillel.freelance.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ua.hillel.freelance.ui.components.Header;
import ua.hillel.freelance.ui.components.MatCard;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class JobDetailPage extends BasePage {
    private final SelenideElement jobPageWrapper = $(".job-page");
    private final SelenideElement closeDetailsButton = jobPageWrapper.$("button[ng-reflect-router-link='/main']");
    private final SelenideElement commentField = jobPageWrapper.$("textarea");
    private final SelenideElement leaveCommentButton = jobPageWrapper.$("mat-card button");
    private final ElementsCollection comments = $$(".comments mat-card");

    public Header header;

    public JobDetailPage() {
        jobPageWrapper.should(Condition.exist);
    }

    @Step("Close job details and return to the Main page")
    public MainPage closeDetails() {
        clickButton(closeDetailsButton);
        return new MainPage();
    }

    @Step("Leave comment for the job")
    public JobDetailPage leaveComment(String comment) {
        setTextValue(commentField, comment, this).clickButton(leaveCommentButton);
        leaveCommentButton.shouldBe(Condition.disabled);
        return this;
    }

    @Step("Get total number of comments")
    public int getNumberOfComments() {
        return comments.size();
    }

    @Step("Get comment text")
    public String getComment(int commentIndex) {
        return new MatCard(comments.snapshot().get(commentIndex-1)).getMainText();
    }
}
