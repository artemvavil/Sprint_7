import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import steps.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import json.CreatingCourier;
import org.junit.Before;
import org.junit.Test;
import steps.CourierGenerator;


import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CreatingCourierTest {

    CreatingCourier courier;
    Integer id;

    @Before
    public void setUp() {
        courier = CourierGenerator.generateRandomCourier();
    }

    @After
    public void cleanUp() {
        if (id != null) {
            CourierApi.deleteCourier(id);
        }
    }

    @Test
    @DisplayName("Создание курьера")
    public void creatingCourier() {
        ValidatableResponse response = CourierApi.create(courier);
        response
                .statusCode(SC_CREATED)
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера с повторяющимся логином")
    public void repeatCourier() {
        CourierApi.create(courier);
        id = CourierApi.login(courier).extract().path("id");
        courier.setPassword("password");
        ValidatableResponse response = CourierApi.create(courier);
        response.statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание нового курьера без логина")
    public void courierCreationWithoutLogin() {
        courier.setLogin(null);
        ValidatableResponse response = CourierApi.create(courier);
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Создание нового курьера без пароля")
    public void courierCreationWithoutPassword() {
        courier.setPassword(null);
        ValidatableResponse response = CourierApi.create(courier);
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}