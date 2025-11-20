package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobject.MainPage;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FAQTest extends BaseTest {
    
    private final int questionIndex;
    private final String expectedText;
    
    public FAQTest(int questionIndex, String expectedText) {
        this.questionIndex = questionIndex;
        this.expectedText = expectedText;
    }
    
    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        return Arrays.asList(new Object[][] {
            {0, "Сутки — 400 рублей."},
            {1, "Пока что у нас так:"},
            {2, "Допустим, вы оформляете заказ на 8 мая."},
            {3, "Только начиная с завтрашнего дня."},
            {4, "Пока что нет!"},
            {5, "Самокат приезжает к вам с полной зарядкой."},
            {6, "Да, пока самокат не привезли."},
            {7, "Да, обязательно."}
        });
    }
    
    @Test
    public void testFAQItem() {
        MainPage mainPage = new MainPage(driver);
        
        // Принимаем куки
        mainPage.acceptCookies();
        
        // Кликаем на вопрос и проверяем ответ через Page Object
        mainPage.clickFaqQuestion(questionIndex);
        String answerText = mainPage.getFaqAnswerText(questionIndex);
        
        assertTrue("Ответ не содержит ожидаемый текст: " + expectedText, 
                   answerText.contains(expectedText));
    }
}
