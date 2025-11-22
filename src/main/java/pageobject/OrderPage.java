package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void fillFirstStep(String firstName, String lastName, String address, String metroStation, String phone) {
        // Заполнение имени
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@placeholder='* Имя']")));
        nameField.sendKeys(firstName);
        
        // Заполнение фамилии
        driver.findElement(By.xpath("//input[@placeholder='* Фамилия']")).sendKeys(lastName);
        
        // Заполнение адреса
        driver.findElement(By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']")).sendKeys(address);
        
        // ВЫБОР СТАНЦИИ МЕТРО
        WebElement metroField = driver.findElement(By.xpath("//input[@placeholder='* Станция метро']"));
        metroField.click();
        
        WebElement stationOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='select-search__select']//button[.//div[text()='Черкизовская']]")));
        stationOption.click();
        
        // Заполнение телефона
        driver.findElement(By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']")).sendKeys(phone);
        
        // Нажатие кнопки Далее
        driver.findElement(By.xpath("//button[text()='Далее']")).click();
    }
    
    public void fillSecondStep(String date, String rentalPeriod, String color, String comment) {
        // Ждем загрузки второй страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@placeholder='* Когда привезти самокат']")));
        
        // ПРОСТОЙ ВЫБОР ДАТЫ
        WebElement dateField = driver.findElement(By.xpath("//input[@placeholder='* Когда привезти самокат']"));
        dateField.sendKeys(date);
        dateField.sendKeys(Keys.ENTER);
        
        // Выбор срока аренды
        driver.findElement(By.className("Dropdown-placeholder")).click();
        WebElement period = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[text()='" + rentalPeriod + "']")));
        period.click();
        
        // ВЫБОР ЦВЕТА
        if ("серая безысходность".equals(color)) {
            driver.findElement(By.id("grey")).click();
        } else if ("чёрный жемчуг".equals(color)) {
            driver.findElement(By.id("black")).click();
        }
        
        // Комментарий
        driver.findElement(By.xpath("//input[@placeholder='Комментарий для курьера']")).sendKeys(comment);
        
        // Нажатие кнопки Заказать
        driver.findElement(By.xpath("//button[contains(@class, 'Button_Middle') and text()='Заказать']")).click();
    }
    
    public void confirmOrder() {
        try {
            System.out.println("Ожидание появления модального окна подтверждения...");
            
            // ПЕРВЫЙ ПРИОРИТЕТ: предложенный локатор
            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Да']")));
            
            System.out.println("Кнопка 'Да' найдена по локатору с Order_Buttons, нажимаем...");
            confirmButton.click();
            
        } catch (Exception e) {
            System.out.println("Первый локатор не сработал: " + e.getMessage());
            
            // ВТОРОЙ ПРИОРИТЕТ: альтернативный локатор
            try {
                WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Да']")));
                
                System.out.println("Кнопка 'Да' найдена по простому локатору, нажимаем...");
                confirmButton.click();
                
            } catch (Exception ex) {
                System.out.println("Второй локатор тоже не сработал: " + ex.getMessage());
                
                // ТРЕТИЙ ПРИОРИТЕТ: локатор с модальным окном
                try {
                    WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class, 'Order_Modal')]//button[text()='Да']")));
                    
                    System.out.println("Кнопка 'Да' найдена по локатору с Order_Modal, нажимаем...");
                    confirmButton.click();
                    
                } catch (Exception exc) {
                    System.out.println("Все локаторы не сработали: " + exc.getMessage());
                }
            }
        }
        
        // Даем время для обработки
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public boolean isSuccessMessageDisplayed() {
        try {
            System.out.println("Проверка сообщения об успешном оформлении заказа...");
            
            // Ждем появления сообщения об успешном оформлении
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Заказ оформлен')]")));
            
            System.out.println("Сообщение об успешном оформлении найдено: " + successMessage.getText());
            return successMessage.isDisplayed();
            
        } catch (Exception e) {
            System.out.println("Сообщение об успешном оформлении не найдено: " + e.getMessage());
            
            // Попробуем альтернативные локаторы
            try {
                WebElement altSuccessMessage = driver.findElement(By.xpath("//div[contains(@class, 'Order_ModalHeader')]"));
                if (altSuccessMessage.getText().contains("Заказ оформлен")) {
                    System.out.println("Альтернативное сообщение найдено: " + altSuccessMessage.getText());
                    return true;
                }
            } catch (Exception ex) {
                System.out.println("Альтернативный локатор тоже не сработал: " + ex.getMessage());
            }
            
            return false;
        }
    }
}
