package unlar.edu.ar.Tp3.dto;

import unlar.edu.ar.Tp3.model.NivelAlerta;

public record AlertaStockResponse(
                Long productoId,
                String nombre,
                int stock,
                NivelAlerta nivelAlerta) {
}
