package school.hei.examen_prog3.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class BestSales {
    private Long id;
    private Instant updateAt;
    private List<SalesElement> sales;

    public BestSales(Long id, Instant updateAt, List<SalesElement> sales) {
        this.id = id;
        this.updateAt = updateAt;
        this.sales = sales;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public List<SalesElement> getSales() {
        return sales;
    }

    public void setSales(List<SalesElement> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestSales bestSales = (BestSales) o;
        return Objects.equals(getId(), bestSales.getId()) && Objects.equals(getUpdateAt(), bestSales.getUpdateAt()) && Objects.equals(getSales(), bestSales.getSales());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUpdateAt(), getSales());
    }

    @Override
    public String toString() {
        return "BestSales{" +
                "id=" + id +
                ", updateAt=" + updateAt +
                ", sales=" + sales +
                '}';
    }
}
