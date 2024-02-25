package steps;

import endpoint.EndPoint;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import json.CreatingCourier;
import json.LoginCourier;

import static io.restassured.RestAssured.given;

public class CourierApi extends EndPoint {

    @Step("Cоздание нового курьера")
    public static ValidatableResponse create(CreatingCourier courier) {
        return
                given()
                        .log().all()
                        .spec(getRequestSpec())
                        .and()
                        .body(courier)
                        .when()
                        .post(CREATING_COURIER)
                        .then();
    }

    @Step("Авторизация курьера")
    public static ValidatableResponse login(CreatingCourier courier) {
        LoginCourier loginCourier = new LoginCourier(courier.getLogin(), courier.getPassword());
        return
                given()
                        .spec(getRequestSpec())
                        .header("Content-type", "application/json")
                        .and()
                        .body(loginCourier)
                        .when()
                        .post(LOGIN_COURIER)
                        .then();

    }

    @Step("Удаление курьера")
    public static ValidatableResponse deleteCourier(int idCourier) {
        return given()
                .spec(getRequestSpec())
                .when()
                .delete(DELETE_COURIER + idCourier)
                .then();
    }
}
