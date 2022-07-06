package createCourier;

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
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CourierCreateTest {

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
    @DisplayName("Создание аккаунта курьера")
    @Description("Успешное создание записи ok: true, запрос возвращает правильный код ответа")
    public void checkCourierAccountCreate() {
        ValidatableResponse response = apiClientCouriers.create(courier);
        int statusCode = response
                .extract()
                .statusCode();
        boolean isCourierCreated = response
                .extract()
                .path("ok");
        courierId = apiClientCouriers
                .login(CourierLogin.from(courier))
                .extract()
                .path("id");
        assertThat(statusCode, equalTo(201));
        assertTrue(isCourierCreated);
    }

    @Test
    @DisplayName("Проверка невозможности создания курьера с уже существующими данными")
    @Description("Проверка получения сообщения при отправке запроса с повторяющимся логином")
    public void checkResponseMessageAboutImpossibleCreateCourier() {
        apiClientCouriers.create(courier);
        ValidatableResponse validatableResponseNegative = apiClientCouriers.create(courier);
        int statusCodeNegativeResponse = validatableResponseNegative
                .extract()
                .statusCode();
        String errorMessage = validatableResponseNegative
                .extract()
                .path("message");
        courierId = apiClientCouriers
                .login(CourierLogin.from(courier))
                .extract()
                .path("id");
        assertThat("Error status code",
                statusCodeNegativeResponse, equalTo(409));
        assertThat("Wrong body - massage",
                errorMessage, (equalTo("Этот логин уже используется. Попробуйте другой.")));
    }
}







