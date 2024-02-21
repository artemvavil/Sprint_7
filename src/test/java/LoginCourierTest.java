import basestep.Base;
import endpoint.EndPoint;
import io.qameta.allure.junit4.DisplayName;
import json.CreatingCourier;
import json.LoginCourier;
import json.LoginCourierResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    private String login = "ninja";
    private String password = "1234";
    private String index;

    @Before
    public void setUp() {
        index = String.valueOf((int) (Math.random() * 1000));
        login += index;
        password += index;
        CreatingCourier creatingCourier = new CreatingCourier(login, password, null);

        given()
                .spec(Base.base())
                .body(creatingCourier)
                .when()
                .post(EndPoint.CREATING_COURIER);
    }

    @Test
    @DisplayName("canAuthCourier")
    public void canAuthCourier() {
        LoginCourier loginCourier = new LoginCourier(login, password);
        given()
                .spec(Base.base())
                .body(loginCourier)
                .when()
                .post(EndPoint.LOGIN_COURIER)
                .then().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());
    }

    @Test
    public void errorLogin() {
        LoginCourier loginCourier = new LoginCourier(login + 1, password);
        given()
                .spec(Base.base())
                .body(loginCourier)
                .when()
                .post(EndPoint.LOGIN_COURIER)
                .then().statusCode(404);
    }

    @After
    public void cleanUp() {
        LoginCourier getIdCard = new LoginCourier(
                login
                , password);

        LoginCourierResponse idCard =
                given()
                        .spec(Base.base())
                        .body(getIdCard)
                        .post(EndPoint.LOGIN_COURIER)
                        .body().as(LoginCourierResponse.class);

        given()
                .spec(Base.base())
                .body(idCard)
                .delete(EndPoint.DELETE_COURIER + idCard.getId());
    }
}
