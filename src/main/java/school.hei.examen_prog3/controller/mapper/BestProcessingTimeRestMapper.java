package school.hei.examen_prog3.controller.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.examen_prog3.controller.rest.BestProcessingTimeRest;
import school.hei.examen_prog3.model.BestProcessingTime;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class BestProcessingTimeRestMapper implements Function<BestProcessingTime, BestProcessingTimeRest> {
    private final BestProcessingTimeElementRestMapper bestProcessingTimeElementRestMapper;

    public BestProcessingTimeRestMapper(BestProcessingTimeElementRestMapper bestProcessingTimeElementRestMapper) {
        this.bestProcessingTimeElementRestMapper = bestProcessingTimeElementRestMapper;
    }

    @SneakyThrows
    @Override
    public BestProcessingTimeRest apply(BestProcessingTime bestProcessingTime) {
        return new BestProcessingTimeRest(
                bestProcessingTime.getUpdateAt(),
                bestProcessingTimeElementRestMapper.applyAll(bestProcessingTime.getBestProcessingTimes())
        );
    }

    @SneakyThrows
    public List<BestProcessingTimeRest> applyAll(List<BestProcessingTime> bestProcessingTimes) {
        List<BestProcessingTimeRest> bestProcessingTimeRestList = new ArrayList<>();

        for (BestProcessingTime bestProcessingTime : bestProcessingTimes) {
            bestProcessingTimeRestList.add(this.apply(bestProcessingTime));
        }

        return bestProcessingTimeRestList;
    }
}
