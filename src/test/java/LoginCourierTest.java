import baseStep.Base;
import endpoint.EndPoint;
import json.CreatingCourier;
import json.LoginCourier;
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
}
