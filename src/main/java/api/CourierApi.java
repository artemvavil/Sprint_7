package api;

import endpoint.EndPoint;
import io.restassured.response.Response;
import json.CreatingCourier;
import json.LoginCourier;

import static io.restassured.RestAssured.given;

public class CourierApi {

    public Response create(Object json) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post(EndPoint.CREATING_COURIER);
    }

    public Response login(LoginCourier login) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post(EndPoint.LOGIN_COURIER);

    }
}
