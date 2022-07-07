package getOrder;

import apiClients.ApiClientsOrders;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import orders.CreateOrder;
import orders.OrderTrack;
import org.hamcrest.core.StringContains;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GetOrderTrackTest {

    private ApiClientsOrders apiClientsOrders;
    private int track;
    private int orderId;
    private List<String> color;
    private CreateOrder order;


    @Before
    public void setUp() {
        apiClientsOrders = new ApiClientsOrders();
        order = CreateOrder.generateRandomOrderWithDefiniteColor(color);
        track = apiClientsOrders.post(order)
                .then()
                .log()
                .all()
                .statusCode(201)
                .extract()
                .path("track");
    }


    @After
    public void cancelOrder() {
        OrderTrack orderResponse = new OrderTrack(track);
        apiClientsOrders.cancel(orderResponse);
    }


    @Test
    @DisplayName("Получение заказа по номеру = успешно")
    public void getOrderByTrackSuccessfully() {
        Response response = apiClientsOrders.getByNumber(track);
        response
                .then().log().all()
                .assertThat()
                .statusCode(200);
        orderId = response.then()
                .extract()
                .path("order.id");
        Assert.assertNotNull(orderId);
        System.out.println("id заказа: " + orderId);
    }


    @Test
    @DisplayName("Проверка получения заказа с несуществующим номером")
    public void getOrderByTrackNotExist() {
        track = 0;
        String message = "Заказ не найден";
        ValidatableResponse response = apiClientsOrders.getOrderTrack(track)
                .assertThat()
                .body(StringContains.containsString(message))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Проверка получения заказа без номера")
    public void getOrderWithoutTrackNumber() {
        String message = "Недостаточно данных для поиска";
        ValidatableResponse response = apiClientsOrders.getOrderWithoutTrack()
                .assertThat()
                .body(StringContains.containsString(message))
                .and()
                .statusCode(400);
    }
}
