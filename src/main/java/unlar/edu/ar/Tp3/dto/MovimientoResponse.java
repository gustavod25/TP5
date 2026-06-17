package unlar.edu.ar.Tp3.dto;

import unlar.edu.ar.Tp3.model.TipoMovimiento;
import java.time.LocalDateTime;

public record MovimientoResponse(
                Long id,
                Long productoId,
                TipoMovimiento tipo,
                int cantidad,
                int stockResultante,
                String motivo,
                LocalDateTime fecha) {
}
