import endpoint.EndPoint;
import json.CreateOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTestColor {
    private String[] color;

    public CreateOrderTestColor(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] sss() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{""}}
        };
    }

    @Test
    public void CreateOrderTestColor() {
        CreateOrder createOrder = new CreateOrder(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                color);
        given()
                .spec(Base.base())
                .body(createOrder)
                .when()
                .post(EndPoint.CREATING_ORDER)
                .then().statusCode(201)
                .and()
                .assertThat().body("track", notNullValue());
    }
}
