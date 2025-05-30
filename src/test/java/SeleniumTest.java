import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.sql.*;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumTest {

    public static void main(String[] args) {
        // אתחול של WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // פתח את הדף עם טופס יצירת משתמש
        driver.get("http://localhost:8080/create-user");

        // חכה שהאלמנט יהיה זמין לפני שממלאים
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));

        // הזן את הנתונים בטופס
        usernameField.sendKeys("new_user");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("submit")).click();

        // תמתין שהשינויים יתעדכנו
        try {
            Thread.sleep(2000); // או השתמש ב- WebDriverWait בהמשך
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
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/eliau/Desktop/Interview/users.db")) {
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
