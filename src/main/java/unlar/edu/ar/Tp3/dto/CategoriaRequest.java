package unlar.edu.ar.Tp3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres") String nombre,

        @Size(max = 200, message = "La descripciÃ³n no puede superar los 200 caracteres") String descripcion) {
}
