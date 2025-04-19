package school.hei.examen_prog3.dao.operations;

import org.springframework.stereotype.Repository;
import school.hei.examen_prog3.dao.DatabaseConnection;

@Repository
public class BestProcessingTimeCrudOperations {
    private final DatabaseConnection databaseConnection;

    public BestProcessingTimeCrudOperations(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
