package school.hei.prog3.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Criteria {
    private String nameColumn;
    private Object value;
    private Object valueMin;
    private Object valueMax;
    private Operator operator;
    private Operator operator1;
    private Operator operator2;

    public Criteria(String nameColumn, Object value, Operator operator) {
        this.nameColumn = nameColumn;
        this.value = value;
        this.operator = operator;
    }

    public Criteria(String nameColumn, Object valueMin, Object valueMax, Operator operator1, Operator operator2) {
        this.nameColumn = nameColumn;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.operator1 = operator1;
        this.operator2 = operator2;
    }
}