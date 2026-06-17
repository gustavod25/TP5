package unlar.edu.ar.Tp3.controller;

import unlar.edu.ar.Tp3.dto.AlertaStockResponse;
import unlar.edu.ar.Tp3.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@Tag(name = "Alertas y Admin", description = "Alertas de stock bajo y reporte de performance")
public class AlertaController {

    private final AlertaService alertaService;
    private final PerformanceReportService performanceReportService;

    
    public AlertaController(AlertaService alertaService,
                             PerformanceReportService performanceReportService) {
        this.alertaService = alertaService;
        this.performanceReportService = performanceReportService;
    }

    
    @GetMapping("/api/alertas/stock-bajo")
    @Operation(summary = "Listar productos con stock bajo o crÃ­tico")
    public ResponseEntity<List<AlertaStockResponse>> stockBajo() {
        return ResponseEntity.ok(alertaService.obtenerProductosEnAlerta());
    }

    
    @GetMapping("/api/admin/performance-report")
    @Operation(summary = "Reporte de performance Big O (puede tardar ~30 seg)")
    public ResponseEntity<Map<String, Object>> performanceReport() {
        return ResponseEntity.ok(performanceReportService.generarReporte());
    }
}

