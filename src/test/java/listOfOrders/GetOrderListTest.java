package listOfOrders;

import apiClients.ApiClientsOrders;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {


    private ApiClientsOrders apiClientsOrders;
    private Response response;


    @Before
    public void setUp() {
        apiClientsOrders = new ApiClientsOrders();
    }


    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderList() {

        response = apiClientsOrders.getListOfOrders();
        response.then()
                .statusCode(200)
                .assertThat()
                .body("orders", notNullValue());
        System.out.println(response.asString());
    }
}
