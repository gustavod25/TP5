package unlar.edu.ar.Tp3.service;

import unlar.edu.ar.Tp3.dto.CategoriaRequest;
import unlar.edu.ar.Tp3.dto.CategoriaResponse;
import unlar.edu.ar.Tp3.exception.BusinessRuleException;
import unlar.edu.ar.Tp3.exception.ResourceNotFoundException;
import unlar.edu.ar.Tp3.model.Categoria;
import unlar.edu.ar.Tp3.repository.CategoriaRepository;
import unlar.edu.ar.Tp3.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<CategoriaResponse> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoriaResponse buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CategorÃ­a con id " + id + " no encontrada"));
    }

    @Override
    public CategoriaResponse crear(CategoriaRequest request) {
        Categoria categoria = new Categoria(null, request.nombre(), request.descripcion());
        return toResponse(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CategorÃ­a con id " + id + " no encontrada"));
        existente.setNombre(request.nombre());
        existente.setDescripcion(request.descripcion());
        return toResponse(categoriaRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("CategorÃ­a con id " + id + " no encontrada");
        }
        boolean tieneProductos = !productoRepository.findByCategoria(id).isEmpty();
        if (tieneProductos) {
            throw new BusinessRuleException(
                    "No se puede eliminar la categorÃ­a " + id +
                            " porque tiene productos asociados.");
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaResponse toResponse(Categoria c) {
        return new CategoriaResponse(c.getId(), c.getNombre(), c.getDescripcion());
    }
}
