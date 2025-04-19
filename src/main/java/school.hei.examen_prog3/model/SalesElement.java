package school.hei.examen_prog3.model;

import java.util.List;
import java.util.Objects;

public class SalesElement {
    private Long id;
    private String salesPoint;
    private List<DishSold> dishSoldList;

    public SalesElement(Long id, String salesPoint, List<DishSold> dishSoldList) {
        this.id = id;
        this.salesPoint = salesPoint;
        this.dishSoldList = dishSoldList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesPoint() {
        return salesPoint;
    }

    public void setSalesPoint(String salesPoint) {
        this.salesPoint = salesPoint;
    }

    public List<DishSold> getDishSoldList() {
        return dishSoldList;
    }

    public void setDishSoldList(List<DishSold> dishSoldList) {
        this.dishSoldList = dishSoldList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesElement that = (SalesElement) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getSalesPoint(), that.getSalesPoint()) && Objects.equals(getDishSoldList(), that.getDishSoldList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSalesPoint(), getDishSoldList());
    }

    @Override
    public String toString() {
        return "SalesElement{" +
                "id=" + id +
                ", salesPoint='" + salesPoint + '\'' +
                ", dishSoldList=" + dishSoldList +
                '}';
    }
}
