package ua.hillel.freelance.ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;

public class BaseTest {
    private static Properties remoteProperties;

    @SneakyThrows
    @BeforeMethod
    @Parameters({"url", "browser"})
    public void setUp(String url, String browser) {
        WebDriverManager.chromedriver().setup();

        Configuration.startMaximized = true;
        Configuration.downloadsFolder = Paths.get("target/selenide/downloads").toString();
        Configuration.reportsFolder = Paths.get("target/selenide/reports").toString();

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(false));

        if ("remote".equalsIgnoreCase(browser)) {
            if (remoteProperties == null) {
                remoteProperties = new Properties();
                remoteProperties.load(new FileInputStream(Paths.get("src/test/resources/remote.properties").toFile()));
            }

            String host = remoteProperties.getProperty("selenoid.host");
            String port = remoteProperties.getProperty("selenoid.port");
            Configuration.remote = String.format("http://%s:%s/wd/hub", host, port);
            Map<String, Boolean> options = new HashMap<>();
            options.put("enableVNC", false);
            options.put("enableVideo", false);
            options.put("enableLog", false);
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", options);
            Configuration.browserCapabilities = capabilities;
        }

        open(url);
    }

    @AfterMethod(alwaysRun = true)
    public void checkResult(ITestResult result) {
        if (result.getStatus() != ITestResult.SUCCESS) {
            saveScreenshot(screenshot(OutputType.BYTES));
        }
        Selenide.closeWebDriver();
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    public void wait(int duration) {
        try {
            Thread.sleep(duration*1000);
        } catch (InterruptedException e) {}
    }
}
