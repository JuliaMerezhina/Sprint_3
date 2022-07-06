package createOrder;

import apiClients.ApiClientsOrders;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.CreateOrder;
import orders.OrderTrack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    private final List<String> color;
    private final int expectedCode;
    int track;
    private ApiClientsOrders apiClientsOrders;
    private CreateOrder order;

    public OrderCreateTest(List<String> color, int expectedCode) {
        this.color = color;
        this.expectedCode = expectedCode;
    }

    @Parameterized.Parameters
    public static Object[][] getColorList() {
        return new Object[][]{
                {List.of("BLACK"), 201},
                {List.of("GREY"), 201},
                {List.of("BLACK", "GREY"), 201},
                {null, 201},
        };
    }

    @Before
    public void setUp() {
        apiClientsOrders = new ApiClientsOrders();
        order = CreateOrder.generateRandomOrderWithDefiniteColor(color);
    }

    @After
    public void cancel() {
        OrderTrack orderResponse = new OrderTrack(track);
        apiClientsOrders.cancel(orderResponse);
    }

    @Test
    @DisplayName("Проверка создания заказа")
    @Description("Возвращает статус код + trackId")
    public void createNewOrderAndCheckResponse() {
        ValidatableResponse response = apiClientsOrders
                .post(order)
                .then()
                .statusCode(201)
                .assertThat()
                .body("track", notNullValue());

        track = response.extract().path("track");
        System.out.println("Response for color " + color + " : " + "track " + track);
    }
}


