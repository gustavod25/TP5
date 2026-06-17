package unlar.edu.ar.Tp3.service;

import unlar.edu.ar.Tp3.dto.CategoriaResponse;
import unlar.edu.ar.Tp3.dto.ProductoRequest;
import unlar.edu.ar.Tp3.dto.ProductoResponse;
import unlar.edu.ar.Tp3.exception.ResourceNotFoundException;
import unlar.edu.ar.Tp3.model.Categoria;
import unlar.edu.ar.Tp3.model.Producto;
import unlar.edu.ar.Tp3.repository.CategoriaRepository;
import unlar.edu.ar.Tp3.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<ProductoResponse> listarTodos(Long categoriaId, Double precioMin,
            Double precioMax, Boolean enStock) {
        Stream<Producto> stream = productoRepository.findAll().stream();
        if (categoriaId != null) {
            stream = stream.filter(p -> p.getCategoria().getId().equals(categoriaId));
        }
        if (precioMin != null) {
            stream = stream.filter(p -> p.getPrecio() >= precioMin);
        }
        if (precioMax != null) {
            stream = stream.filter(p -> p.getPrecio() <= precioMax);
        }
        if (Boolean.TRUE.equals(enStock)) {
            stream = stream.filter(p -> p.getStock() > 0);
        }
        return stream.map(this::toResponse).toList();
    }

    @Override
    public ProductoResponse buscarPorId(Long id) {
        return productoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto con id " + id + " no encontrado"));
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CategorÃ­a con id " + request.categoriaId() + " no encontrada"));
        Producto producto = new Producto(null, request.nombre(), request.descripcion(),
                request.precio(), request.stockInicial(), categoria);
        return toResponse(productoRepository.save(producto));
    }

    @Override
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto con id " + id + " no encontrado"));
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CategorÃ­a con id " + request.categoriaId() + " no encontrada"));
        existente.setNombre(request.nombre());
        existente.setDescripcion(request.descripcion());
        existente.setPrecio(request.precio());
        existente.setCategoria(categoria);
        return toResponse(productoRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto con id " + id + " no encontrado");
        }
        productoRepository.deleteById(id);
    }

    @Override
    public List<ProductoResponse> buscarPorNombre(String texto) {
        return productoRepository.buscarPorNombre(texto).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ProductoResponse> listarOrdenados(String campo, String orden) {
        Comparator<Producto> comparator = switch (campo.toLowerCase()) {
            case "precio" -> Comparator.comparingDouble(Producto::getPrecio);
            case "stock" -> Comparator.comparingInt(Producto::getStock);
            default -> Comparator.comparing(Producto::getNombre);
        };
        if ("desc".equalsIgnoreCase(orden)) {
            comparator = comparator.reversed();
        }
        return productoRepository.findAll().stream()
                .sorted(comparator)
                .map(this::toResponse)
                .toList();
    }

    private ProductoResponse toResponse(Producto p) {
        CategoriaResponse cat = new CategoriaResponse(
                p.getCategoria().getId(),
                p.getCategoria().getNombre(),
                p.getCategoria().getDescripcion());
        return new ProductoResponse(p.getId(), p.getNombre(), p.getDescripcion(),
                p.getPrecio(), p.getStock(), cat);
    }
}
