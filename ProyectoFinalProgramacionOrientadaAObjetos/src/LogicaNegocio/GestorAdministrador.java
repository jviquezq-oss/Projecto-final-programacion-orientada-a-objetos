package LogicaNegocio;

import DAO.AdministradorDAO;
import DAO.CuentaDAO;
import Entidades.Administador;
import Entidades.Cuenta;

public class GestorAdministrador {

    public static Administador registrarAdministrador(String correoElectronico, String contrasena, String nombreUsuario) throws Exception {
        Cuenta nuevaCuenta = new Cuenta(correoElectronico, contrasena, nombreUsuario);
        CuentaDAO.insertar(nuevaCuenta);

        Administador nuevoAdministrador = new Administador(nuevaCuenta.getIdCuenta(), correoElectronico, contrasena, nombreUsuario);
        return AdministradorDAO.insertar(nuevoAdministrador);
    }

    public static Administador buscarPorId(int idCuenta) throws Exception {
        Administador administrador = AdministradorDAO.buscarPorId(idCuenta);

        if (administrador == null) {
            throw new Exception("El administrador no existe.");
        }

        return administrador;
    }

    public static Administador buscarPorCorreoElectronico(String correoElectronico) throws Exception {
        Administador administrador = AdministradorDAO.buscarPorCorreoElectronico(correoElectronico);

        if (administrador == null) {
            throw new Exception("El administrador no existe.");
        }

        return administrador;
    }

    public static void eliminarAdministrador(int idCuenta) throws Exception {
        Administador administrador = AdministradorDAO.buscarPorId(idCuenta);

        if (administrador == null) {
            throw new Exception("El administrador no existe.");
        }

        AdministradorDAO.eliminar(idCuenta);
    }
}