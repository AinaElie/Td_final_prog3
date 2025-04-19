package school.hei.examen_prog3.controller.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class BestSalesRest {
    private Instant updateAt;
    private List<SalesElementRest> sales;

    public BestSalesRest() {
    }

    public BestSalesRest(Instant updateAt, List<SalesElementRest> sales) {
        this.updateAt = updateAt;
        this.sales = sales;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public List<SalesElementRest> getSales() {
        return sales;
    }

    public void setSales(List<SalesElementRest> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestSalesRest that = (BestSalesRest) o;
        return Objects.equals(getUpdateAt(), that.getUpdateAt()) && Objects.equals(getSales(), that.getSales());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUpdateAt(), getSales());
    }

    @Override
    public String toString() {
        return "BestSalesRest{" +
                "updateAt=" + updateAt +
                ", sales=" + sales +
                '}';
    }
}
