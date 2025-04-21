import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        // הורדה והגדרה של גרסת ChromeDriver קבועה
        WebDriverManager.chromedriver().setup();

        // הפעלת Chrome במצב headless
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
                {"tomsmith", "SuperSecretPassword!", "https://the-internet.herokuapp.com/login", "Welcome, tomsmith!"},
                {"invalidUser", "invalidPass", "https://the-internet.herokuapp.com/login", "Invalid username or password"}
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, String url, String expectedMessage) {
        driver.get(url);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // הגדלת זמן ההמתנה ל-20 שניות

        if (expectedMessage.contains("Welcome")) {
            // מחכה שההודעה Welcome תופיע
            WebElement welcomeMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("welcome-message")));
            String welcomeMessage = welcomeMessageElement.getText();
            Assert.assertEquals(welcomeMessage, expectedMessage);
        } else {
            // מחכה שההודעת שגיאה תופיע
            WebElement errorMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("error-message")));
            String errorMessage = errorMessageElement.getText();
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
