package unlar.edu.ar.Tp3.exception;


public class ResourceNotFoundException extends RuntimeException {

    
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}

