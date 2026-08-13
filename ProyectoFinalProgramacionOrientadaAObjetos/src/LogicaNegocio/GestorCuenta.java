package LogicaNegocio;

import DAO.CuentaDAO;
import Entidades.Cuenta;

public class GestorCuenta {

    public static Cuenta registrarCuenta(String correoElectronico, String contrasena, String nombreUsuario) throws Exception {
        Cuenta nuevaCuenta = new Cuenta(correoElectronico, contrasena, nombreUsuario);
        return CuentaDAO.insertar(nuevaCuenta);
    }

    public static Cuenta buscarPorCorreoElectronico(String correoElectronico) throws Exception {
        return CuentaDAO.buscarPorCorreoElectronico(correoElectronico);
    }

    public static Cuenta buscarPorNombreUsuario(String nombreUsuario) throws Exception {
        return CuentaDAO.buscarPorNombreUsuario(nombreUsuario);
    }

    public static Cuenta buscarPorId(int idCuenta) throws Exception {
        return CuentaDAO.buscarPorId(idCuenta);
    }
    public static void actualizarContrasena(int idCuenta, String contrasena) throws Exception {
        Cuenta cuenta = CuentaDAO.buscarPorId(idCuenta);

        if (cuenta == null) {
            throw new Exception("La cuenta no existe.");
        }

        CuentaDAO.actualizarContrasena(idCuenta, contrasena);
    }

    public static void cambiarContrasena(int idCuenta, String contrasenaActual, String nuevaContrasena, String confirmacion) throws Exception {
        Cuenta cuenta = CuentaDAO.buscarPorId(idCuenta);
        if (cuenta == null) {
            throw new Exception("La cuenta no existe.");
        }
        cuenta.cambiarContrasena(contrasenaActual, nuevaContrasena, confirmacion);
        CuentaDAO.actualizar(cuenta);
    }

    public static void actualizarCuenta(Cuenta cuenta) throws Exception {
        CuentaDAO.actualizar(cuenta);
    }

    public static void actualizarCuentaPorCorreoElectronico(String correoElectronico, String nuevoCorreoElectronico, String nuevaContrasena, String nuevoNombreUsuario) throws Exception {
        Cuenta cuenta = CuentaDAO.buscarPorCorreoElectronico(correoElectronico);
        if (cuenta == null) {
            throw new Exception("La cuenta no existe.");
        }
        cuenta.setCorreoElectronico(nuevoCorreoElectronico);
        cuenta.setContrasena(nuevaContrasena);
        cuenta.setNombreUsuario(nuevoNombreUsuario);
        CuentaDAO.actualizar(cuenta);
    }

    public static void actualizarCuentaPorNombreUsuario(String nombreUsuario, String nuevoCorreoElectronico, String nuevaContrasena, String nuevoNombreUsuario) throws Exception {
        Cuenta cuenta = CuentaDAO.buscarPorNombreUsuario(nombreUsuario);
        if (cuenta == null) {
            throw new Exception("La cuenta no existe.");
        }
        cuenta.setCorreoElectronico(nuevoCorreoElectronico);
        cuenta.setContrasena(nuevaContrasena);
        cuenta.setNombreUsuario(nuevoNombreUsuario);
        CuentaDAO.actualizar(cuenta);
    }

    public static void eliminarCuenta(int idCuenta) throws Exception {
        CuentaDAO.eliminar(idCuenta);
    }

    public static void eliminarCuentaPorCorreoElectronico(String correoElectronico) throws Exception {
        CuentaDAO.eliminarPorCorreoElectronico(correoElectronico);
    }

    public static void eliminarCuentaPorNombreUsuario(String nombreUsuario) throws Exception {
        CuentaDAO.eliminarPorNombreUsuario(nombreUsuario);
    }
    public static String obtenerTipoCuenta(int idCuenta) throws Exception {
        return CuentaDAO.obtenerTipoCuenta(idCuenta);
    }
}