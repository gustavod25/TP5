package unlar.edu.ar.Tp3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductoRequest(
                @NotBlank(message = "El nombre es obligatorio") @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres") String nombre,

                @Size(max = 500, message = "La descripciÃ³n no puede superar los 500 caracteres") String descripcion,

                @PositiveOrZero(message = "El precio debe ser mayor o igual a 0") double precio,

                @PositiveOrZero(message = "El stock inicial debe ser mayor o igual a 0") int stockInicial,

                @NotNull(message = "La categorÃ­a es obligatoria") Long categoriaId) {
}
