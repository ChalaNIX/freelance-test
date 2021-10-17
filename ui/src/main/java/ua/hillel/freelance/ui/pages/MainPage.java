package ua.hillel.freelance.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ua.hillel.freelance.ui.components.Header;
import ua.hillel.freelance.ui.components.MatCard;
import ua.hillel.freelance.ui.exceptions.JobNotFoundException;

import static com.codeborne.selenide.Selenide.*;

public class MainPage extends BasePage {
    private final SelenideElement mainPageWrapper = $(".index-page");
    private final ElementsCollection jobs = mainPageWrapper.$$("mat-card");

    public Header header;

    public MainPage() {
        mainPageWrapper.should(Condition.exist);
        header = new Header();
    }

    @Step("Check if job is displayed")
    public boolean isJobDisplayed(String jobTitle) {
        return getJobByTitle(jobTitle) != null;
    }

    @Step("Open job details page")
    public JobDetailPage viewJobDetails(String jobTitle) {
        SelenideElement job = getJobByTitle(jobTitle);
        if (job != null) {
            new MatCard(getJobByTitle(jobTitle)).clickViewDetails();
            return new JobDetailPage();
        } else {
            throw new JobNotFoundException("Cannot find job with title: " + jobTitle);
        }

    }

    @Step("Get total number of displayed page")
    public int getAllJobs() {
        return jobs.size();
    }

    private SelenideElement getJobByTitle(String jobTitle) {
        return jobs.snapshot().stream().filter(job -> job.$("mat-card-title").text().equals(jobTitle)).findFirst()
                .orElse(null);
    }
}
