package school.hei.examen_prog3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PointOfSaleDishDTO {
    @JsonProperty("dishIdentifier")
    private Long dishIdentifier;

    @JsonProperty("dishName")
    private String dishName;

    @JsonProperty("quantitySold")
    private double quantitySold;

    public Long getDishIdentifier() {
        return dishIdentifier;
    }

    public void setDishIdentifier(Long dishIdentifier) {
        this.dishIdentifier = dishIdentifier;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(double quantitySold) {
        this.quantitySold = quantitySold;
    }
}