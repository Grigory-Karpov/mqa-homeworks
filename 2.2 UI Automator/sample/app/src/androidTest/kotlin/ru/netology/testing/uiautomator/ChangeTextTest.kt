package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textToSet = "Netology"

//    @Test
//    fun testInternetSettings() {
//        // Press home
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.pressHome()
//
//        // Wait for launcher
//        val launcherPackage = device.launcherPackageName
//        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
//        waitForPackage(SETTINGS_PACKAGE)
//
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val intent = context.packageManager.getLaunchIntentForPackage(SETTINGS_PACKAGE)
//        context.startActivity(intent)
//        device.wait(Until.hasObject(By.pkg(SETTINGS_PACKAGE)), TIMEOUT)
//
//        device.findObject(
//            UiSelector().resourceId("android:id/title").instance(0)
//        ).click()
//    }

//    @Test
//    fun testChangeText() {
//        // Press home
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.pressHome()
//
//        // Wait for launcher
//        val launcherPackage = device.launcherPackageName
//        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
//        waitForPackage(SETTINGS_PACKAGE)
//
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val packageName = context.packageName
//        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
//        context.startActivity(intent)
//        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
//
//
//        device.findObject(By.res(packageName, "userInput")).text = textToSet
//        device.findObject(By.res(packageName, "buttonChange")).click()
//
//        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
//        assertEquals(result, textToSet)
//    }

    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Before
    fun beforeEachTest() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }

    @Test
    fun testInternetSettings() {
        waitForPackage(SETTINGS_PACKAGE)

        device.findObject(
            UiSelector().resourceId("android:id/title").instance(0)
        ).click()
    }

    @Test
    fun testChangeText() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        device.findObject(By.res(packageName, "userInput")).text = textToSet
        device.findObject(By.res(packageName, "buttonChange")).click()

        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
        assertEquals(result, textToSet)
    }
    @Test
    fun testEmptyStringDoesNotChangeText() {
        // Находим элемент TextView, в котором отображается текст, и запоминаем, что там было изначально
        val textToBeChanged = device.findObject(By.res(packageName, "textToBeChanged"))
        val originalText = textToBeChanged.text

        // Находим поле ввода
        val userInput = device.findObject(By.res(packageName, "userInput"))
        // Кликаем по нему (ЛКМ)
        userInput.click()
        // Очищаем, если там что-то было, и вводим пробелы (что считается пустой строкой)
        userInput.clearTextField()
        userInput.text = "      " // несколько пробелов

        // Находим кнопку изменения текста и кликаем по ней
        val buttonChange = device.findObject(By.res(packageName, "buttonChange"))
        buttonChange.click()

        // Проверяем, что текст в TextView остался равен originalText
        assertEquals(originalText, textToBeChanged.text)
    }
    @Test
    fun testOpenTextInNewActivity() {
        // Задаем текст, который будем вводить
        val textToType = "Netology Test"

        // Находим поле ввода, кликаем, очищаем и вводим наш текст
        val userInput = device.findObject(By.res(packageName, "userInput"))
        userInput.click()
        userInput.clearTextField()
        userInput.text = textToType

        // Находим кнопку запуска новой Activity и кликаем по ней
        val buttonActivity = device.findObject(By.res(packageName, "buttonActivity"))
        buttonActivity.click()

        // Ждем, пока на экране появится новая Activity (допустим, ждем появления элемента с ID "text")
        // timeout 5000 миллисекунд (5 секунд)
        device.wait(Until.hasObject(By.res(packageName, "text")), 5000)

        // Находим текстовое поле в новой Activity
        val textInNewActivity = device.findObject(By.res(packageName, "text"))

        // Проверяем, что текст в новой Activity совпадает с тем, что мы ввели
        assertEquals(textToType, textInNewActivity.text)
    }
}



