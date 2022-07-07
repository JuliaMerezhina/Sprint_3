package acceptOrder;

import apiClients.ApiClientCouriers;
import apiClients.ApiClientsOrders;
import couriers.Courier;
import couriers.CourierLogin;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import orders.CreateOrder;
import orders.OrderResponseSuccess;
import orders.OrderTrack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

//Это задание можешь выполнить по желанию: оно не повлияет на оценку за основную часть, но поможет дополнительно попрактиковаться.
// не удалось получить успешный ответ
public class AcceptOrderTest {

    //сначала создать заказ todo
    private ApiClientsOrders apiClientsOrders;
    private Courier courier;
    private ApiClientCouriers apiClientCouriers;
    private int courierId;
    private int orderId;
    private int track;

    private List<String> color;
    private CourierLogin courierLogin;

    private CreateOrder createOrder;

    private OrderResponseSuccess orderResponseSuccess;

    @Before
    public void setUp() {
        courier = Courier.getRandom();
        courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());
        apiClientCouriers = new ApiClientCouriers();
        apiClientCouriers.create(courier);
        apiClientCouriers.login(courierLogin);
        courierId = apiClientCouriers
                .login(CourierLogin.from(courier))
                .extract()
                .path("id");
        apiClientsOrders = new ApiClientsOrders();
        createOrder = CreateOrder.generateRandomOrderWithDefiniteColor(color);


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
        orderResponseSuccess = new OrderResponseSuccess(orderId, courierId);
        Response response = apiClientsOrders.put(orderResponseSuccess);
        response.then().log().all()
                .statusCode(200);
    }

}
