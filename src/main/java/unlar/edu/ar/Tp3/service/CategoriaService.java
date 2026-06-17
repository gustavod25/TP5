package unlar.edu.ar.Tp3.service;

import unlar.edu.ar.Tp3.dto.CategoriaRequest;
import unlar.edu.ar.Tp3.dto.CategoriaResponse;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponse> listarTodas();

    CategoriaResponse buscarPorId(Long id);

    CategoriaResponse crear(CategoriaRequest request);

    CategoriaResponse actualizar(Long id, CategoriaRequest request);

    void eliminar(Long id);
}
