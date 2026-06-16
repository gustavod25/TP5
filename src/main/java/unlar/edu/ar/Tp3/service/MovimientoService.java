package unlar.edu.ar.service;

import unlar.edu.ar.dto.MovimientoRequest;
import unlar.edu.ar.dto.MovimientoResponse;
import unlar.edu.ar.exception.InsufficientStockException;
import unlar.edu.ar.exception.ResourceNotFoundException;

import java.util.List;


public interface MovimientoService {

    
    MovimientoResponse registrar(MovimientoRequest request);

    
    List<MovimientoResponse> listarPorProducto(Long productoId);
}

