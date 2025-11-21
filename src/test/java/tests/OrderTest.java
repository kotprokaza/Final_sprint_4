package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobject.MainPage;
import pageobject.OrderPage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {
    
    private final String orderButtonType;
    private final String name;
    private final String lastName;
    private final String address;
    private final String phone;
    private final String date;
    private final String comment;
    
    public OrderTest(String orderButtonType, String name, String lastName, 
                    String address, String phone, String date, String comment) {
        this.orderButtonType = orderButtonType;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.date = date;
        this.comment = comment;
    }
    
    @Parameterized.Parameters(name = "Заказ через {0} кнопку: {1} {2}")
    public static Collection<Object[]> getData() {
        return Arrays.asList(new Object[][] {
            {"верхнюю", "Иван", "Иванов", "Москва, ул. Ленина, 1", "+79991234567", "25.12.2024", "Позвонить за час"},
            {"нижнюю", "Петр", "Петров", "Санкт-Петербург, Невский пр., 100", "+79997654321", "26.12.2024", "Не звонить"}
        });
    }
    
    @Test
    public void testOrderScooter() {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);
        
        // Принимаем куки
        mainPage.acceptCookies();
        
        // Нажимаем на кнопку заказа в зависимости от типа
        if ("верхнюю".equals(orderButtonType)) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }
        
        // Заполняем первую страницу заказа
        orderPage.fillFirstPage(name, lastName, address, phone);
        orderPage.clickNextButton();
        
        // Заполняем вторую страницу заказа
        orderPage.fillSecondPage(date, comment);
        orderPage.clickOrderButton();
        orderPage.confirmOrder();
        
        // Проверяем успешное оформление заказа
        assertTrue("Заказ не был оформлен успешно", orderPage.isSuccessMessageDisplayed());
    }
}
