package unlar.edu.ar.Tp3service;

import unlar.edu.ar.Tp3.model.*;
import unlar.edu.ar.Tp3.*;
import unlar.edu.ar.Tp3.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlertaServiceImpl implements AlertaService {

    private final ProductoRepository productoRepository;
    private final StockConfig stockConfig;

    
    public AlertaServiceImpl(ProductoRepository productoRepository, StockConfig stockConfig) {
        this.productoRepository = productoRepository;
        this.stockConfig = stockConfig;
    }

    
    @Override
    public List<AlertaStockResponse> obtenerProductosEnAlerta() {
        return productoRepository.findAll().stream()
                .filter(p -> p.getStock() < stockConfig.minimo())
                .map(this::toAlertaResponse)
                .toList();
    }

    
    private NivelAlerta calcularNivel(Producto producto) {
        if (producto.getStock() < stockConfig.critico()) {
            return NivelAlerta.CRITICO;
        }
        return NivelAlerta.BAJO;
    }

    
    private AlertaStockResponse toAlertaResponse(Producto p) {
        return new AlertaStockResponse(p.getId(), p.getNombre(),
                p.getStock(), calcularNivel(p));
    }
}

