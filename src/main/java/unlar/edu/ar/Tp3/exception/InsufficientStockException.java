package unlar.edu.ar.Tp3.exception;


public class InsufficientStockException extends RuntimeException {

    private final Long productoId;
    private final int stockDisponible;
    private final int cantidadSolicitada;

    
    public InsufficientStockException(Long productoId, int stockDisponible, int cantidadSolicitada) {
        super(String.format("No se pueden retirar %d unidades. Stock disponible: %d",
                cantidadSolicitada, stockDisponible));
        this.productoId = productoId;
        this.stockDisponible = stockDisponible;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    
    public Long getProductoId() { return productoId; }

    
    public int getStockDisponible() { return stockDisponible; }

    
    public int getCantidadSolicitada() { return cantidadSolicitada; }
}

