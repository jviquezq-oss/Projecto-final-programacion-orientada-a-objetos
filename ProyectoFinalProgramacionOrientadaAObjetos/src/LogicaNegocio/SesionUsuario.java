package LogicaNegocio;

import Entidades.Cuenta;
import Entidades.Reproductor;

public final class SesionUsuario {

    private static Cuenta cuentaActiva;
    private static Reproductor reproductor;

    private SesionUsuario() {
    }

    public static Cuenta getCuentaActiva() {
        return cuentaActiva;
    }

    public static void setCuentaActiva(Cuenta cuentaActiva) {
        SesionUsuario.cuentaActiva = cuentaActiva;
        SesionUsuario.reproductor = new Reproductor();
    }

    public static Reproductor getReproductor() {
        return reproductor;
    }

    public static boolean hayCuentaActiva() {
        return cuentaActiva != null;
    }

    public static void cerrarSesion() {
        cuentaActiva = null;
        reproductor = null;
    }
}