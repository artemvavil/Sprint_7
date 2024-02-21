import api.CourierApi;
import basestep.Base;
import endpoint.EndPoint;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import json.CreatingCourier;
import json.LoginCourier;
import json.LoginCourierResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class CreatingCourierTest {
    private String login = "ninja";
    private String password = "1234";
    private String firstName = "saske";
    private String index;
    private CourierApi courierApi = new CourierApi();

    @Before
    public void setUp() {
        index = String.valueOf((int) (Math.random() * 1000));
        login += index;
        password += index;
    }

    @Test
    @DisplayName("creatingCourier")
    public void creatingCourier() {
        CreatingCourier creatingCourier = new CreatingCourier(login, "password", "first");
        Response response = courierApi.create(creatingCourier);
        assertEquals(201, response.statusCode());
        response.then().assertThat().body("ok", equalTo(true));

    }

    @Test
    @DisplayName("repeatCourier")
    public void repeatCourier() {
        CreatingCourier creatingCourier = new CreatingCourier(login, password, firstName);
        given()
                .spec(Base.base())
                .body(creatingCourier)
                .when()
                .post(EndPoint.CREATING_COURIER);

        given()
                .spec(Base.base())
                .body(creatingCourier)
                .when()
                .post(EndPoint.CREATING_COURIER)
                .then().statusCode(409);
    }

    @After
    public void cleanUp() {
        LoginCourier getId = new LoginCourier(
                login
                , password);

        LoginCourierResponse idCard =
                given()
                        .spec(Base.base())
                        .body(getId)
                        .post(EndPoint.LOGIN_COURIER)
                        .body().as(LoginCourierResponse.class);

        given()
                .spec(Base.base())
                .body(idCard)
                .delete(EndPoint.DELETE_COURIER + idCard.getId());
    }
}