package unlar.edu.ar.Tp3.repository;

import org.springframework.stereotype.Repository;
import unlar.edu.ar.Tp3.model.MovimientoInventario;
import java.util.List;

@Repository
public class InMemoryMovimientoRepository
        extends GenericInMemoryRepository<MovimientoInventario, Long>
        implements MovimientoRepository {

    @Override
    public List<MovimientoInventario> findByProductoId(Long productoId) {
        return dataStore.values().stream()
                .filter(m -> m.getProductoId().equals(productoId))
                .toList();
    }

    @Override
    protected Long extractId(MovimientoInventario entity) {
        return entity.getId();
    }

    @Override
    protected MovimientoInventario assignId(MovimientoInventario entity, Long newId) {
        entity.setId(newId);
        return entity;
    }
}
