package school.hei.examen_prog3.controller.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.controller.rest.BestProcessingTimeRest;
import school.hei.examen_prog3.model.BestProcessingTime;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BestProcessingTimeRestMapper implements Function<BestProcessingTime, BestProcessingTimeRest> {
    private final BestProcessingTimeElementRestMapper elementMapper;

    @Override
    @SneakyThrows
    public BestProcessingTimeRest apply(BestProcessingTime bestProcessingTime) {
        if (bestProcessingTime == null || bestProcessingTime.getBestProcessingTimes() == null) {
            return new BestProcessingTimeRest(bestProcessingTime.getUpdateAt(), List.of());
        }

        return new BestProcessingTimeRest(
                bestProcessingTime.getUpdateAt(),
                elementMapper.applyAll(bestProcessingTime.getBestProcessingTimes())
        );
    }

    @SneakyThrows
    public List<BestProcessingTimeRest> applyAll(List<BestProcessingTime> bestProcessingTimes) {
        return bestProcessingTimes.stream()
                .map(this::apply)
                .toList();
    }
}