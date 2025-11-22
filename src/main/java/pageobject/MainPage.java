package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    
    // Локаторы для кнопок заказа
    private final By topOrderButton = By.xpath(".//button[@class='Button_Button__ra12g' and text()='Заказать']");
    private final By bottomOrderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    
    // Локаторы для FAQ
    private final By faqQuestion = By.xpath("//div[@data-accordion-component='AccordionItem']");
    private final By faqAnswer = By.xpath(".//div[@data-accordion-component='AccordionItemPanel']");
    
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }
    
    public void clickOrderButton(boolean useTopButton) {
        WebElement orderButton;
        
        if (useTopButton) {
            orderButton = driver.findElement(topOrderButton);
            orderButton.click();
        } else {
            orderButton = driver.findElement(bottomOrderButton);
            
            // Скроллим к элементу с отступом
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", orderButton);
            
            // Ждем пока элемент станет кликабельным
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(orderButton));
            
            // Добавляем небольшую паузу для стабилизации
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // Пробуем несколько способов клика
            try {
                orderButton.click();
            } catch (Exception e) {
                System.out.println("Обычный клик не сработал, пробуем JavaScript клик: " + e.getMessage());
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", orderButton);
            }
        }
    }
    
    // Методы для FAQ тестов
    public void clickFaqQuestion(int index) {
        WebElement question = driver.findElements(faqQuestion).get(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);
        question.click();
    }
    
    public String getFaqAnswer(int index) {
        WebElement answer = driver.findElements(faqAnswer).get(index);
        return answer.getText();
    }
}
