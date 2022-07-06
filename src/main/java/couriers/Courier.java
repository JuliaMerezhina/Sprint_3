package couriers;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

    public String login; //Логин курьера, записывается в поле login таблицы Couriers
    public String password; //Пароль курьера, хэш от значения записывается в поле passwordHash таблицы Couriers
    public String firstName; //Имя курьера, записывается в поле firstName таблицы Couriers

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier() {

    }

    //Random 10 chars string Alphanumeric
    public static Courier getRandom() {
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphanumeric(10);
        return new Courier(login, password, firstName);
    }

    public static Courier getCourierWithLoginOnly() {
        return new Courier().setLogin(RandomStringUtils.randomAlphabetic(10));
    }

    public static Courier getCourierWithPasswordOnly() {
        return new Courier().setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }

    public Courier setPassword(String password) {
        this.password = password;
        return this;
    }
    /*{
    "login": "ninja",
    "password": "1234",
    "firstName": "saske"
}*/

    @Override
    public String toString() {
        return "Courier {" +
                "login:" + login + "," +
                "password:" + password + "," +
                "firstName:" + firstName + "}";
    }
}










