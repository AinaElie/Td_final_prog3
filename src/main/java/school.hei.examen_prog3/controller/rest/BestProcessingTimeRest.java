package school.hei.examen_prog3.controller.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BestProcessingTimeRest {
    private Instant updateAt;
    private List<BestProcessingTimeElementRest> bestProcessingTimes;

    public BestProcessingTimeRest(Instant updateAt, List<BestProcessingTimeElementRest> bestProcessingTimes) {
        this.updateAt = updateAt;
        this.bestProcessingTimes = bestProcessingTimes;
    }
}
