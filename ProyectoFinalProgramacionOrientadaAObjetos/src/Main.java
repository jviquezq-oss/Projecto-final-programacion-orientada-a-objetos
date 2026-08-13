import Controlador.Controlador;

public class Main {

    public static void main(String[] args) {

        // Iniciar sesión
        Controlador.iniciarSesion();

        // Probar compra de una canción
        Controlador.comprarCancion();

        // Probar nuevamente la compra
        Controlador.comprarCancion();
    }
}