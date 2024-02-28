import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import steps.OrderApi;

import static org.hamcrest.Matchers.notNullValue;

public class ListOrdersTest {

    @Test
    @DisplayName("Получение списка заказов")
    public void listOrder() {
        ValidatableResponse response = OrderApi.getOrders();
        response
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat().body("orders.id", notNullValue());
    }
}
