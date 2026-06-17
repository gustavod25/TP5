package unlar.edu.ar.Tp3.dto;

import unlar.edu.ar.Tp3.model.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MovimientoRequest(
        @NotNull(message = "El id del producto es obligatorio") Long productoId,

        @NotNull(message = "El tipo de movimiento es obligatorio (ENTRADA o SALIDA)") TipoMovimiento tipo,

        @Positive(message = "La cantidad debe ser mayor a 0") int cantidad,

        @Size(max = 200, message = "El motivo no puede superar los 200 caracteres") String motivo) {
}
