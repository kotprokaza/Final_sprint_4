package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    
    // Локатор для кнопки "Заказать" вверху страницы
    private By orderButtonTop = By.className("Button_Button__ra12g");
    
    // Локатор для кнопки "Заказать" внизу страницы
    private By orderButtonBottom = By.xpath("//div[contains(@class, 'Home_FinishButton')]/button");
    
    // Локатор для cookie кнопки
    private By cookieButton = By.id("rcc-confirm-button");
    
    // Локаторы для FAQ
    private By faqSection = By.className("Home_FAQ__3uVm4");
    private By faqQuestion = By.xpath("//div[@data-accordion-component='AccordionItemButton']");
    private By faqAnswer = By.xpath("//div[@data-accordion-component='AccordionItemPanel']");
    
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
    
    public void clickFaqQuestion(int index) {
        // Прокручиваем до раздела FAQ
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOfElementLocated(faqSection));
        
        // Кликаем на вопрос по индексу
        driver.findElements(faqQuestion).get(index).click();
    }
    
    public String getFaqAnswerText(int index) {
        // Ждем появления ответа и возвращаем текст
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOfElementLocated(
                By.id("accordion__panel-" + index)
            ));
        return driver.findElement(By.id("accordion__panel-" + index)).getText();
    }
}
