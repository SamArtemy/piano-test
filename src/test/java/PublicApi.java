import io.restassured.filter.log.LogDetail;
import org.aeonbits.owner.ConfigFactory;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class PublicApi {

    ConfigProperties cfg = ConfigFactory.create(ConfigProperties.class, System.getProperties());
    private final String API_PUBLIC = cfg.apiPublic();


    JSONObject getUsers() {
        String response = given().log().ifValidationFails(LogDetail.URI)
                .expect()
                .when()
                .get(API_PUBLIC + "users")
                .then().log().ifValidationFails(LogDetail.BODY).assertThat().statusCode(200)
                .extract()
                .response().body().asString();
        return new JSONObject(response);
    }

    JSONObject postLogin(String email, String password) {
        JSONObject requestBody = new JSONObject()
                .put("email", email)
                .put("password", password);
        String response = given().log().ifValidationFails(LogDetail.URI)
                .body(requestBody.toString())
                .expect()
                .when()
                .post(API_PUBLIC + "login")
                .then().log().ifValidationFails(LogDetail.BODY).assertThat().statusCode(200).extract().response().body().asString();
        return new JSONObject(response);
    }


}
