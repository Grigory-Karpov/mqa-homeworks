package ru.netology.qa;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SampleAppPage {
    private AppiumDriver driver;


    private By textToBeChanged = By.id("ru.netology.testing.uiautomator:id/textToBeChanged");
    private By userInput = By.id("ru.netology.testing.uiautomator:id/userInput");
    private By buttonChange = By.id("ru.netology.testing.uiautomator:id/buttonChange");
    private By buttonActivity = By.id("ru.netology.testing.uiautomator:id/buttonActivity");
    private By textInNewActivity = By.id("ru.netology.testing.uiautomator:id/text");

    // Конструктор
    public SampleAppPage(AppiumDriver driver) {
        this.driver = driver;
    }

    // Методы для работы с элементами
    public String getTextToBeChanged() {
        return driver.findElement(textToBeChanged).getText();
    }

    public void changeText(String text) {
        WebElement input = driver.findElement(userInput);
        input.click();
        input.clear();
        input.sendKeys(text);
        driver.findElement(buttonChange).click();
    }

    public void openNewActivity() {
        driver.findElement(buttonActivity).click();
    }

    public String getTextInNewActivity() {
        return driver.findElement(textInNewActivity).getText();
    }

    public void fillInput(String text) {
        WebElement input = driver.findElement(userInput);
        input.click();
        input.clear();
        input.sendKeys(text);
    }
}