package LogicaNegocio;

import DAO.CompraDAO;
import DAO.UsuarioDAO;
import Entidades.Cancion;
import Entidades.Cuenta;
import Entidades.Usuario;
import Excepciones.ParametroInvalidoException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class GestorUsuario {

    public static Usuario registrarUsuario(String nombreCompleto, LocalDate fechaDeNacimiento, String nacionalidad, String cedula, String avatar, String nombreUsuario, String correoElectronico, String contrasena, String confirmacionContrasena) throws Exception {
        if (!Usuario.esMayorDeEdad(fechaDeNacimiento)) {
            throw new ParametroInvalidoException("El usuario es menor de edad.");
        }

        Cuenta.validarContrasena(contrasena);

        if (!contrasena.equals(confirmacionContrasena)) {
            throw new ParametroInvalidoException("La contraseña y su confirmación no coinciden.");
        }

        try {
            Cuenta nuevaCuenta = GestorCuenta.registrarCuenta(correoElectronico, contrasena, nombreUsuario);
            Usuario nuevoUsuario = new Usuario(nuevaCuenta.getIdCuenta(), nombreCompleto, fechaDeNacimiento, nacionalidad, cedula, avatar, nombreUsuario, correoElectronico, contrasena);

            return UsuarioDAO.insertar(nuevoUsuario);

        } catch (SQLException e) {
            throw new Exception("La operación registrar usuario no pudo ser realizada.", e);
        }
    }

    public static List<Usuario> obtenerTodos() throws Exception {
        try {
            return UsuarioDAO.listarTodos();

        } catch (SQLException e) {
            throw new Exception("La operación obtener usuarios no pudo ser realizada.", e);
        }
    }

    public static Usuario buscarPorNombreUsuario(String nombreUsuario) throws Exception {
        Cuenta cuentaDeUsuario = GestorCuenta.buscarPorNombreUsuario(nombreUsuario);

        if (cuentaDeUsuario == null) {
            throw new Exception("El usuario no fue econtrado");
        }

        return buscarPorId(cuentaDeUsuario.getIdCuenta());
    }

    public static Usuario buscarPorId(int idCuenta) throws Exception {
        try {
            Usuario usuario = UsuarioDAO.buscarPorId(idCuenta);

            if (usuario == null) {
                throw new Exception("El usuario no existe.");
            }

            return usuario;

        } catch (SQLException e) {
            throw new Exception("La operación buscar usuario por ID no pudo ser realizada.", e);
        }
    }

    public static void actualizarUsuario(int idCuenta, String nombreCompleto, LocalDate fechaDeNacimiento, String nacionalidad, String cedula, String avatar) throws Exception {
        try {
            Usuario usuario = UsuarioDAO.buscarPorId(idCuenta);

            if (usuario == null) {
                throw new Exception("El usuario no existe.");
            }

            usuario.setNombrCompleto(nombreCompleto);
            usuario.setFechaDeNacimiento(fechaDeNacimiento);
            usuario.setNacionalidad(nacionalidad);
            usuario.setCedula(cedula);
            usuario.setAvatar(avatar);

            UsuarioDAO.actualizar(usuario);

        } catch (SQLException e) {
            throw new Exception("La operación actualizar usuario no pudo ser realizada.", e);
        }
    }

    public static void eliminarUsuario(int idCuenta) throws Exception {
        try {
            Usuario usuario = UsuarioDAO.buscarPorId(idCuenta);

            if (usuario == null) {
                throw new Exception("El usuario no existe.");
            }

            UsuarioDAO.eliminar(idCuenta);

        } catch (SQLException e) {
            throw new Exception("La operación eliminar usuario no pudo ser realizada.", e);
        }
    }

    public static void agregarSaldo(int idCuenta, double cantidad) throws Exception {
        try {
            Usuario usuario = UsuarioDAO.buscarPorId(idCuenta);

            if (usuario == null) {
                throw new Exception("El usuario no existe.");
            }

            if (cantidad <= 0) {
                throw new Exception("La cantidad debe ser mayor que cero.");
            }

            UsuarioDAO.agregarSaldo(idCuenta, cantidad);

        } catch (SQLException e) {
            throw new Exception("La operación agregar saldo no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> obtenerCancionesCompradas(int idUsuario) throws Exception {
        try {
            Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                throw new Exception("El usuario no existe.");
            }

            return CompraDAO.obtenerCancionesCompradas(idUsuario);

        } catch (SQLException e) {
            throw new Exception("La operación obtener canciones compradas no pudo ser realizada.", e);
        }
    }

}