package acceptOrder;

import apiClients.ApiClientCouriers;
import apiClients.ApiClientsOrders;
import couriers.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import preconditions.PreconditionsEndpoints;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcceptOrderAndCourierErrorTest {

    private ApiClientsOrders apiClientsOrders;
    private int courierId;
    private int orderId;
    private Courier courier;
    private ApiClientCouriers apiClientCouriers;


    @Before
    public void setUp() {
        courier = Courier.getRandom();
        apiClientCouriers = new ApiClientCouriers();
        apiClientsOrders = new ApiClientsOrders();
    }

    @After
    public void tearDown() {
        apiClientCouriers.delete(courierId);

    }

    @Test
    @DisplayName("Принять заказ c несуществующим номером")
    public void acceptOrderNonExistOrder() {
        orderId = 0;
        String message = "Курьера с таким id не существует";
        ValidatableResponse response = apiClientsOrders.acceptOrders(orderId, courierId)
                .assertThat()
                .statusCode(404);
        assertEquals(message, response.extract().path("message"));
    }


    @Test
    @DisplayName("Принять заказ без номера")
    public void acceptOrderWithoutOrderId() {
        String message = "Not Found.";
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .when()
                .queryParam("courierId", courierId)
                .put(PreconditionsEndpoints.BASEURI + PreconditionsEndpoints.ORDERSURI + "/accept/")
                .then();
        int statusCode = response.extract().statusCode();
        String acceptOrderError = response.extract().path("message");
        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение об ошибке некорректно", acceptOrderError.contains(message));
    }
}
