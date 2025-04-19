package school.hei.prog3.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DishSale {
    private String dishName;
    private double costQuantity;
    private double totalPrice;

    public DishSale(String dishName, double costQuantity, double totalPrice) {
        this.dishName = dishName;
        this.costQuantity = costQuantity;
        this.totalPrice = totalPrice;
    }
}
