import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import json.CreatingCourier;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierApi;
import steps.CourierGenerator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    CreatingCourier courier;
    Integer id;

    @Before
    public void setUp() {

        courier = CourierGenerator.generateRandomCourier();
        CourierApi.create(courier);
    }

    @After
    public void cleanUp() {
        if (id != null) {
            CourierApi.deleteCourier(id);
        }
    }

    @Test
    @DisplayName("Возможно авторизоваться")
    public void canAuthCourier() {
        ValidatableResponse response = CourierApi.login(courier);
        response
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация c несуществующим логином и паролем")
    public void notCanAuthCourier() {
        courier.setLogin("Llllogin");
        courier.setPassword("pppppassword");
        ValidatableResponse response = CourierApi.login(courier);
        response
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Неверный логин")
    public void errorLogin() {
        courier.setLogin("LoginLoginLogin");
        ValidatableResponse response = CourierApi.login(courier);
        response
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
    @Test
    @DisplayName("Неверный пароль")
    public void errorPass() {
        courier.setPassword("PasswordPasswordPassword");
        ValidatableResponse response = CourierApi.login(courier);
        response
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация без пароля")
    public void authWithoutPasswordTest() {
        courier.setPassword(null);
        ValidatableResponse response = CourierApi.login(courier);
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация без логина")
    public void authWithoutLoginTest() {
        courier.setLogin(null);
        ValidatableResponse response = CourierApi.login(courier);
        response.statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
