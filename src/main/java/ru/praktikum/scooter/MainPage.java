package ru.praktikum.scooter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private static final String MAIN_PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final By SCOOTER_LOGO = By.className("Header_LogoScooter__3lsAR");
    private static final By YANDEX_LOGO = By.className("Header_LogoYandex__3TSOI");
    private static final String XPATH_SEARCH_TEXT_HEADER = ".//div[text()='%s']";
    private static final String XPATH_SEARCH_TEXT_ANSWER = ".//p[text()='%s']";
    private static final By UPPER_ORDER_BUTTON = By.xpath(".//div[@class='Header_Nav__AGCXC']//button[@class='Button_Button__ra12g']");
    private static final By LOWER_ORDER_BUTTON = By.xpath(".//div[@class='Home_RoadMap__2tal_']//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private static final By ACCEPT_COOKIE_BUTTON = By.className("App_CookieButton__3cvqF");

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickQuestion(String questionHeader){
        driver.get(MAIN_PAGE_URL);
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_SEARCH_TEXT_HEADER, questionHeader)));
        scrollToElement(driver, element);
        element.click();
    }

    public boolean isAnswerFound(String questionAnswer){
        try {
            driver.findElement(By.xpath(String.format(XPATH_SEARCH_TEXT_ANSWER, questionAnswer)));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAnswerVisible(String questionAnswer){
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_SEARCH_TEXT_ANSWER, questionAnswer)));
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

    public void clickMakeOrder(String orderButton){
        if(orderButton.equals("верхняяКнопка")){
            driver.findElement(UPPER_ORDER_BUTTON).click();
        } else if (orderButton.equals("нижняяКнопка")) {
            WebElement element = driver.findElement(LOWER_ORDER_BUTTON);
            scrollToElement(driver, element);
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } else {
            driver.findElement(UPPER_ORDER_BUTTON).click();
        }
    }

    public void clickAcceptCookie(){
        driver.get(MAIN_PAGE_URL);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(ACCEPT_COOKIE_BUTTON));
        driver.findElement(ACCEPT_COOKIE_BUTTON).click();
    }
}