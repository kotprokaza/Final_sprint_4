package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;
    
    // Локатор для кнопки "Заказать" вверху страницы
    private By orderButtonTop = By.className("Button_Button__ra12g");
    
    // Локатор для кнопки "Заказать" внизу страницы
    private By orderButtonBottom = By.xpath("//div[contains(@class, 'Home_FinishButton')]/button");
    
    // Локатор для cookie кнопки
    private By cookieButton = By.id("rcc-confirm-button");
    
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }
    
    public void clickOrderButtonBottom() {
        driver.findElement(orderButtonBottom).click();
    }
    
    public void acceptCookies() {
        driver.findElement(cookieButton).click();
    }
}
