package LogicaNegocio;

import Entidades.Administador;
import Entidades.Usuario;

public final class SesionUsuario {
    private static Usuario usuarioActivo;
    private static Administador administradorActivo;

    private SesionUsuario() {
    }

    public static Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public static void setUsuarioActivo(Usuario usuarioActivo) {
        SesionUsuario.usuarioActivo = usuarioActivo;
    }

    public static Administador getAdministradorActivo() {
        return administradorActivo;
    }

    public static void setAdministradorActivo(Administador administradorActivo) {
        SesionUsuario.administradorActivo = administradorActivo;
    }

    public static boolean hayUsuarioActivo() {
        return usuarioActivo != null;
    }

    public static boolean hayAdministradorActivo() {
        return administradorActivo != null;
    }

    public static void cerrarSesion() {
        usuarioActivo = null;
        administradorActivo = null;
    }
}
