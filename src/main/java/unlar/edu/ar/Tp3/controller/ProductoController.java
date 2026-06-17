package unlar.edu.ar.Tp3.controller;

import unlar.edu.ar.Tp3.dto.*;
import unlar.edu.ar.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "CRUD y consultas de productos del inventario")
public class ProductoController {

    private final ProductoService productoService;

    
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    
    @GetMapping
    @Operation(summary = "Listar productos (con filtros opcionales)",
               description = "Filtros opcionales: categoria, precioMin, precioMax, enStock")
    public ResponseEntity<List<ProductoResponse>> listar(
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) Boolean enStock) {
        return ResponseEntity.ok(productoService.listarTodos(categoria, precioMin, precioMax, enStock));
    }

    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ProductoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    
    @GetMapping("/buscar")
    @Operation(summary = "BÃºsqueda textual de productos por nombre")
    public ResponseEntity<List<ProductoResponse>> buscar(
            @RequestParam @NotBlank(message = "El parÃ¡metro q es obligatorio") String q) {
        return ResponseEntity.ok(productoService.buscarPorNombre(q));
    }

    
    @GetMapping("/ordenados")
    @Operation(summary = "Listar productos ordenados (O(n log n) â€” TimSort)")
    public ResponseEntity<List<ProductoResponse>> ordenados(
            @RequestParam(defaultValue = "nombre") String campo,
            @RequestParam(defaultValue = "asc")   String orden) {
        return ResponseEntity.ok(productoService.listarOrdenados(campo, orden));
    }

    
    @PostMapping
    @Operation(summary = "Crear nuevo producto")
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse creado = productoService.crear(request);
        return ResponseEntity.created(URI.create("/api/productos/" + creado.id()))
                .body(creado);
    }

    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto existente")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.actualizar(id, request));
    }

    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

