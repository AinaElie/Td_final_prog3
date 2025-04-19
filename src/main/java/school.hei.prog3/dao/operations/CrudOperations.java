package school.hei.prog3.dao.operations;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CrudOperations<E> {
    List<E> getAll(int page, int size);
}