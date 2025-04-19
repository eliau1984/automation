package com.algosec.registration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FormAutomation {

    public static void main(String[] args) {

        // הגדרת driver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        // יצירת driver ל-Chrome
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        // גישה לדף
        driver.get("http://localhost/form.html");

        // איפוס שדות
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement emailField = driver.findElement(By.name("email"));

        // מילוי הטופס
        nameField.sendKeys("Eli");
        emailField.sendKeys("eli@example.com");

        // שליחת הטופס
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        submitButton.click();

        // בדיקה אם הנתונים הוצגו כראוי
        WebElement confirmationMessage = driver.findElement(By.id("confirmationMessage"));
        if (confirmationMessage.getText().contains("Name: Eli")) {
            System.out.println("Form submission successful!");
        } else {
            System.out.println("Form submission failed.");
        }

        // סגירת הדפדפן
        driver.quit();
    }
}
