import org.openqa.selenium.chrome.ChromeDriver;
import ru.praktikum.scooter.MainPage;
import ru.praktikum.scooter.OrderScooterPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

@RunWith(Parameterized.class)
public class OrderPagePositiveFlowTest {
    private WebDriver driver;
    private final String orderButton;
    private final String firstName;
    private final String secondName;
    private final String address;
    private final String metroStation;
    private final String phoneNumber;
    private final String orderDate;
    private final int rentPeriod;
    private final String scooterColour;
    private final String commentary;

    public OrderPagePositiveFlowTest(String orderButton, String firstName, String secondName, String address,
                                     String metroStation, String phoneNumber, String orderDate, int rentPeriod,
                                     String scooterColour, String commentary) {
        this.orderButton = orderButton;
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.orderDate = orderDate;
        this.rentPeriod = rentPeriod;
        this.scooterColour = scooterColour;
        this.commentary = commentary;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"верхняяКнопка", "Евгений", "Завгородний", "Серпуховский Вал", "Бунинская аллея", "+79784653298",
                        "следДень", 3, "Черный", ""},
                {"нижняяКнопка", "Михаил", "Булочкин", "Тверская, д.1", "Ломоносовский проспект", "89752100347",
                        "следНеделя", 6, "Любой", "Доставить не раньше обеда."}
        };
    }

    @Before
    public void before() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @Test
    public void testMakeOrder() {
        OrderScooterPage objOrderPage = new OrderScooterPage(driver);
        MainPage objMainpage = new MainPage(driver);
        objMainpage.clickAcceptCookie();
        objMainpage.clickMakeOrder(orderButton);
        objOrderPage.setOrderFirstPage(firstName, secondName, address, metroStation, phoneNumber);
        objOrderPage.setOrderSecondPage(orderDate, rentPeriod, scooterColour, commentary);
        objOrderPage.clickConfirm();
        Assert.assertTrue("Сообщение об удачном заказе не найдено", objOrderPage.isOrderSuccessTextVisible());
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}