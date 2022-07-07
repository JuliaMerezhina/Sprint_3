package orders;

import lombok.Data;

@Data
public class OrderResponseSuccess {
    private int id;
    private int courierId;

    public OrderResponseSuccess(int id, int courierId) {
        this.id = id;
        this.courierId = courierId;
    }
}



