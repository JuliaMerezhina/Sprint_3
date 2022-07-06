package courierLogin;

import apiClients.ApiClientCouriers;
import couriers.Courier;
import couriers.CourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CourierLoginNegativeParametrizedTest {

    private static final Courier courier = Courier.getRandom();
    private static final ApiClientCouriers apiClientCouriers = new ApiClientCouriers();
    private final CourierLogin courierLogin;
    private final int expectedStatus;
    private final String expectedErrorMessage;
    private int courierId;

    public CourierLoginNegativeParametrizedTest(CourierLogin courierLogin, int expectedStatus, String expectedErrorMessage) {
        this.courierLogin = courierLogin;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    //поведение отличается от описанного в документации в случае отсутствия логина получаем ошибку как в документации, а в случае отсутствия пароля - ошибку 504
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {CourierLogin.getCredentialsWithPasswordOnly(courier), 400, "Недостаточно данных для входа"},
                {CourierLogin.getCredentialsWithLoginOnly(courier), 400, "Недостаточно данных для входа"},
                {CourierLogin.getCredentialsWithRandomLogin(courier), 404, "Учетная запись не найдена"},
                {CourierLogin.getCredentialsWithRandomPassword(courier), 404, "Учетная запись не найдена"},
        };
    }

    @After
    public void tearDown() {
        apiClientCouriers.delete(courierId);
    }

    @Test
    @DisplayName("Проверка получения ответов на некорректные запросы")
    @Description("- Проверка авторизации с запросом только из пароля\n" +
            "- Проверка авторизации с запросом только из логина\n" +
            "- Проверка авторизации с рандомным  логином\n" +
            "- Проверка авторизации с рандомным  паролем")
    public void courierLoginIncorrectAuthorizationTest() {
        apiClientCouriers.create(courier);
        ValidatableResponse login = new ApiClientCouriers().login(courierLogin);
        courierId = apiClientCouriers
                .login(CourierLogin.from(courier))
                .extract()
                .path("id");
        int ActualStatusCode = login
                .extract()
                .statusCode();
        assertEquals("Status code is incorrect",
                expectedStatus, ActualStatusCode);
        String actualMessage = login
                .extract()
                .path("message");
        assertEquals("Courier ID is incorrect",
                expectedErrorMessage, actualMessage);
    }


}






