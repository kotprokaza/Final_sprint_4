package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    private JavascriptExecutor js;
    
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
    private By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath(".//button[text()='Заказать']");
    private By confirmOrderButton = By.xpath(".//button[text()='Да']");
    private By successMessage = By.xpath(".//div[contains(@class, 'Order_ModalHeader')]");
    
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }
    
    public void fillFirstPage(String name, String lastName, String address, String phone) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(phoneField).sendKeys(phone);
        
        // Выбор станции метро
        selectMetroStation("Сокольники");
        
        // Закрываем выпадающий список метро, кликая на другое поле
        driver.findElement(nameField).click();
    }
    
    private void selectMetroStation(String stationName) {
        // Кликаем на поле выбора метро
        driver.findElement(metroField).click();
        
        // Ждем появления списка станций
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOfElementLocated(By.className("select-search__select")));
        
        // Выбираем станцию по имени
        WebElement stationElement = driver.findElement(By.xpath(String.format("//div[contains(text(), '%s')]", stationName)));
        js.executeScript("arguments[0].click();", stationElement);
    }
    
    public void clickNextButton() {
        // Используем JavaScript для клика, чтобы обойти перекрытие
        WebElement nextButtonElement = driver.findElement(nextButton);
        js.executeScript("arguments[0].click();", nextButtonElement);
    }
    
    public void fillSecondPage(String date, String comment) {
        // Заполняем дату
        WebElement dateElement = driver.findElement(dateField);
        dateElement.clear();
        dateElement.sendKeys(date);
        
        // Закрываем календарь, кликая на другое поле
        driver.findElement(rentalPeriodField).click();
        
        // Выбираем период аренды
        WebElement rentalPeriodElement = driver.findElement(rentalPeriodField);
        js.executeScript("arguments[0].click();", rentalPeriodElement);
        
        WebElement periodOption = driver.findElement(By.xpath(".//div[text()='сутки']"));
        js.executeScript("arguments[0].click();", periodOption);
        
        // Выбираем цвет
        driver.findElement(colorBlackCheckbox).click();
        
        // Заполняем комментарий
        driver.findElement(commentField).sendKeys(comment);
    }
    
    public void clickOrderButton() {
        WebElement orderButtonElement = driver.findElement(orderButton);
        js.executeScript("arguments[0].click();", orderButtonElement);
    }
    
    public void confirmOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        WebElement confirmButtonElement = driver.findElement(confirmOrderButton);
        js.executeScript("arguments[0].click();", confirmButtonElement);
    }
    
    public boolean isSuccessMessageDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        return driver.findElement(successMessage).isDisplayed();
    }
}
