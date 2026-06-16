package unlar.edu.ar.Tp3.repository;

import org.springframework.stereotype.Repository;
import unlar.edu.ar.Tp3.model.Categoria;

@Repository
public class InMemoryCategoriaRepository
        extends GenericInMemoryRepository<Categoria, Long>
        implements CategoriaRepository {

    @Override
    protected Long extractId(Categoria entity) {
        return entity.getId();
    }

    @Override
    protected Categoria assignId(Categoria entity, Long newId) {
        entity.setId(newId);
        return entity;
    }
}
