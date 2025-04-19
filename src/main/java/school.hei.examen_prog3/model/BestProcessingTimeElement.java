package school.hei.examen_prog3.model;

import java.util.Objects;

public class BestProcessingTimeElement {
    private Long id;
    private String salesPoint;
    private String dish;
    private double preparationDuration;
    private DurationUnit durationUnit;

    public BestProcessingTimeElement(Long id, String salesPoint, String dish, double preparationDuration, DurationUnit durationUnit) {
        this.id = id;
        this.salesPoint = salesPoint;
        this.dish = dish;
        this.preparationDuration = preparationDuration;
        this.durationUnit = durationUnit;
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

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public double getPreparationDuration() {
        return preparationDuration;
    }

    public void setPreparationDuration(double preparationDuration) {
        this.preparationDuration = preparationDuration;
    }

    public DurationUnit getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(DurationUnit durationUnit) {
        this.durationUnit = durationUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestProcessingTimeElement that = (BestProcessingTimeElement) o;
        return Double.compare(getPreparationDuration(), that.getPreparationDuration()) == 0 && Objects.equals(getId(), that.getId()) && Objects.equals(getSalesPoint(), that.getSalesPoint()) && Objects.equals(getDish(), that.getDish()) && getDurationUnit() == that.getDurationUnit();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSalesPoint(), getDish(), getPreparationDuration(), getDurationUnit());
    }

    @Override
    public String toString() {
        return "BestProcessingTimeElement{" +
                "id=" + id +
                ", salesPoint='" + salesPoint + '\'' +
                ", dish='" + dish + '\'' +
                ", preparationDuration=" + preparationDuration +
                ", durationUnit=" + durationUnit +
                '}';
    }
}
