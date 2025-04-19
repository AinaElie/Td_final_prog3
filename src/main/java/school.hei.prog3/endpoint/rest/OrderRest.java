package school.hei.prog3.endpoint.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import school.hei.prog3.model.StatusOrder;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderRest {
    private String reference;
    private List<DishOrderRest> dishes;
    private List<StatusOrder> status;

    public OrderRest(String reference, List<DishOrderRest> dishes, List<StatusOrder> status) {
        this.reference = reference;
        this.dishes = dishes;
        this.status = status;
    }
}
