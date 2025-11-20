package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FAQTest extends BaseTest {
    
    private final String questionLocator;
    private final String answerLocator;
    private final String expectedText;
    
    public FAQTest(String questionLocator, String answerLocator, String expectedText) {
        this.questionLocator = questionLocator;
        this.answerLocator = answerLocator;
        this.expectedText = expectedText;
    }
    
    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
            {"//div[@id='accordion__heading-0']", "//div[@id='accordion__panel-0']", "Сутки — 400 рублей."},
            {"//div[@id='accordion__heading-1']", "//div[@id='accordion__panel-1']", "Пока что у нас так:"},
            // Добавь остальные вопросы по аналогии
        };
    }
    
    @Test
    public void testFAQItem() {
        // Прокручиваем до раздела FAQ
        driver.findElement(By.xpath(questionLocator)).click();
        
        // Ждем появления ответа
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(answerLocator)));
        
        String actualText = driver.findElement(By.xpath(answerLocator)).getText();
        assertTrue("Текст ответа не содержит ожидаемую строку", 
                   actualText.contains(expectedText));
    }
}
