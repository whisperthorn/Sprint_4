package POM;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;

public class OrderScooterPage {

    public static final String[] RENT_PERIOD_STRING = new String[]{"сутки", "двое суток", "трое суток", "четверо суток", "пятеро суток", "шестеро суток", "семеро суток"};
    private static final String[] MONTH_NAMES = new String[]{"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
    private static final String MAIN_PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final By UPPER_ORDER_BUTTON = By.xpath(".//div[@class='Header_Nav__AGCXC']//button[@class='Button_Button__ra12g']");
    private static final By LOWER_ORDER_BUTTON = By.xpath(".//div[@class='Home_RoadMap__2tal_']//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private static final By FIRST_NAME_FIELD = By.xpath(".//input[@placeholder='* Имя']");
    private static final By SECOND_NAME_FIELD = By.xpath(".//input[@placeholder='* Фамилия']");
    private static final By ADDRESS_FIELD = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private static final By METRO_STATION_FIELD = By.xpath(".//input[@placeholder='* Станция метро']");
    private static final By PHONE_NUMBER_FIELD = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private static final By NEXT_ORDER_PAGE_BUTTON = By.xpath(".//div[@class='Order_NextButton__1_rCA']//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private static final By ACCEPT_COOKIE_BUTTON = By.className("App_CookieButton__3cvqF");
    private static final By CALENDAR_PREV_MONTH_BUTTON = By.xpath(".//button[contains(@aria-label, 'Previous Month')]");
    private static final By CALENDAR_NEXT_MONTH_BUTTON = By.xpath(".//button[contains(@aria-label, 'Next Month')]");
    private static final By CALENDAR_FIELD = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private static final By CALENDAR = By.className("react-datepicker__month-container");
    private static final By RENT_PERIOD_FIELD = By.xpath(".//div[text()='* Срок аренды']");
    private static final By BLACK_COLOUR_CHECKBOX = By.id("black");
    private static final By GREY_COLOUR_CHECKBOX = By.id("grey");
    private static final By COMMENTARY_FIELD = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private static final By FINISH_ORDER_BUTTON = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    private static final By CONFIRM_ORDER_BUTTON = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");
    private static final By SUCCESSFUL_ORDER_TEXT = By.xpath(".//div[@class='Order_ModalHeader__3FDaJ' and text()='Заказ оформлен']");
    private WebDriver driver;

    public OrderScooterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickMakeOrder(String orderButton){
        driver.get(MAIN_PAGE_URL);
        if(orderButton.equals("верхняяКнопка")){
            driver.findElement(UPPER_ORDER_BUTTON).click();
        } else if (orderButton.equals("нижняяКнопка")) {
            WebElement element = driver.findElement(LOWER_ORDER_BUTTON);
            scrollToElement(driver, element);
            element.click();
        } else {
            driver.findElement(UPPER_ORDER_BUTTON).click();
        }
    }

    public void clickAcceptCookie(){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(ACCEPT_COOKIE_BUTTON));
        driver.findElement(ACCEPT_COOKIE_BUTTON).click();
    }

    public void setFirstName(String firstName){
        driver.findElement(FIRST_NAME_FIELD).sendKeys(firstName);
        }

    public void setSecondName(String secondName){
        driver.findElement(SECOND_NAME_FIELD).sendKeys(secondName);
        }

    public void setAddress(String address){
        driver.findElement(ADDRESS_FIELD).sendKeys(address);
        }

    public void setMetroStation(String metroStation){
        driver.findElement(METRO_STATION_FIELD).click();
        WebElement element = driver.findElement(By.xpath(String.format(".//div[text()='%s']", metroStation)));
        scrollToElement(driver,element);
        element.click();
        }

    public void setPhoneNumber(String phoneNumber){
        driver.findElement(PHONE_NUMBER_FIELD).sendKeys(phoneNumber);
        }

    public void clickNext(){
        driver.findElement(NEXT_ORDER_PAGE_BUTTON).click();
        }

    public void setOrderDate(String orderDate){
        LocalDate currentDate = LocalDate.now();
        LocalDate newDate = calculateNewDate(orderDate, currentDate);

        driver.findElement(CALENDAR_FIELD).click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(CALENDAR));

        selectTargetMonth(currentDate, newDate);

        String[] xpathString = {String.valueOf(newDate.getDayOfMonth()), getMonthName(newDate.getMonthValue()), String.valueOf(newDate.getYear())};
        WebElement element = driver.findElement(By.xpath(String.format(".//div[contains(@aria-label, '%s-е %s %s г.')]", xpathString[0], xpathString[1], xpathString[2])));
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    private void selectTargetMonth(LocalDate currentDate, LocalDate newDate) {
        if(isBeforeMonth(currentDate, newDate)){
            while(isBeforeMonth(currentDate, newDate)){
                driver.findElement(CALENDAR_NEXT_MONTH_BUTTON).click();
                currentDate = currentDate.plusMonths(1);
            }
        } else if (isAfterMonth(currentDate, newDate)) {
            while(isAfterMonth(currentDate, newDate)){
                driver.findElement(CALENDAR_PREV_MONTH_BUTTON).click();
                currentDate = currentDate.minusMonths(1);
            }
        }
    }

    private LocalDate calculateNewDate(String orderDate, LocalDate currentDate) {
        LocalDate newDate = currentDate;
        if(orderDate.equals("следДень")){
            newDate = currentDate.plusDays(1);
            return newDate;
        } else if (orderDate.equals("следНеделя")) {
            newDate = currentDate.plusDays(7);
            return newDate;
        }
        return newDate;
    }

    private String getMonthName(int currentMonth) {
        return MONTH_NAMES[currentMonth-1];
    }

    private boolean isBeforeMonth(LocalDate currentDate, LocalDate newDate) {
        return currentDate.getYear() < newDate.getYear() ||
                (currentDate.getYear() == newDate.getYear() && currentDate.getMonthValue() < newDate.getMonthValue());
    }

    private boolean isAfterMonth(LocalDate currentDate, LocalDate newDate) {
        return currentDate.getYear() > newDate.getYear() ||
                (currentDate.getYear() == newDate.getYear() && currentDate.getMonthValue() > newDate.getMonthValue());
    }

    public void setRentPeriod(int rentPeriod){
        driver.findElement(RENT_PERIOD_FIELD).click();
        WebElement element = driver.findElement(By.xpath(String.format(".//div[text()='%s']", getRentPeriod(rentPeriod))));
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    private String getRentPeriod(int rentPeriod) {
        return RENT_PERIOD_STRING[rentPeriod-1];
    }

    public void setScooterColour(String scooterColour){
        if(scooterColour.equals("Черный")) {
            driver.findElement(BLACK_COLOUR_CHECKBOX).click();
        } else if (scooterColour.equals("Серый")) {
            driver.findElement(GREY_COLOUR_CHECKBOX).click();
        }
    }

    public void setCommentary(String commentary){
        if(!commentary.isEmpty()){
            driver.findElement(COMMENTARY_FIELD).sendKeys(commentary);
            }
        }

    public void clickFinishOrder(){
        driver.findElement(FINISH_ORDER_BUTTON).click();
        }

    public void clickConfirm(){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(CONFIRM_ORDER_BUTTON));
        driver.findElement(CONFIRM_ORDER_BUTTON).click();
        }

    public boolean isOrderSuccessTextVisible(){
        try {
            driver.findElement(SUCCESSFUL_ORDER_TEXT);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void setOrderFirstPage(String firstName, String secondName, String address, String metroStation, String phoneNumber) {
        setFirstName(firstName);
        setSecondName(secondName);
        setAddress(address);
        setMetroStation(metroStation);
        setPhoneNumber(phoneNumber);
        clickNext();
    }

    public void setOrderSecondPage(String orderDate, int rentPeriod, String scooterColour, String commentary) {
        setOrderDate(orderDate);
        setRentPeriod(rentPeriod);
        setScooterColour(scooterColour);
        setCommentary(commentary);
        clickFinishOrder();
    }
}