import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Date;

public class ApiTests {
    @DataProvider(name = "invalidName")
    public static Object[][] invalidName() {
        return new Object[][]{
                {"!@#$%^&*():;'/.?><|"},
                {21},
        };
    }

    @DataProvider(name = "invalidSurname")
    public static Object[][] invalidSurname() {
        return new Object[][]{
                {"!@#$%^&*():;'/.?><|"},
                {21},
        };
    }

    @DataProvider(name = "invalidEmail")
    public static Object[][] emailInvalid() {
        return new Object[][]{
                {"xxx"},
                {"@x.xx"},
                {"x@.xx"},
                {"x@x.x"}};
    }

    @DataProvider(name = "invalidAge")
    public static Object[][] ageInvalid() {
        return new Object[][]{
                {-1},
                {101}};
    }

    @DataProvider(name = "invalidDate")
    public static Object[][] dateInvalid() {
        return new Object[][]{
                {Date.valueOf("0.0.0000")},
                {Date.valueOf("12.12.2200")},
                {Date.valueOf("")}};
    }

    @Test
    public void getUsers() {
        PublicApi publicApi = new PublicApi();
        JSONObject response = publicApi.getUsers();
        Assert.assertTrue(response.getString("users").contains("all"));
    }

    @Test
    public void validToken() {
        PrivateApi privateApi = new PrivateApi();

        Response response = privateApi.postCreate("name", "surname", "example@mail.ru", "Russia",
                "middle", true, 21, "male", Date.valueOf("12.12.2000"), 30000, "valid-authorization-header");
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void invalidToken() {
        PrivateApi privateApi = new PrivateApi();
        Response response = privateApi.postCreate("name", "surname", "example@mail.ru", "Russia",
                "middle", true, 21, "male", Date.valueOf("12.12.2000"), 30000, " invalid-authorization-header");
        Assert.assertEquals(403, response.getStatusCode());
    }

    @Test(dataProvider = "invalidAge")
    public void negativeValueAge(int age) {
        PrivateApi privateApi = new PrivateApi();
        Response response = privateApi.postCreate("name", "surname", "example@mail.ru", "Russia",
                "middle", true, age, "male", Date.valueOf("12.12.2000"), 30000, "valid-authorization-header");
        Assert.assertEquals(422, response.getStatusCode());
    }

    @Test(dataProvider = "invalidEmail")
    public void negativeValueEmail(String email) {
        PrivateApi privateApi = new PrivateApi();
        Response response = privateApi.postCreate("name", "surname", email, "Russia",
                "middle", true, 21, "male", Date.valueOf("12.12.2000"), 30000, "valid-authorization-header");
        Assert.assertEquals(422, response.getStatusCode());
    }

    @Test(dataProvider = "invalidName")
    public void negativeValueName(String name) {
        PrivateApi privateApi = new PrivateApi();
        Response response = privateApi.postCreate(name, "surname", "example@mail.ru", "Russia",
                "middle", true, 21, "male", Date.valueOf("12.12.2000"), 30000, "valid-authorization-header");
        Assert.assertEquals(422, response.getStatusCode());
    }

    @Test(dataProvider = "invalidSurname")
    public void negativeValueSurname(String surname) {
        PrivateApi privateApi = new PrivateApi();
        Response response = privateApi.postCreate("name", surname, "example@mail.ru", "Russia",
                "middle", true, 21, "male", Date.valueOf("12.12.2000"), 30000, "valid-authorization-header");
        Assert.assertEquals(422, response.getStatusCode());
    }

    @Test(dataProvider = "invalidDate")
    public void negativeValueDate(Date date) {
        PrivateApi privateApi = new PrivateApi();
        Response response = privateApi.postCreate("name", "surname", "example@mail.ru", "Russia",
                "middle", true, 21, "male", date, 30000, "valid-authorization-header");
        Assert.assertEquals(422, response.getStatusCode());
    }
}
