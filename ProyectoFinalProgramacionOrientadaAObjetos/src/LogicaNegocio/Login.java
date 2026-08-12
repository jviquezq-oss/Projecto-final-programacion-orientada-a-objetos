package LogicaNegocio;

import Entidades.Administador;
import Entidades.Cuenta;
import Entidades.Usuario;
import Excepciones.CredencialesInvalidasException;

public class Login {

    private Login() {
    }

    public static void iniciarSesion(String correoElectronico, String contrasena) throws Exception {
        Cuenta cuenta = GestorCuenta.buscarPorCorreoElectronico(correoElectronico);
        if (cuenta == null || !cuenta.getContrasena().equals(contrasena)) {
            throw new CredencialesInvalidasException("El correo electrónico o la contraseña son incorrectos.");
        }
        String tipoCuenta = GestorCuenta.obtenerTipoCuenta(cuenta.getIdCuenta());
        if ("Administrador".equals(tipoCuenta)) {
            Administador administrador = GestorAdministrador.buscarPorId(cuenta.getIdCuenta());
            SesionUsuario.setAdministradorActivo(administrador);
            return;
        }

        if ("Usuario".equals(tipoCuenta)) {
            Usuario usuario = GestorUsuario.buscarPorId(cuenta.getIdCuenta());
            SesionUsuario.setUsuarioActivo(usuario);
            return;
        }

        throw new CredencialesInvalidasException("La cuenta no tiene un tipo válido.");
    }
}