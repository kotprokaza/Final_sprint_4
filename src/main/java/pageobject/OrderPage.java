package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    
    // Локаторы первой страницы заказа
    private By nameField = By.xpath(".//input[@placeholder='* Имя']");
    private By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroField = By.xpath(".//input[@placeholder='* Станция метро']");
    private By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath(".//button[text()='Далее']");
    
    // Локаторы второй страницы заказа
    private By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodField = By.className("Dropdown-placeholder");
    private By colorBlackCheckbox = By.id("black");
    private By colorGreyCheckbox = By.id("grey");
    private By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath(".//button[text()='Заказать']");
    private By confirmOrderButton = By.xpath(".//button[text()='Да']");
    private By successMessage = By.xpath(".//div[contains(@class, 'Order_ModalHeader')]");
    
    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void fillFirstPage(String name, String lastName, String address, String phone) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(phoneField).sendKeys(phone);
        
        // Выбор станции метро
        driver.findElement(metroField).click();
        driver.findElement(By.xpath(".//button[@value='1']")).click();
    }
    
    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }
    
    public void fillSecondPage(String date, String comment) {
        // Заполняем дату
        driver.findElement(dateField).sendKeys(date);
        
        // Выбираем период аренды
        driver.findElement(rentalPeriodField).click();
        driver.findElement(By.xpath(".//div[text()='сутки']")).click();
        
        // Выбираем цвет
        driver.findElement(colorBlackCheckbox).click();
        
        // Заполняем комментарий
        driver.findElement(commentField).sendKeys(comment);
    }
    
    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }
    
    public void confirmOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        driver.findElement(confirmOrderButton).click();
    }
    
    public boolean isSuccessMessageDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        return driver.findElement(successMessage).isDisplayed();
    }
}
