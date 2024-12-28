package POM;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.JavascriptExecutor;

public class MainPage {
    private static final String MAIN_PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final By SCOOTER_LOGO = By.className("Header_LogoScooter__3lsAR");
    private static final By YANDEX_LOGO = By.className("Header_LogoYandex__3TSOI");
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickQuestion(String questionHeader){
        driver.get(MAIN_PAGE_URL);
        WebElement element = driver.findElement(By.xpath(String.format(".//div[text()='%s']", questionHeader)));
        scrollToElement(driver, element);
        element.click();
    }

    public boolean isAnswerFound(String questionAnswer){
        try {
            driver.findElement(By.xpath(String.format(".//p[text()='%s']", questionAnswer)));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAnswerVisible(String questionAnswer){
        WebElement element = driver.findElement(By.xpath(String.format(".//p[text()='%s']", questionAnswer)));
        return element.isDisplayed();
    }

    private void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickScooterLogo() {
        driver.get(MAIN_PAGE_URL);
        driver.findElement(SCOOTER_LOGO).click();
    }

    public void clickYandexLogo() {
        driver.get(MAIN_PAGE_URL);
        driver.findElement(YANDEX_LOGO).click();
        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
    }
}