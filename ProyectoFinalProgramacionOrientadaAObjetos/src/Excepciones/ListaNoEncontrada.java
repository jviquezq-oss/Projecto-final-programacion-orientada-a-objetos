package Excepciones;

public class ListaNoEncontrada extends RuntimeException {
    public ListaNoEncontrada(String message) {
        super(message);
    }
}
