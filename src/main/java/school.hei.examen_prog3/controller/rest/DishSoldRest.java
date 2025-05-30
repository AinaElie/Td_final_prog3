package school.hei.examen_prog3.controller.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DishSoldRest {
    private String dish;
    private double quantitySold;
    private double total_amount;

    @JsonCreator
    public DishSoldRest(
            @JsonProperty("dish") String dish,
            @JsonProperty("quantitySold") double quantitySold,
            @JsonProperty("total_amount") double totalAmount
    ) {
        this.dish = dish;
        this.quantitySold = quantitySold;
        this.total_amount = totalAmount;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public double getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(double quantitySold) {
        this.quantitySold = quantitySold;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishSoldRest that = (DishSoldRest) o;
        return Double.compare(getQuantitySold(), that.getQuantitySold()) == 0 && Double.compare(getTotal_amount(), that.getTotal_amount()) == 0 && Objects.equals(getDish(), that.getDish());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDish(), getQuantitySold(), getTotal_amount());
    }

    @Override
    public String toString() {
        return "DishSoldRest{" +
                "dish='" + dish + '\'' +
                ", quantitySold=" + quantitySold +
                ", total_amount=" + total_amount +
                '}';
    }
}
