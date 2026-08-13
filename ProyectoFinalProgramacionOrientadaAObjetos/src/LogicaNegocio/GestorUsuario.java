package LogicaNegocio;

import DAO.CancionDAO;
import DAO.CompraDAO;
import DAO.UsuarioDAO;
import Entidades.Cancion;
import Entidades.Cuenta;
import Entidades.Usuario;
import java.time.LocalDate;
import java.util.List;

public class GestorUsuario {

    public static Usuario registrarUsuario(String nombreCompleto, LocalDate fechaDeNacimiento, String nacionalidad, String cedula, String avatar, String nombreUsuario, String correoElectronico, String contrasena) throws Exception {
        Cuenta nuevaCuenta = GestorCuenta.registrarCuenta(correoElectronico, contrasena, nombreUsuario);

        Usuario nuevoUsuario = new Usuario(nuevaCuenta.getIdCuenta(), nombreCompleto, fechaDeNacimiento, nacionalidad, cedula, avatar, nombreUsuario, correoElectronico, contrasena);

        return UsuarioDAO.insertar(nuevoUsuario);
    }
    public static List<Usuario> obtenerTodos() throws Exception {
        return UsuarioDAO.listarTodos();
    }
    public static Usuario buscarPorNombreUsuario(String nombreUsuario   ) throws Exception {
        Cuenta cuentaDeUsuario = GestorCuenta.buscarPorNombreUsuario(nombreUsuario);
        if(cuentaDeUsuario == null){
            throw new Exception("El usuario no fue econtrado");
        }
        return buscarPorId(cuentaDeUsuario.getIdCuenta());
    }
    public static Usuario buscarPorId(int idCuenta) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idCuenta);

        if (usuario == null) {
            throw new Exception("El usuario no existe.");
        }

        return usuario;
    }

    public static void actualizarUsuario(int idCuenta, String nombreCompleto, LocalDate fechaDeNacimiento, String nacionalidad, String cedula, String avatar) throws Exception {
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
    }

    public static void eliminarUsuario(int idCuenta) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idCuenta);

        if (usuario == null) {
            throw new Exception("El usuario no existe.");
        }

        UsuarioDAO.eliminar(idCuenta);
    }

    public static void agregarSaldo(int idCuenta, double cantidad) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idCuenta);

        if (usuario == null) {
            throw new Exception("El usuario no existe.");
        }

        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor que cero.");
        }

        UsuarioDAO.agregarSaldo(idCuenta, cantidad);
    }

    public static List<Cancion> obtenerCancionesCompradas(int idUsuario) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new Exception("El usuario no existe.");
        }

        return CompraDAO.obtenerCancionesCompradas(idUsuario);
    }

    public static void comprarCancion(int idUsuario, int idCancion) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new Exception("El usuario no existe.");
        }

        Cancion cancion = CancionDAO.buscarPorId(idCancion);

        if (cancion == null) {
            throw new Exception("La canción no existe.");
        }

        if (CompraDAO.existe(idUsuario, idCancion)) {
            throw new Exception("El usuario ya posee esta canción.");
        }

        if (usuario.getSaldo() < cancion.getPrecio()) {
            throw new Exception("El usuario no posee saldo suficiente.");
        }

        UsuarioDAO.agregarSaldo(idUsuario, -cancion.getPrecio());
        CompraDAO.insertar(idUsuario, idCancion, cancion.getPrecio());
    }

    public static boolean verificarCompra(int idUsuario, int idCancion) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new Exception("El usuario no existe.");
        }

        return CompraDAO.existe(idUsuario, idCancion);
    }
}