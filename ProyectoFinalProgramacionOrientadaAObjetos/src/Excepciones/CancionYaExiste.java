package Excepciones;

public class CancionYaExiste extends RuntimeException {
    public CancionYaExiste(String message) {
        super(message);
    }
}
