package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobject.MainPage;
import pageobject.OrderPage;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private final String browser;
    private final String buttonPosition;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String color;
    private final String comment;

    public OrderTest(String browser, String buttonPosition, String firstName, String lastName,
                     String address, String metroStation, String phone,
                     String date, String rentalPeriod, String color, String comment) {
        this.browser = browser;
        this.buttonPosition = buttonPosition;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        "chrome", "upper",
                        "Дима", "Иванов", "ул. Потапова Д 1", "Черкизовская", "81234567899",
                        "28.10.2025", "двое суток", "серая безысходность", "доставить вместе со шлемом"
                },
                {
                        "chrome", "down",
                        "Вася", "Васильев", "пр-т Анкина Д 12", "Черкизовская", "89876543211",
                        "28.10.2025", "двое суток", "серая безысходность", "доставить вместе со шлемом"
                },
                {
                        "firefox", "upper",
                        "Дима", "Иванов", "ул. Потапова Д 1", "Черкизовская", "81234567899",
                        "28.10.2025", "двое суток", "серая безысходность", "доставить вместе со шлемом"
                },
                {
                        "firefox", "down",
                        "Вася", "Васильев", "пр-т Анкина Д 12", "Черкизовская", "89876543211",
                        "28.10.2025", "двое суток", "серая безысходность", "доставить вместе со шлемом"
                }
        });
    }

    @Test
    public void testOrder() {
        // Устанавливаем системное свойство для текущего теста
        System.setProperty("browser", browser);

        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        mainPage.open();

        boolean useTopButton = "upper".equals(buttonPosition);
        mainPage.clickOrderButton(useTopButton);

        orderPage.fillFirstStep(firstName, lastName, address, metroStation, phone);
        orderPage.fillSecondStep(date, rentalPeriod, color, comment);
        orderPage.confirmOrder();

        assertTrue("Заказ не был оформлен успешно в " + browser, orderPage.isSuccessMessageDisplayed());
    }
}
