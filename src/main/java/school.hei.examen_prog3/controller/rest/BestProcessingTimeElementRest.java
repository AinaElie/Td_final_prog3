package school.hei.examen_prog3.controller.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import school.hei.examen_prog3.model.DurationUnit;

import java.util.Objects;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class BestProcessingTimeElementRest {
    private String salesPoint;
    private String dish;
    private double preparationDuration;
    private DurationUnit durationUnit;

    public BestProcessingTimeElementRest(String salesPoint, String dish, double preparationDuration, DurationUnit durationUnit) {
        this.salesPoint = salesPoint;
        this.dish = dish;
        this.preparationDuration = preparationDuration;
        this.durationUnit = durationUnit;
    }
}
