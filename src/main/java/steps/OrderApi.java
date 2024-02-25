package steps;

import endpoint.EndPoint;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;
import json.CreateOrder;

import static io.restassured.RestAssured.given;

public class OrderApi extends EndPoint {
    @Step("Создание заказа")
    public static ValidatableResponse createOrder(CreateOrder order) {
        return given()
                .log().all()
                .spec(getRequestSpec())
                .body(order)
                .when()
                .post(CREATING_ORDER).then();
    }

    @Step("Получение списка заказов")
    public static ValidatableResponse getOrders() {
        return given()
                .log().all()
                .spec(getRequestSpec())
                .when()
                .get(CREATING_ORDER).then();
    }
}
