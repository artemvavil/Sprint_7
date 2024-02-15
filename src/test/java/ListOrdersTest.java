import baseStep.Base;
import endpoint.EndPoint;
import json.CreateOrder;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ListOrdersTest {

    @Before
    public void createOrder() {
        CreateOrder createOrder = new CreateOrder("Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                new String[]{"BLACK"});
        given()
                .spec(Base.base())
                .body(createOrder)
                .when()
                .post(EndPoint.CREATING_ORDER);
    }

    @Test
    public void listOrder() {
        given()
                .spec(Base.base())
                .when()
                .get(EndPoint.CREATING_ORDER)
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("orders.id", notNullValue());
    }
}
