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
public class DishOrderRest {
    private String idDishOrder;
    private String dishName;
    private double actualPrice;
    private double dishQuantity;
    private List<StatusOrder> status;

    public DishOrderRest(String idDishOrder, String dishName, double actualPrice, double dishQuantity, List<StatusOrder> status) {
        this.idDishOrder = idDishOrder;
        this.dishName = dishName;
        this.actualPrice = actualPrice;
        this.dishQuantity = dishQuantity;
        this.status = status;
    }
}
