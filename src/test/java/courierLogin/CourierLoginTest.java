package courierLogin;

import apiClients.ApiClientCouriers;
import couriers.Courier;
import couriers.CourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {

    private Courier courier;
    private ApiClientCouriers apiClientCouriers;
    private int courierId;

    @Before
    public void setUp() {
        courier = Courier.getRandom();
        apiClientCouriers = new ApiClientCouriers();
    }

    @After
    public void tearDown() {
        apiClientCouriers.delete(courierId);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с успешными данными")
    @Description("Возвращается id")
    public void courierLoginSuccess() {
        apiClientCouriers.create(courier);
        ValidatableResponse response = apiClientCouriers.login(CourierLogin.from(courier));
        courierId = response
                .extract()
                .path("id");
        int statusCodeSuccessLogin = response
                .extract()
                .statusCode();
        assertThat("Courier ID is incorrect",
                courierId, is(not(0)));
        assertThat(statusCodeSuccessLogin, equalTo(200));
    }


}
