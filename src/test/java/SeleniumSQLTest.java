import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.*;

public class SeleniumSQLTest {

    public static void main(String[] args) {
        // אתחול של WebDriver
        WebDriver driver = new ChromeDriver();

        // פתח את הדף עם טופס יצירת משתמש
        driver.get("https://yourwebsite.com/create-user");

        // הזן את הנתונים בטופס
        driver.findElement(By.id("username")).sendKeys("new_user");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("submit")).click();

        // תמתין שהשינויים יתעדכנו
        try {
            Thread.sleep(2000); // שינה למוד אוטומטי עם WebDriverWait בהמשך
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // בדיקה אם המשתמש אכן הוסף
        checkUserInDatabase("new_user");

        // סגור את הדפדפן
        driver.quit();
    }

    public static void checkUserInDatabase(String username) {
        // התחבר למסד הנתונים
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:your-database.db")) {
            Statement stmt = connection.createStatement();

            // כתוב את השאילתא לבדיקת המשתמש
            String query = "SELECT * FROM users WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);

            // אם יש תוצאה, זה אומר שהמשתמש הוסף
            if (rs.next()) {
                System.out.println("User exists in database: " + rs.getString("username"));
            } else {
                System.out.println("User not found in database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
