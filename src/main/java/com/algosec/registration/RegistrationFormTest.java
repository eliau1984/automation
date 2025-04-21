package com.algosec.registration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.sql.*;
import java.time.Duration;

public class RegistrationFormTest {

    public static void main(String[] args) {
        // הגדרת הנתיב ל-Chromedriver
        WebDriverManager.chromedriver().setup();

        // הגדרת אפשרויות לדפדפן
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // יצירת מופע של WebDriver
        WebDriver driver = new ChromeDriver(options);

        // פתיחת טופס ההרשמה המקומי
        driver.get("http://localhost/index.html");

        // יצירת WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // יצירת טבלה אם לא קיימת
            createTable();

            // המתנה לטעינת השדות
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit']")));

            // נתונים לבדיקה
            String testName = "John Doe";
            String testEmail = "johndoe@example.com";

            // שליחת נתונים
            nameField.sendKeys(testName);
            emailField.sendKeys(testEmail);
            submitButton.click();

            // המתנה לעיבוד הנתונים
            Thread.sleep(2000);

            // הוספת הנתונים למסד הנתונים
            insertDataToDatabase(testName, testEmail);

            // בדיקת שמירת הנתונים במסד
            if (checkDataInDatabase(testEmail)) {
                System.out.println("✅ הנתונים נשמרו בהצלחה במסד הנתונים.");
            } else {
                System.out.println("❌ שגיאה: הנתונים לא נמצאו במסד הנתונים.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // סגירת הדפדפן
            driver.quit();
        }
    }

    // פונקציה ליצירת טבלת 'users' אם היא לא קיימת
    public static void createTable() {
        Connection connection = null;
        Statement statement = null;

        try {
            // נתיב למסד הנתונים
            String dbPath = "jdbc:sqlite:C:/Automation/INTERVIEW/users.db";
            connection = DriverManager.getConnection(dbPath);

            // שאילתת יצירת טבלה אם לא קיימת
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "email TEXT NOT NULL UNIQUE);";

            statement = connection.createStatement();
            statement.executeUpdate(createTableSQL);

            System.out.println("✅ טבלת 'users' נוצרה בהצלחה.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // פונקציה להוספת נתונים למסד הנתונים
    public static void insertDataToDatabase(String name, String email) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // נתיב למסד הנתונים
            String dbPath = "jdbc:sqlite:C:/Automation/INTERVIEW/users.db";
            connection = DriverManager.getConnection(dbPath);

            // שאילתת SQL להוספת נתונים
            String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
            statement = connection.prepareStatement(insertSQL);
            statement.setString(1, name);
            statement.setString(2, email);

            statement.executeUpdate();
            System.out.println("✅ הנתונים נוספו למסד הנתונים.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // פונקציה לבדיקת שמירת הנתונים במסד SQLite
    public static boolean checkDataInDatabase(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // נתיב למסד הנתונים
            String dbPath = "jdbc:sqlite:C:/Automation/INTERVIEW/users.db";
            connection = DriverManager.getConnection(dbPath);

            // שאילתת SQL לבדוק אם הנתונים קיימים
            String query = "SELECT * FROM users WHERE email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            // אם נמצא רשומה
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
