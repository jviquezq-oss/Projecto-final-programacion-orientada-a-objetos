package LogicaNegocio;

import DAO.CuentaDAO;
import Entidades.Cuenta;

import java.sql.SQLException;

public class GestorCuenta {

    public static Cuenta registrarCuenta(String correoElectronico, String contrasena, String nombreUsuario) throws Exception {
        try {
            Cuenta nuevaCuenta = new Cuenta(correoElectronico, contrasena, nombreUsuario);
            return CuentaDAO.insertar(nuevaCuenta);

        } catch (SQLException e) {
            throw new Exception("La operación registrar cuenta no pudo ser realizada.", e);
        }
    }

    public static Cuenta buscarPorCorreoElectronico(String correoElectronico) throws Exception {
        try {
            return CuentaDAO.buscarPorCorreoElectronico(correoElectronico);

        } catch (SQLException e) {
            throw new Exception("La operación buscar cuenta por correo electrónico no pudo ser realizada.", e);
        }
    }

    public static Cuenta buscarPorNombreUsuario(String nombreUsuario) throws Exception {
        try {
            return CuentaDAO.buscarPorNombreUsuario(nombreUsuario);

        } catch (SQLException e) {
            throw new Exception("La operación buscar cuenta por nombre de usuario no pudo ser realizada.", e);
        }
    }

    public static Cuenta buscarPorId(int idCuenta) throws Exception {
        try {
            return CuentaDAO.buscarPorId(idCuenta);

        } catch (SQLException e) {
            throw new Exception("La operación buscar cuenta por ID no pudo ser realizada.", e);
        }
    }

    public static void actualizarContrasena(int idCuenta, String contrasena) throws Exception {
        try {
            Cuenta cuenta = CuentaDAO.buscarPorId(idCuenta);

            if (cuenta == null) {
                throw new Exception("La cuenta no existe.");
            }

            CuentaDAO.actualizarContrasena(idCuenta, contrasena);

        } catch (SQLException e) {
            throw new Exception("La operación actualizar contraseña no pudo ser realizada.", e);
        }
    }

    public static void cambiarContrasena(int idCuenta, String contrasenaActual, String nuevaContrasena, String confirmacion) throws Exception {
        try {
            Cuenta cuenta = CuentaDAO.buscarPorId(idCuenta);

            if (cuenta == null) {
                throw new Exception("La cuenta no existe.");
            }

            cuenta.cambiarContrasena(contrasenaActual, nuevaContrasena, confirmacion);
            CuentaDAO.actualizar(cuenta);

        } catch (SQLException e) {
            throw new Exception("La operación cambiar contraseña no pudo ser realizada.", e);
        }
    }

    public static void actualizarCuenta(Cuenta cuenta) throws Exception {
        try {
            CuentaDAO.actualizar(cuenta);

        } catch (SQLException e) {
            throw new Exception("La operación actualizar cuenta no pudo ser realizada.", e);
        }
    }

    public static void actualizarCuentaPorCorreoElectronico(String correoElectronico, String nuevoCorreoElectronico, String nuevaContrasena, String nuevoNombreUsuario) throws Exception {
        try {
            Cuenta cuenta = CuentaDAO.buscarPorCorreoElectronico(correoElectronico);

            if (cuenta == null) {
                throw new Exception("La cuenta no existe.");
            }

            cuenta.setCorreoElectronico(nuevoCorreoElectronico);
            cuenta.setContrasena(nuevaContrasena);
            cuenta.setNombreUsuario(nuevoNombreUsuario);
            CuentaDAO.actualizar(cuenta);

        } catch (SQLException e) {
            throw new Exception("La operación actualizar cuenta por correo electrónico no pudo ser realizada.", e);
        }
    }

    public static void actualizarCuentaPorNombreUsuario(String nombreUsuario, String nuevoCorreoElectronico, String nuevaContrasena, String nuevoNombreUsuario) throws Exception {
        try {
            Cuenta cuenta = CuentaDAO.buscarPorNombreUsuario(nombreUsuario);

            if (cuenta == null) {
                throw new Exception("La cuenta no existe.");
            }

            cuenta.setCorreoElectronico(nuevoCorreoElectronico);
            cuenta.setContrasena(nuevaContrasena);
            cuenta.setNombreUsuario(nuevoNombreUsuario);
            CuentaDAO.actualizar(cuenta);

        } catch (SQLException e) {
            throw new Exception("La operación actualizar cuenta por nombre de usuario no pudo ser realizada.", e);
        }
    }

    public static void eliminarCuenta(int idCuenta) throws Exception {
        try {
            CuentaDAO.eliminar(idCuenta);

        } catch (SQLException e) {
            throw new Exception("La operación eliminar cuenta no pudo ser realizada.", e);
        }
    }

    public static void eliminarCuentaPorCorreoElectronico(String correoElectronico) throws Exception {
        try {
            CuentaDAO.eliminarPorCorreoElectronico(correoElectronico);

        } catch (SQLException e) {
            throw new Exception("La operación eliminar cuenta por correo electrónico no pudo ser realizada.", e);
        }
    }

    public static void eliminarCuentaPorNombreUsuario(String nombreUsuario) throws Exception {
        try {
            CuentaDAO.eliminarPorNombreUsuario(nombreUsuario);

        } catch (SQLException e) {
            throw new Exception("La operación eliminar cuenta por nombre de usuario no pudo ser realizada.", e);
        }
    }

    public static String obtenerTipoCuenta(int idCuenta) throws Exception {
        try {
            return CuentaDAO.obtenerTipoCuenta(idCuenta);

        } catch (SQLException e) {
            throw new Exception("La operación obtener tipo de cuenta no pudo ser realizada.", e);
        }
    }
}