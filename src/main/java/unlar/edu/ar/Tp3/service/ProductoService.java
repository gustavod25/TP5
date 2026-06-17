package unlar.edu.ar.Tp3.service;

import unlar.edu.ar.Tp3.dto.ProductoRequest;
import unlar.edu.ar.Tp3.dto.ProductoResponse;

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
