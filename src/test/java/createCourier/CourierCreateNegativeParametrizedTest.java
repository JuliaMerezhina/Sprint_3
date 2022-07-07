package createCourier;

import apiClients.ApiClientCouriers;
import couriers.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierCreateNegativeParametrizedTest {

    private final Courier courier;
    private final int expectedStatus;
    private final String expectedErrorMessage;

    public CourierCreateNegativeParametrizedTest(Courier courier, int expectedStatus, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {Courier.getCourierWithLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"}
        };

    }


    @Test
    @DisplayName("Проверка отсутствия возможности создания аккаунта курьера с неполными данными")
    @Description("Попытка создания курьера, при передаче: только логин или только пароль")

    public void SendRequestWithIncompleteData() {
        ValidatableResponse response = new ApiClientCouriers().create(courier);
        String actualMessage = response
                .extract()
                .path("message");
        int code = response
                .extract()
                .path("code");
        assertEquals(expectedErrorMessage, actualMessage);
        assertEquals(expectedStatus, code);


    }
}
