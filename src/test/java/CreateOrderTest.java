import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import json.CreateOrder;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderApi;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] choiceColor() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{""}}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrderTest() {
        CreateOrder order = new CreateOrder(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                color);
        ValidatableResponse response = OrderApi.createOrder(order);
        response
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .assertThat().body("track", notNullValue());
    }
}
