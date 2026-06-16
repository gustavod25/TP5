package unlar.edu.ar.Tp3.repository;

import java.util.*;

public interface IGenericRepository<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T save(T entity);

    void deleteById(ID id);

    boolean existsById(ID id);

    long count();
}
