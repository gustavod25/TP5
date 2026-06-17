package unlar.edu.ar.Tp3.service;

import unlar.edu.ar.Tp3.dto.MovimientoRequest;
import unlar.edu.ar.Tp3.dto.MovimientoResponse;

import java.util.List;

public interface MovimientoService {

    MovimientoResponse registrar(MovimientoRequest request);

    List<MovimientoResponse> listarPorProducto(Long productoId);
}
