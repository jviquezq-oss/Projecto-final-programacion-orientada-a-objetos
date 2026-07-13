package Excepciones;

public class CancionNoEncontradaException extends Exception {
    public CancionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
