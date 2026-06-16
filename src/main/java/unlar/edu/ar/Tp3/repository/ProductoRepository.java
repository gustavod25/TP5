package unlar.edu.ar.Tp3.repository;

import unlar.edu.ar.Tp3.model.Producto;
import java.util.List;

public interface ProductoRepository extends IGenericRepository<Producto, Long> {

    List<Producto> findByCategoria(Long categoriaId);

    List<Producto> buscarPorNombre(String texto);
}
