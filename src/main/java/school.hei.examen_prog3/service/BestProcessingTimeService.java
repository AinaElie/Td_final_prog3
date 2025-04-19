package school.hei.examen_prog3.service;

import org.springframework.stereotype.Service;
import school.hei.examen_prog3.controller.mapper.BestProcessingTimeRestMapper;
import school.hei.examen_prog3.controller.rest.BestProcessingTimeRest;
import school.hei.examen_prog3.dao.operations.BestProcessingTimeCrud;

import java.util.List;

@Service
public class BestProcessingTimeService {
    private final BestProcessingTimeCrud bestProcessingTimeCrud;
    private final BestProcessingTimeRestMapper bestProcessingTimeRestMapper;

    public BestProcessingTimeService(BestProcessingTimeCrud bestProcessingTimeCrud, BestProcessingTimeRestMapper bestProcessingTimeRestMapper) {
        this.bestProcessingTimeCrud = bestProcessingTimeCrud;
        this.bestProcessingTimeRestMapper = bestProcessingTimeRestMapper;
    }

    public List<BestProcessingTimeRest> getAll () {
        return bestProcessingTimeRestMapper.applyAll(bestProcessingTimeCrud.findAll());
    }
}
