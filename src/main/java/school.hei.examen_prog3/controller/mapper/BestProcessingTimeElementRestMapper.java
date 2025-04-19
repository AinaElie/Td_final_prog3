package school.hei.examen_prog3.controller.mapper;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.controller.rest.BestProcessingTimeElementRest;
import school.hei.examen_prog3.model.BestProcessingTimeElement;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BestProcessingTimeElementRestMapper implements Function<BestProcessingTimeElement, BestProcessingTimeElementRest> {

    @Override
    @SneakyThrows
    public BestProcessingTimeElementRest apply(BestProcessingTimeElement element) {
        if (element == null) {
            return null;
        }

        return new BestProcessingTimeElementRest(
                element.getSalesPoint(),
                element.getDish(),
                element.getPreparationDuration(),
                element.getDurationUnit()
        );
    }

    @SneakyThrows
    public List<BestProcessingTimeElementRest> applyAll(List<BestProcessingTimeElement> elements) {
        return elements.stream()
                .map(this::apply)
                .collect(Collectors.toList());
    }
}