package com.semini;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {

    WebDriver driver;
    //Declaring a WebDriverWait variable for explicit wait
    WebDriverWait wait;

    @BeforeTest
    public void setup() {
        // ✅ Automatically sets up the ChromeDriver
        WebDriverManager.chromedriver().setup();

        // ✅ Launch browser
        driver = new ChromeDriver();

        // ✅ Maximize window
        driver.manage().window().maximize();
        // ✅ explicit wait (10 seconds)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @Test(priority = 1)
    public void testValidLogin() {
        // ✅ Step 1: Open the login page
        driver.get("https://the-internet.herokuapp.com/login");

        // ✅ Step 2: Enter correct username and password
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");

        // ✅ Step 3: Click login button
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        Assert.assertTrue(message.getText().contains("You logged into a secure area!"));
    }

    @Test(priority = 2)
    public void testInvalidLogin() {
        driver.get("https://the-internet.herokuapp.com/login");

        driver.findElement(By.id("username")).sendKeys("wronguser");
        driver.findElement(By.id("password")).sendKeys("wrongpassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        Assert.assertTrue(message.getText().contains("Your username is invalid!"));
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }
}