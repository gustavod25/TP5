package unlar.edu.ar.Tp3.controller;

import unlar.edu.ar.Tp3.dto.*;
import unlar.edu.ar.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/movimientos")
@Tag(name = "Movimientos", description = "Registro y consulta de movimientos de inventario")
public class MovimientoController {

    private final MovimientoService movimientoService;

    
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    
    @PostMapping
    @Operation(summary = "Registrar movimiento de inventario (ENTRADA o SALIDA)")
    public ResponseEntity<MovimientoResponse> registrar(@Valid @RequestBody MovimientoRequest request) {
        return ResponseEntity.ok(movimientoService.registrar(request));
    }

    
    @GetMapping("/producto/{id}")
    @Operation(summary = "Historial de movimientos de un producto")
    public ResponseEntity<List<MovimientoResponse>> listarPorProducto(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.listarPorProducto(id));
    }
}

