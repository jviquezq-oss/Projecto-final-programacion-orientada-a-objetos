package LogicaNegocio;

import DAO.CancionDAO;
import DAO.CompraDAO;
import DAO.UsuarioDAO;
import Entidades.Cancion;
import Entidades.Usuario;

import java.sql.SQLException;

public class GestorCompra {

    public static void comprarCancion(int idUsuario, int idCancion) throws Exception {
        try {
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

        } catch (SQLException e) {
            throw new Exception("La operación comprar canción no pudo ser realizada.", e);
        }
    }

    public static boolean verificarCompra(int idUsuario, int idCancion) throws Exception {
        try {
            Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                throw new Exception("El usuario no existe.");
            }

            return CompraDAO.existe(idUsuario, idCancion);

        } catch (SQLException e) {
            throw new Exception("La operación verificar compra no pudo ser realizada.", e);
        }
    }
}