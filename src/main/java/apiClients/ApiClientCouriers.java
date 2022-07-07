package apiClients;

import couriers.Courier;
import couriers.CourierLogin;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import preconditions.PreconditionsEndpoints;

import static io.restassured.RestAssured.given;
import static preconditions.PreconditionsEndpoints.BASEURI;

public class ApiClientCouriers extends ApiClientsScooter {

    public final String PATH = BASEURI + PreconditionsEndpoints.COURIERURI;
    private final String COURIER = "/courier";

    private final String LOGIN_URL = COURIER + "/login";
    private final String DELETE_COURIER_URL = COURIER + "/{courierId}";


    @Step//Создать курьера
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(reqSpec)
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }


    @Step //Логин курьера в системе
    public ValidatableResponse login(CourierLogin courierLogin) {
        return given()
                .spec(reqSpec)
                .body(courierLogin)
                .when()
                .post(LOGIN_URL)
                .then();
    }

    @Step //Удалить курьера
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(reqSpec)
                .when()
                .delete(PATH + courierId)
                .then();
    }


}







