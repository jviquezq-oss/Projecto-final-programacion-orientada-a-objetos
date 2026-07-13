package Excepciones;

public class ColaVaciaException extends Exception {
    public ColaVaciaException(String mensaje) {
        super(mensaje);
    }
}