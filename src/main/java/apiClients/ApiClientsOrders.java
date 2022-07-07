package apiClients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import orders.CreateOrder;
import orders.OrderResponseSuccess;
import orders.OrderTrack;
import preconditions.PreconditionsEndpoints;

import static io.restassured.RestAssured.given;

public class ApiClientsOrders extends ApiClientsScooter {
    private static final String ORDER_PATH = "/api/v1/orders";
    public final String PATH = PreconditionsEndpoints.BASEURI + PreconditionsEndpoints.ORDERSURI;
    private final String ORDER_URL = "/orders";
    private final String ACCEPT_ID_ORDER_URL = ORDER_URL + "/accept/{id}";

    private final String GET_ORDER_URL = ORDER_URL + "/track";
    private final String CANCEL_ORDER_URL = ORDER_URL + "/cancel";

    @Step //Получить список заказов
    public Response getListOfOrders() {
        return given()
                .spec(reqSpec)
                .when()
                .get(PATH);
    }

    @Step //Принять заказ по id
    public ValidatableResponse acceptOrder(int orderId) {
        return given()
                .spec(reqSpec)
                .when()
                .put(PATH + "/accept/" + orderId)
                .then();
    }

    @Step //Принять заказ по его id и id курьера
    public ValidatableResponse acceptOrders(int orderId, int courierId) {
        return given()
                .spec(reqSpec)
                .when()
                .queryParam("courierId", courierId) //  .queryParam("parameter2", "some_value")
                .put(PATH + "/accept/" + orderId)
                .then();
    }


    @Step //Orders - Получить заказ по его номеру

    public ValidatableResponse getOrderTrack(int track) {
        return given()
                .spec(reqSpec)
                .when()
                .queryParam("t", track) //Трекинговый номер заказа
                .get(PATH + "/track")
                .then();
    }

    @Step //Получить заказ без номера заказа
    public ValidatableResponse getOrderWithoutTrack() {
        return given()
                .spec(reqSpec)
                .when()
                .get(PATH + "/track")
                .then();
    }

    public Response post(CreateOrder order) {
        return reqSpec
                .body(order)
                .when()
                .post(ORDER_URL);
    }

    public Response put(OrderResponseSuccess orderResponseSuccess) {
        return given().log().all()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .pathParam("id", orderResponseSuccess.getId())
                .queryParam("courierId", orderResponseSuccess.getCourierId())
                .body(orderResponseSuccess)
                .when()
                .put(ACCEPT_ID_ORDER_URL);
    }


    public Response get() {
        return reqSpec
                .when()
                .get(ORDER_URL);
    }

    public Response getByNumber(int track) {
        return reqSpecWithoutHeaders
                .queryParam("t", track)
                .when()
                .get(GET_ORDER_URL);
    }

    public Response cancel(OrderTrack orderResponse) {
        return reqSpec
                .body(orderResponse)
                .when().log().all()
                .put(CANCEL_ORDER_URL);
    }
}























