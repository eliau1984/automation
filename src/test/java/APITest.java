import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class APITest {

    @Test
    public void testCreatePost() {
        // נתוני הבקשה
        String requestBody = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        // ביצוע קריאת POST
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts");

        // בדיקת קוד סטטוס 201
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");

        // בדיקת שהתשובה היא JSON
        Assert.assertTrue(response.getContentType().contains("application/json"), "Response is not in JSON format");

        // בדיקה אם ה-Title שהעברנו הוחזר בהצלחה
        Assert.assertTrue(response.getBody().asString().contains("foo"), "Title 'foo' is missing in response body");
    }
}
