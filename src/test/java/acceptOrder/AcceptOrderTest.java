package acceptOrder;

import apiClients.ApiClientCouriers;
import apiClients.ApiClientsOrders;
import couriers.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.OrderResponseSuccess;
import orders.OrderTrack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class AcceptOrderTest {

    //сначала создать заказ todo
    private ApiClientsOrders apiClientsOrders;
    private Courier courier;
    private ApiClientCouriers apiClientCouriers;
    private int courierId;
    private int orderId;
    private int track;


    @Before
    public void setUp() {
        courier = Courier.getRandom();
        apiClientCouriers = new ApiClientCouriers();
        apiClientsOrders = new ApiClientsOrders();
    }

    @After
    public void tearDown() {
        OrderTrack orderResponse = new OrderTrack(track);
        apiClientsOrders.cancel(orderResponse);
        apiClientCouriers.delete(courierId);
    }


    @Test
    @DisplayName("Принять заказ")
    public void acceptOrderSuccessful() {
        OrderResponseSuccess orderResponseSuccess = new OrderResponseSuccess(orderId, courierId);
        ValidatableResponse response = apiClientsOrders
                .put(orderResponseSuccess)
                .then().log().all()
                .statusCode(200)
                .assertThat().body("ok", notNullValue())
                .assertThat().body("ok", equalTo(true));
    }

}
