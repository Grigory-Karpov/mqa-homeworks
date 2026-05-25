import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;

public class SampleAppTest {
    private AppiumDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:deviceName", "Some name"); // Имя эмулятора, Appium найдет любой запущенный
        // Указываем Appium, какое приложение на телефоне мы хотим запустить
        desiredCapabilities.setCapability("appium:appPackage", "ru.netology.testing.uiautomator");
        desiredCapabilities.setCapability("appium:appActivity", "ru.netology.testing.uiautomator.MainActivity");
        desiredCapabilities.setCapability("appium:automationName", "uiautomator2");
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        // URL, на котором работает твой локальный Appium Сервер (запущенный в терминале)
        URL remoteUrl = new URL("http://127.0.0.1:4723");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
    }

    @Test
    public void testEmptyStringDoesNotChangeText() {
        // Находим TextView с результатом
        var textToBeChanged = driver.findElement(By.id("ru.netology.testing.uiautomator:id/textToBeChanged"));
        String originalText = textToBeChanged.getText();

        // Находим поле ввода, кликаем, очищаем и вводим пробелы (пустую строку)
        var userInput = driver.findElement(By.id("ru.netology.testing.uiautomator:id/userInput"));
        userInput.click();
        userInput.clear();
        userInput.sendKeys("      ");

        // Находим кнопку и нажимаем
        var buttonChange = driver.findElement(By.id("ru.netology.testing.uiautomator:id/buttonChange"));
        buttonChange.click();

        // Проверяем, что текст остался прежним
        Assertions.assertEquals(originalText, textToBeChanged.getText());
    }

    @Test
    public void testOpenTextInNewActivity() throws InterruptedException {
        String textToType = "Appium Test Netology";

        // Находим поле ввода, вводим текст
        var userInput = driver.findElement(By.id("ru.netology.testing.uiautomator:id/userInput"));
        userInput.click();
        userInput.clear();
        userInput.sendKeys(textToType);

        // Находим кнопку открытия новой Activity и нажимаем
        var buttonActivity = driver.findElement(By.id("ru.netology.testing.uiautomator:id/buttonActivity"));
        buttonActivity.click();

        // Ставим паузу 3 секунды, чтобы новая Activity успела открыться на экране
        Thread.sleep(3000);

        // Находим текст в новой Activity
        var textInNewActivity = driver.findElement(By.id("ru.netology.testing.uiautomator:id/text"));

        // Сравниваем текст
        Assertions.assertEquals(textToType, textInNewActivity.getText());
    }

    @AfterEach
    public void tearDown() {
        // Закрываем драйвер после каждого теста
        if (driver != null) {
            driver.quit();
        }
    }
}