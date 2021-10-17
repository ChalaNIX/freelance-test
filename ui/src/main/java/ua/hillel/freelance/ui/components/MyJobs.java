package ua.hillel.freelance.ui.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ua.hillel.freelance.ui.exceptions.JobNotFoundException;
import ua.hillel.freelance.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class MyJobs extends BasePage {
    private final SelenideElement myJobsWrapper = $("app-my-jobs");
    private final ElementsCollection myJobs = myJobsWrapper.$$("mat-card");

    public MyJobs() {
        myJobsWrapper.should(Condition.exist);
    }

    public int getAllMyJobs() {
        return myJobs.size();
    }

    @Step("Delete job")
    public MyJobs deleteJob(String title) {
        SelenideElement job = getJobByTitle(title);
        if (job != null) {
            new MatCard(job).clickDeleteJob();
            return this;
        } else {
            throw new JobNotFoundException("Cannot find job with title " + title);
        }
    }

    @Step("Get total number of comments for the job")
    public int getJobComments(String jobTitle) {
        SelenideElement job = getJobByTitle(jobTitle);
        if (job != null) {
            return new MatCard(job).getComments();
        } else {
            throw new JobNotFoundException("");
        }
    }

    @Step("Check if job is displayed")
    public boolean isJobDisplayed(String title) {
        return getJobByTitle(title) != null;
    }

    private SelenideElement getJobByTitle(String title) {
        if (myJobs.size() == 0) {
            return null;
        } else {
            return myJobs.snapshot().stream().filter(job -> job.$("mat-card-title").text().equals(title))
                    .findFirst().orElse(null);
        }
    }
}
