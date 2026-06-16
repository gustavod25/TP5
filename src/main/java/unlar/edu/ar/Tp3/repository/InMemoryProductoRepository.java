package unlar.edu.ar.Tp3.repository;

import org.springframework.stereotype.Repository;
import unlar.edu.ar.Tp3.model.Producto;
import java.util.List;

@Repository
public class InMemoryProductoRepository
        extends GenericInMemoryRepository<Producto, Long>
        implements ProductoRepository {

    @Override
    public List<Producto> findByCategoria(Long categoriaId) {
        return dataStore.values().stream()
                .filter(p -> p.getCategoria().getId().equals(categoriaId))
                .toList();
    }

    @Override
    public List<Producto> buscarPorNombre(String texto) {
        String lower = texto.toLowerCase();
        return dataStore.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(lower))
                .toList();
    }

    @Override
    protected Long extractId(Producto entity) {
        return entity.getId();
    }

    @Override
    protected Producto assignId(Producto entity, Long newId) {
        entity.setId(newId);
        return entity;
    }
}
