package unlar.edu.ar.service;

import unlar.edu.ar.dto.ProductoRequest;
import unlar.edu.ar.dto.ProductoResponse;
import unlar.edu.ar.exception.ResourceNotFoundException;

import java.util.List;


public interface ProductoService {

    
    List<ProductoResponse> listarTodos(Long categoriaId, Double precioMin,
                                       Double precioMax, Boolean enStock);

    
    ProductoResponse buscarPorId(Long id);

    
    ProductoResponse crear(ProductoRequest request);

    
    ProductoResponse actualizar(Long id, ProductoRequest request);

    
    void eliminar(Long id);

    
    List<ProductoResponse> buscarPorNombre(String texto);

    
    List<ProductoResponse> listarOrdenados(String campo, String orden);
}

