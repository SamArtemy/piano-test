import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.json.JSONObject;

import java.util.Date;

import static io.restassured.RestAssured.given;

public class PrivateApi {
    ConfigProperties cfg = ConfigFactory.create(ConfigProperties.class, System.getProperties());
    private final String API_PRIVATE = cfg.apiPrivate();

    JSONObject postLogin(String email, String password) {
        JSONObject requestBody = new JSONObject()
                .put("email", email)
                .put("password", password);
        String response = given().log().ifValidationFails(LogDetail.URI)
                .header("token", "api_token")
                .body(requestBody.toString())
                .expect()
                .when()
                .post(API_PRIVATE + "login")
                .then().log().ifValidationFails(LogDetail.BODY).assertThat().statusCode(201).extract().response().body().asString();
        return new JSONObject(response);
    }

    Response postCreate(String name, String surname, String email, String location, String exp, Boolean automation, int age, String gender, Date date, int salary, String token) {
        JSONObject requestBody = new JSONObject()
                .put("first_name", name)
                .put("last_name", surname)
                .put("email", email)
                .put("location", location)
                .put("experience", exp)
                .put("automation", automation)
                .put("age", age)
                .put("gender", gender)
                .put("date of application", date)
                .put("desired salary", salary);
        String response = given().log().ifValidationFails(LogDetail.URI)
                .header("token", token)
                .body(requestBody.toString())
                .expect()
                .when()
                .post(API_PRIVATE + "create")
                .then().log().ifValidationFails(LogDetail.BODY).assertThat().statusCode(201).extract().response().body().asString();
        return (Response) new JSONObject(response);
    }


}
