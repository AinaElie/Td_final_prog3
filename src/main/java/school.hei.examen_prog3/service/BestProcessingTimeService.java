package school.hei.examen_prog3.service;

import org.springframework.stereotype.Service;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BestProcessingTimeService {
    private final BestProcessingTimeCrud bestProcessingTimeCrud;
    private final BestProcessingTimeElementCrud elementCrud;
    private final BestProcessingTimeRestMapper mapper;
    private final SalesPoint salesPoint;

    public BestProcessingTimeService(BestProcessingTimeCrud bestProcessingTimeCrud,
                                     BestProcessingTimeElementCrud elementCrud,
                                     BestProcessingTimeRestMapper mapper,
                                     SalesPoint salesPoint) {
        this.bestProcessingTimeCrud = bestProcessingTimeCrud;
        this.elementCrud = elementCrud;
        this.mapper = mapper;
        this.salesPoint = salesPoint;
    }

    public BestProcessingTimeRest getBestProcessingTime(String dishId, int top, String durationUnit, String calculationMode)
            throws IOException, InterruptedException, URISyntaxException {

        BestProcessingTime bestProcessingTime = salesPoint.getBestProcessingTimesPDV();

        List<BestProcessingTimeElement> filteredElements = bestProcessingTime.getBestProcessingTimes().stream()
                .filter(e -> e.getDish().equalsIgnoreCase(getDishNameById(dishId)))
                .collect(Collectors.toList());

        if (filteredElements.isEmpty()) {
            return new BestProcessingTimeRest(bestProcessingTime.getUpdateAt(), List.of());
        }

        Comparator<BestProcessingTimeElement> comparator = switch (calculationMode.toUpperCase()) {
            case "MINIMUM" -> Comparator.comparingDouble(BestProcessingTimeElement::getPreparationDuration);
            case "MAXIMUM" -> Comparator.comparingDouble(BestProcessingTimeElement::getPreparationDuration).reversed();
            default -> Comparator.comparingDouble(BestProcessingTimeElement::getPreparationDuration);
        };

        filteredElements.sort(comparator);

        DurationUnit targetUnit = DurationUnit.valueOf(durationUnit.toUpperCase());
        filteredElements.forEach(e -> convertDuration(e, targetUnit));

        if (filteredElements.size() > top) {
            filteredElements = filteredElements.subList(0, top);
        }

        return mapper.apply(new BestProcessingTime(
                bestProcessingTime.getId(),
                bestProcessingTime.getUpdateAt(),
                filteredElements
        ));
    }

    private String getDishNameById(String dishId) {
        return switch (dishId) {
            case "1" -> "Hot dog";
            case "2" -> "Omelette";
            case "3" -> "Saucisse frit";
            default -> "";
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

        for (BestProcessingTimeElement element : bestProcessingTime.getBestProcessingTimes()) {
            elementCrud.create(element, savedProcessingTime.getId());
        }
    }

    public void clearData() {
        bestProcessingTimeCrud.deleteAll();
    }
}