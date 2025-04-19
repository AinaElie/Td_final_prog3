package school.hei.prog3.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProcessingTimeMapper {
    private String durationType;
    private double value;

    public ProcessingTimeMapper(String durationType, double value) {
        this.durationType = durationType;
        this.value = value;
    }
}
