import POM.MainPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LogoUrlTest {
    private static final String SCOOTER_LOGO_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final String YANDEX_LOGO_URL = "https://dzen.ru/";
    private WebDriver driver;

    @Before
    public void before() {
        driver = new FirefoxDriver();
    }

    @Test
    public void testScooterLogoUrl() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.clickScooterLogo();
        String expected = SCOOTER_LOGO_URL;
        String actual = driver.getCurrentUrl();
        Assert.assertEquals("Url не соответствует", expected, actual);
    }

    @Test
    public void testYandexLogoUrl() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.clickYandexLogo();
        String expected = YANDEX_LOGO_URL;
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlMatches(YANDEX_LOGO_URL));
        String actual = driver.getCurrentUrl();
        Assert.assertTrue("Url не соответствует", actual.contains(expected));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}