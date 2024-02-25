package steps;

import json.CreatingCourier;

import static org.example.RandomString.randomString;

public class CourierGenerator {
    public static CreatingCourier generateRandomCourier() {
        CreatingCourier courier = new CreatingCourier();
        courier.setLogin(randomString(8));
        courier.setPassword(randomString(10));
        courier.setFirstName(randomString(11));
        return courier;
    }
}

