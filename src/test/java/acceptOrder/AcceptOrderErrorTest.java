package acceptOrder;

import apiClients.ApiClientCouriers;
import apiClients.ApiClientsOrders;
import couriers.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcceptOrderErrorTest {

    private ApiClientsOrders apiClientsOrders;
    private int courierId;
    private int orderId;
    private Courier courier;
    private ApiClientCouriers apiClientCouriers;

    @Before
    public void setUp() {
        apiClientsOrders = new ApiClientsOrders();
    }

    @Test
    @DisplayName("Принять заказ без id курьера")
    public void acceptOrderWithoutIdCourier() {
        String message = "Недостаточно данных для поиска";
        ValidatableResponse response = apiClientsOrders.acceptOrder(orderId);
        int statusCode = response.extract().statusCode();
        String acceptOrderError = response.extract().path("message");
        assertEquals("statusCode неверный", 400, statusCode);
        assertTrue("Сообщение об ошибке некорректно", acceptOrderError.contains(message));

    }

    @Test
    @DisplayName("Принять заказ с некорректным id курьера")
    public void acceptOrderInvalidIdCourier() {
        String message = "Курьера с таким id не существует";
        courierId = 0;
        ValidatableResponse response = apiClientsOrders.acceptOrders(orderId, courierId);
        int statusCode = response.extract().statusCode();
        String acceptOrderError = response.extract().path("message");
        assertEquals("statusCode неверный", 404, statusCode);
        assertTrue("Сообщение об ошибке некорректно", acceptOrderError.contains(message));
    }


}
