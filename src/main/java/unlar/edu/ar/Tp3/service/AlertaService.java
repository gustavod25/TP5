package unlar.edu.ar.Tp3.service;

import unlar.edu.ar.dto.Tp3.AlertaStockResponse;
import java.util.List;


public interface AlertaService {

    
    List<AlertaStockResponse> obtenerProductosEnAlerta();
}

