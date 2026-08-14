package LogicaNegocio;

import DAO.AdministradorDAO;
import DAO.CuentaDAO;
import Entidades.Administador;
import Entidades.Cuenta;

import java.sql.SQLException;

public class GestorAdministrador {

    public static Administador registrarAdministrador(String correoElectronico, String contrasena, String nombreUsuario) throws Exception {
        try {
            Cuenta nuevaCuenta = new Cuenta(correoElectronico, contrasena, nombreUsuario);
            CuentaDAO.insertar(nuevaCuenta);

            Administador nuevoAdministrador = new Administador(nuevaCuenta.getIdCuenta(), correoElectronico, contrasena, nombreUsuario);
            return AdministradorDAO.insertar(nuevoAdministrador);

        } catch (SQLException e) {
            throw new Exception("La operación registrar administrador no pudo ser realizada.", e);
        }
    }

    public static Administador buscarPorId(int idCuenta) throws Exception {
        try {
            Administador administrador = AdministradorDAO.buscarPorId(idCuenta);

            if (administrador == null) {
                throw new Exception("El administrador no existe.");
            }

            return administrador;

        } catch (SQLException e) {
            throw new Exception("La operación buscar administrador por ID no pudo ser realizada.", e);
        }
    }

    public static Administador buscarPorCorreoElectronico(String correoElectronico) throws Exception {
        try {
            Administador administrador = AdministradorDAO.buscarPorCorreoElectronico(correoElectronico);

            if (administrador == null) {
                throw new Exception("El administrador no existe.");
            }

            return administrador;

        } catch (SQLException e) {
            throw new Exception("La operación buscar administrador por correo electrónico no pudo ser realizada.", e);
        }
    }

    public static void eliminarAdministrador(int idCuenta) throws Exception {
        try {
            Administador administrador = AdministradorDAO.buscarPorId(idCuenta);

            if (administrador == null) {
                throw new Exception("El administrador no existe.");
            }

            AdministradorDAO.eliminar(idCuenta);

        } catch (SQLException e) {
            throw new Exception("La operación eliminar administrador no pudo ser realizada.", e);
        }
    }

    public static boolean hayAdministradorRegistrado() throws Exception {
        try {
            return AdministradorDAO.hayAdministradorRegistrado();

        } catch (SQLException e) {
            throw new Exception("La operación verificar administrador registrado no pudo ser realizada.", e);
        }
    }
}