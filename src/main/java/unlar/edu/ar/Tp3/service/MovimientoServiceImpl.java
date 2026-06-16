package unlar.edu.ar.service;

import unlar.edu.ar.dto.MovimientoRequest;
import unlar.edu.ar.dto.MovimientoResponse;
import unlar.edu.ar.exception.InsufficientStockException;
import unlar.edu.ar.exception.ResourceNotFoundException;
import unlar.edu.ar.model.MovimientoInventario;
import unlar.edu.ar.model.Producto;
import unlar.edu.ar.model.TipoMovimiento;
import unlar.edu.ar.repository.MovimientoRepository;
import unlar.edu.ar.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    
    public MovimientoServiceImpl(MovimientoRepository movimientoRepository,
                                  ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    
    @Override
    public MovimientoResponse registrar(MovimientoRequest request) {
        Producto producto = productoRepository.findById(request.productoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto con id " + request.productoId() + " no encontrado"));

        int stockActual = producto.getStock();
        if (request.tipo() == TipoMovimiento.SALIDA && request.cantidad() > stockActual) {
            throw new InsufficientStockException(
                    request.productoId(), stockActual, request.cantidad());
        }

        int nuevoStock = request.tipo() == TipoMovimiento.ENTRADA
                ? producto.incrementarStock(request.cantidad())
                : producto.decrementarStock(request.cantidad());

        MovimientoInventario movimiento = new MovimientoInventario(
                null, request.productoId(), request.tipo(),
                request.cantidad(), nuevoStock, request.motivo(), LocalDateTime.now());

        return toResponse(movimientoRepository.save(movimiento));
    }

    
    @Override
    public List<MovimientoResponse> listarPorProducto(Long productoId) {
        if (!productoRepository.existsById(productoId)) {
            throw new ResourceNotFoundException("Producto con id " + productoId + " no encontrado");
        }
        return movimientoRepository.findByProductoId(productoId).stream()
                .map(this::toResponse)
                .toList();
    }

    
    private MovimientoResponse toResponse(MovimientoInventario m) {
        return new MovimientoResponse(m.getId(), m.getProductoId(), m.getTipo(),
                m.getCantidad(), m.getStockResultante(), m.getMotivo(), m.getFecha());
    }
}

