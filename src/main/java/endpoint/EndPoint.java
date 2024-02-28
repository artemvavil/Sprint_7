package endpoint;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class EndPoint {
    public final static String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    public final static String CREATING_COURIER = "/api/v1/courier";
    public final static String LOGIN_COURIER = "/api/v1/courier/login";
    public final static String CREATING_ORDER = "/api/v1/orders";
    public final static  String DELETE_COURIER = "/api/v1/courier/";

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}





