package couriers;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
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

    public Courier(Courier courier) {
        this.login = courier.getLogin();
        this.password = courier.getPassword();
    }


    public static Courier from(Courier courier) {
        return new Courier(courier);

    }
}









