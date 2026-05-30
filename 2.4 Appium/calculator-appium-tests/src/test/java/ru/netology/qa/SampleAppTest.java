package ru.netology.qa;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SampleAppTest {
    private AppiumDriver driver;
    private SampleAppPage page; 

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:deviceName", "Some name");
        desiredCapabilities.setCapability("appium:appPackage", "ru.netology.testing.uiautomator");
        desiredCapabilities.setCapability("appium:appActivity", "ru.netology.testing.uiautomator.MainActivity");
        desiredCapabilities.setCapability("appium:automationName", "uiautomator2");
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        URL remoteUrl = new URL("http://127.0.0.1:4723");
        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        
        // Включаем неявное ожидание (максимум 10 секунд на поиск любого элемента)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        page = new SampleAppPage(driver);
    }

    @Test
    public void testEmptyStringDoesNotChangeText() {
        String originalText = page.getTextToBeChanged();
        page.changeText("   ");
        Assertions.assertEquals(originalText, page.getTextToBeChanged());
    }

    @Test
    public void testOpenTextInNewActivity() {
        String textToType = "Appium Test Netology";
        page.fillInput(textToType);
        page.openNewActivity();
        
        // Thread.sleep удален, драйвер сам подождет появления новой Activity благодаря implicitlyWait
        
        Assertions.assertEquals(textToType, page.getTextInNewActivity());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}