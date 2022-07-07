package deleteCourier;

import apiClients.ApiClientCouriers;
import couriers.Courier;
import couriers.CourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import preconditions.PreconditionsEndpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class CourierDeleteTest {

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
    @DisplayName("Удаление аккаунта курьера")
    @Description("Успешный выполнение запроса ok: true")
    public void checkResponseAboutCourierDelete() {
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
        ValidatableResponse response2 = apiClientCouriers.delete(courierId);
        int statusCode = response
                .extract()
                .statusCode();
        String errorMessage = response
                .extract()
                .path("message");
        assertThat(statusCode, equalTo(200));
    }


    @Test
    @DisplayName("Отправка запроса с несуществующим ID")
    @Description("Возвращается ошибка 404")
    public void checkErrorMessageWhenIdIsNotExist() {
        courierId = 0;

        ValidatableResponse response = apiClientCouriers.delete(courierId);
        int statusCode = response
                .extract()
                .statusCode();
        String errorMessage = response
                .extract()
                .path("message");
        assertThat(statusCode, equalTo(404));
        assertThat("Wrong body - massage",
                errorMessage, (equalTo("Курьера с таким id нет.")));
    }

    @Test
    @DisplayName("Отправка запроса без ID")
    @Description("Возвращается ошибка 404")
    public void checkErrorWithoutCourierId() {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete(PreconditionsEndpoints.BASEURI + PreconditionsEndpoints.COURIERURI)
                .then()
                .statusCode(404);
    }
}

















