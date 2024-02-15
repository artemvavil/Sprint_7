import baseStep.Base;
import endpoint.EndPoint;
import json.CreatingCourier;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreatingCourierTest {
    private String login = "ninja";
    private String password = "1234";
    private String firstName = "saske";
    private String index;

    @Before
    public void setUp() {
        index = String.valueOf((int) (Math.random() * 1000));
        login += index;
        password += index;
    }

    @Test
    public void creatingCourier() {
        CreatingCourier creatingCourier = new CreatingCourier(login, password, firstName);
        given()
                .spec(Base.base())
                .body(creatingCourier)
                .when()
                .post(EndPoint.CREATING_COURIER)
                .then().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));

    }

    @Test
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
}