package steps;

import endpoint.EndPoint;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import json.CreatingCourier;
import json.LoginCourier;

import static io.restassured.RestAssured.given;

public class CourierApi {

    @Step("Cоздание нового курьера")
    public static ValidatableResponse create(CreatingCourier courier) {
        return
                given()
                        .log().all()
                        .spec(EndPoint.getRequestSpec())
                        .and()
                        .body(courier)
                        .when()
                        .post(EndPoint.CREATING_COURIER)
                        .then();
    }

    @Step("Авторизация курьера")
    public static ValidatableResponse login(CreatingCourier courier) {
        LoginCourier loginCourier = new LoginCourier(courier.getLogin(), courier.getPassword());
        return
                given()
                        .spec(EndPoint.getRequestSpec())
                        .header("Content-type", "application/json")
                        .and()
                        .body(loginCourier)
                        .when()
                        .post(EndPoint.LOGIN_COURIER)
                        .then();

    }

    @Step("Удаление курьера")
    public static ValidatableResponse deleteCourier(int idCourier) {
        return given()
                .spec(EndPoint.getRequestSpec())
                .when()
                .delete(EndPoint.DELETE_COURIER + idCourier)
                .then();
    }
}
