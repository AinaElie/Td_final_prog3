package school.hei.examen_prog3.service;

import org.springframework.stereotype.Service;
import school.hei.examen_prog3.controller.mapper.BestProcessingTimeElementRestMapper;
import school.hei.examen_prog3.controller.mapper.BestProcessingTimeRestMapper;
import school.hei.examen_prog3.controller.rest.BestProcessingTimeRest;
import school.hei.examen_prog3.dao.SalesPoint;
import school.hei.examen_prog3.dao.operations.BestProcessingTimeCrud;
import school.hei.examen_prog3.dao.operations.BestProcessingTimeElementCrud;
import school.hei.examen_prog3.model.BestProcessingTime;
import school.hei.examen_prog3.model.BestProcessingTimeElement;
import school.hei.examen_prog3.model.DurationUnit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sun.org.apache.bcel.internal.classfile.JavaClass.getComparator;

@Service
public class BestProcessingTimeService {
    private final BestProcessingTimeCrud bestProcessingTimeCrud;
    private final BestProcessingTimeElementCrud elementCrud;
    private final BestProcessingTimeRestMapper mapper;
    private final SalesPoint salesPoint;
    private final BestProcessingTimeElementRestMapper bestProcessingTimeElementRestMapper;

    public BestProcessingTimeService(BestProcessingTimeCrud bestProcessingTimeCrud, BestProcessingTimeElementCrud elementCrud, BestProcessingTimeRestMapper mapper, SalesPoint salesPoint,
                                     BestProcessingTimeElementRestMapper bestProcessingTimeElementRestMapper) {
        this.bestProcessingTimeCrud = bestProcessingTimeCrud;
        this.elementCrud = elementCrud;
        this.mapper = mapper;
        this.salesPoint = salesPoint;
        this.bestProcessingTimeElementRestMapper = bestProcessingTimeElementRestMapper;
    }

    public BestProcessingTimeRest getBestProcessingTime(String dishId, int top, String durationUnit, String calculationMode) {
        BestProcessingTime latest = bestProcessingTimeCrud.findLatest()
                .orElseThrow(() -> new RuntimeException("No processing time data available"));

        List<BestProcessingTimeElement> filteredElements = latest.getBestProcessingTimes().stream()
                .filter(e -> e.getDish().equalsIgnoreCase(getDishNameById(dishId)))
                .collect(Collectors.toList());

        DurationUnit targetUnit = DurationUnit.valueOf(durationUnit.toUpperCase());
        filteredElements.forEach(e -> convertDuration(e, targetUnit));

        filteredElements.sort(getComparator(calculationMode));

        if (filteredElements.size() > top) {
            filteredElements = filteredElements.subList(0, top);
        }
        
        return new BestProcessingTimeRest(
                latest.getUpdateAt(),
                bestProcessingTimeElementRestMapper.applyAll(filteredElements)
        );
    }

    private Comparator<BestProcessingTimeElement> getComparator(String calculationMode) {
        return switch (calculationMode.toUpperCase()) {
            case "MINIMUM" -> Comparator.comparingDouble(BestProcessingTimeElement::getPreparationDuration);
            case "MAXIMUM" -> Comparator.comparingDouble(BestProcessingTimeElement::getPreparationDuration).reversed();
            default -> Comparator.comparingDouble(BestProcessingTimeElement::getPreparationDuration);
        };
    }

    private String getDishNameById(String dishId) {
        return switch (dishId) {
            case "1" -> "Hot dog";
            case "2" -> "Omelette";
            case "3" -> "Saucisse frit";
            default -> throw new IllegalArgumentException("Unknown dish id: " + dishId);
        };
    }

    private void convertDuration(BestProcessingTimeElement element, DurationUnit targetUnit) {
        double durationInSeconds = switch (element.getDurationUnit()) {
            case SECONDS -> element.getPreparationDuration();
            case MINUTES -> element.getPreparationDuration() * 60;
            case HOUR -> element.getPreparationDuration() * 3600;
        };

        double convertedDuration = switch (targetUnit) {
            case SECONDS -> durationInSeconds;
            case MINUTES -> durationInSeconds / 60;
            case HOUR -> durationInSeconds / 3600;
        };

        element.setPreparationDuration(convertedDuration);
        element.setDurationUnit(targetUnit);
    }

    public void synchronize() throws IOException, InterruptedException, URISyntaxException {
        clearData();

        BestProcessingTime bestProcessingTime = salesPoint.getBestProcessingTimesPDV();
        BestProcessingTime savedProcessingTime = bestProcessingTimeCrud.create(bestProcessingTime);

        if (bestProcessingTime.getBestProcessingTimes() != null) {
            for (BestProcessingTimeElement element : bestProcessingTime.getBestProcessingTimes()) {
                elementCrud.create(element, savedProcessingTime.getId());
            }
        }
    }

    public void clearData() {
        bestProcessingTimeCrud.deleteAll();
    }
}