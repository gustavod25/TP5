package unlar.edu.ar.Tp3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError("Recurso no encontrado", ex.getMessage()));
    }

    
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientStock(InsufficientStockException ex) {
        Map<String, Object> body = buildError("Stock insuficiente", ex.getMessage());
        body.put("productoId", ex.getProductoId());
        body.put("stockDisponible", ex.getStockDisponible());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessRule(BusinessRuleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError("Regla de negocio violada", ex.getMessage()));
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError("Error de validaciÃ³n", errores));
    }

    
    private Map<String, Object> buildError(String error, String mensaje) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", error);
        body.put("mensaje", mensaje);
        body.put("timestamp", LocalDateTime.now().toString());
        return body;
    }
}

