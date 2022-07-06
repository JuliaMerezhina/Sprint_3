package couriers;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierLogin {

    public String login;
    public String password;

    public CourierLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierLogin() {

    }

    public static CourierLogin from(Courier courier) {
        return new CourierLogin(courier.login, courier.password);
    }

    public static CourierLogin getCredentialsWithPasswordOnly(Courier courier) {
        return new CourierLogin().setPassword(courier.password);
    }

    public static CourierLogin getCredentialsWithLoginOnly(Courier courier) {
        return new CourierLogin().setLogin(courier.login);
    }

    public static CourierLogin getCredentialsWithRandomLogin(Courier courier) {
        return new CourierLogin(RandomStringUtils.randomAlphabetic(10), courier.password);
    }

    public static CourierLogin getCredentialsWithRandomPassword(Courier courier) {
        return new CourierLogin(courier.login, RandomStringUtils.randomAlphabetic(10));
    }

    public CourierLogin setLogin(String login) {
        this.login = login;
        return this;
    }

    public CourierLogin setPassword(String password) {
        this.password = password;
        return this;
    }

    /*{
        "login": "ninja",
            "password": "1234"
    } */
    @Override
    public String toString() {
        return "CourierCredentials {" +
                "login:" + login + "," +
                "password:" + password + "}";
    }

}
