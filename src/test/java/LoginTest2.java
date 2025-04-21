import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import static java.lang.Thread.sleep;

public class LoginTest2 {
    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(); // או כל דפדפן שתרצה להשתמש בו
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    // DataProvider שמספק את הנתונים
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
                {"tomsmith", "SuperSecretPassword!", "https://the-internet.herokuapp.com/login", "Welcome, tomsmith!"},  // הצלחה
                {"invalidUser", "invalidPass", "https://the-internet.herokuapp.com/login", "Invalid username or password"}  // כישלון
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, String url, String expectedMessage) throws InterruptedException {
        driver.get(url);  // האתר שלך

        // הזן שם משתמש וסיסמה
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);

        // לחץ על כפתור הכניסה
        loginPage.clickLoginButton();
        sleep(2000); // מנהל זמן בשניות

        // בדוק את התוצאה המתקבלת
        if (expectedMessage.contains("Welcome")) {
            // אם מדובר בהצלחה, בדוק את דף הבית
            String welcomeMessage = homePage.getWelcomeMessage();
            Assert.assertEquals(welcomeMessage, expectedMessage);
        } else {
            // אם מדובר בכישלון, בדוק את הודעת השגיאה
            String errorMessage = loginPage.getErrorMessage();
            Assert.assertEquals(errorMessage, expectedMessage);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
