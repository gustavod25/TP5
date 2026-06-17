package unlar.edu.ar.Tp3.controller;

import unlar.edu.ar.Tp3.dto.*;
import unlar.edu.ar.Tp3.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/categorias")
@Tag(name = "CategorÃ­as", description = "CRUD de categorÃ­as de productos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    
    @GetMapping
    @Operation(summary = "Listar todas las categorÃ­as")
    public ResponseEntity<List<CategoriaResponse>> listar() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener categorÃ­a por ID")
    public ResponseEntity<CategoriaResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    
    @PostMapping
    @Operation(summary = "Crear nueva categorÃ­a")
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse creada = categoriaService.crear(request);
        return ResponseEntity.created(URI.create("/api/categorias/" + creada.id()))
                .body(creada);
    }

    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categorÃ­a")
    public ResponseEntity<CategoriaResponse> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.actualizar(id, request));
    }

    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categorÃ­a (falla con 409 si tiene productos)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

