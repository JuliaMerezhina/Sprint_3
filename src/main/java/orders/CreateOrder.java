package orders;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.List;


@AllArgsConstructor
public class CreateOrder {

    public static Instant time = Instant.now();
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<String> color;

    public static CreateOrder generateRandomOrderWithDefiniteColor(List<String> color) {
        final String firstName = RandomStringUtils.randomAlphabetic(6);
        final String lastName = RandomStringUtils.randomAlphabetic(6);
        final String address = RandomStringUtils.randomAlphabetic(6);
        final String metroStation = RandomStringUtils.randomAlphabetic(6);
        final String phone = RandomStringUtils.randomNumeric(11);
        final int rentTime = (int) (Math.random() * 9);
        final String deliveryDate = time.toString();
        final String comment = RandomStringUtils.randomAlphabetic(10);
        return new CreateOrder(firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                color);
    }
}









