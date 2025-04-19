package school.hei.examen_prog3.controller.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

public class SalesElementRest {
    private String salesPoint;
    private String dish;
    private double quantitySold;
    private double total_amount;

    public SalesElementRest(String salesPoint, String dish, double quantitySold, double total_amount) {
        this.salesPoint = salesPoint;
        this.dish = dish;
        this.quantitySold = quantitySold;
        this.total_amount = total_amount;
    }

    public String getSalesPoint() {
        return salesPoint;
    }

    public void setSalesPoint(String salesPoint) {
        this.salesPoint = salesPoint;
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
        SalesElementRest that = (SalesElementRest) o;
        return Double.compare(getQuantitySold(), that.getQuantitySold()) == 0 && Double.compare(getTotal_amount(), that.getTotal_amount()) == 0 && Objects.equals(getSalesPoint(), that.getSalesPoint()) && Objects.equals(getDish(), that.getDish());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSalesPoint(), getDish(), getQuantitySold(), getTotal_amount());
    }

    @Override
    public String toString() {
        return "SalesElementRest{" +
                "salesPoint='" + salesPoint + '\'' +
                ", dish='" + dish + '\'' +
                ", quantitySold=" + quantitySold +
                ", total_amount=" + total_amount +
                '}';
    }
}
