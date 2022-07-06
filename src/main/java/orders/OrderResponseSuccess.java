package orders;

import lombok.Data;

@Data
public class OrderResponseSuccess {
    private static Integer id;
    private static Integer courierId;

    public OrderResponseSuccess(int orderId, int courierId) {
    }

    public static Integer getCourierId() {
        return courierId;
    }

    public static Integer getId() {
        return id;

    }
}



