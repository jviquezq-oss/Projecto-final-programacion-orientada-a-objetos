package LogicaNegocio;

import Entidades.Usuario;

public class SesionUsuario {
    private static Usuario usuarioActivo;

    public static Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public static void setUsuarioActivo(Usuario usuarioActivo) {
        SesionUsuario.usuarioActivo = usuarioActivo;
    }
}
