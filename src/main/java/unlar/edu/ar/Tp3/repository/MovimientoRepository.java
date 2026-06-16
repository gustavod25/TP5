package unlar.edu.ar.Tp3.repository;

import unlar.edu.ar.Tp3.model.MovimientoInventario;
import java.util.List;

public interface MovimientoRepository extends IGenericRepository<MovimientoInventario, Long> {

    List<MovimientoInventario> findByProductoId(Long productoId);
}
