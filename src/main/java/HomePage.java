import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class HomePage {
    private WebDriver driver;

    // אתרים של אלמנטים בדף הבית
    //private By welcomeMessage = By.className("subheader"); // עדכן את ה-ID בהתאם לדף שלך
    private By welcomeMessage = By.cssSelector("#content > div > h4");


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // פונקציה לשליפת הודעת ברוך הבא
    public String getWelcomeMessage() {
        return driver.findElement(welcomeMessage).getText();
    }
}
