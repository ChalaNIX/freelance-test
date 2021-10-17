package ua.hillel.freelance.ui.components;

import com.codeborne.selenide.SelenideElement;
import ua.hillel.freelance.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.switchTo;

public class MatCard extends BasePage {
    private final SelenideElement mainText;
    private final SelenideElement jobComments;
    private final SelenideElement viewDetailsButton;
    private final SelenideElement deleteJobButton;

    public MatCard(SelenideElement parent) {
        mainText = parent.$("mat-card-content");
        jobComments = parent.$("mat-card-subtitle[align='end']");
        viewDetailsButton = parent.$("button");
        deleteJobButton = parent.$("button.mat-warn");
    }

    public String getMainText() {
        return mainText.text();
    }

    public void clickViewDetails() {
        clickButton(viewDetailsButton);
    }

    public void clickDeleteJob() {
        clickButton(deleteJobButton);
        switchTo().alert().accept();
    }

    public int getComments() {
        return Integer.parseInt(jobComments.text().split(":")[1].trim());
    }
}
