package school.hei.examen_prog3.controller.mapper;

import lombok.*;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.controller.rest.BestProcessingTimeElementRest;
import school.hei.examen_prog3.model.BestProcessingTimeElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class BestProcessingTimeElementRestMapper implements Function<BestProcessingTimeElement, BestProcessingTimeElementRest> {
    @SneakyThrows
    @Override
    public BestProcessingTimeElementRest apply(BestProcessingTimeElement bestProcessingTimeElement) {
        return new BestProcessingTimeElementRest(
                bestProcessingTimeElement.getSalesPoint(),
                bestProcessingTimeElement.getDish(),
                bestProcessingTimeElement.getPreparationDuration(),
                bestProcessingTimeElement.getDurationUnit()
        );
    }

    @SneakyThrows
    public List<BestProcessingTimeElementRest> applyAll(List<BestProcessingTimeElement> bestProcessingTimeElements) {
        List<BestProcessingTimeElementRest> bestProcessingTimeElementRests = new ArrayList<>();

        for (BestProcessingTimeElement bestProcessingTimeElement : bestProcessingTimeElements) {
            bestProcessingTimeElementRests.add(this.apply(bestProcessingTimeElement));
        }

        return bestProcessingTimeElementRests;
    }
}
