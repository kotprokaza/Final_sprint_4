package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    // Локаторы
    private final By topOrderButton = By.className("Button_Button__ra12g");
    private final By bottomOrderButton = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    
    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }
    
    public void clickOrderButton(boolean topButton) {
        if (topButton) {
            driver.findElement(topOrderButton).click();
        } else {
            WebElement element = driver.findElement(bottomOrderButton);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
            element.click();
        }
    }
    
    public void clickFaqQuestion(int index) {
        // Скроллим к вопросу
        WebElement question = driver.findElement(By.id("accordion__heading-" + index));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);
        
        // Кликаем на вопрос
        question.click();
    }
    
    public String getFaqAnswer(int index) {
        // Ждем появления ответа и получаем полный текст
        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("accordion__panel-" + index)));
        return answer.getText();
    }
}
