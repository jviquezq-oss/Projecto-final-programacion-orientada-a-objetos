package LogicaNegocio;

import DAO.CompraDAO;
import DAO.UsuarioDAO;
import Entidades.Cancion;
import Entidades.Usuario;
import Excepciones.CancionNoEncontradaException;
import Excepciones.UsuarioNoExisteException;

import java.sql.SQLException;
import java.util.List;

public class GestorCatalogoUsuario {

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

    public static Cancion buscarPorId(int idUsuario, int idCancion) throws Exception {
        try {
            Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                throw new UsuarioNoExisteException("El usuario no existe.");
            }

            Cancion cancion = CompraDAO.buscarPorId(idUsuario, idCancion);

            if (cancion == null) {
                throw new CancionNoEncontradaException("La canción no pertenece al catálogo del usuario.");
            }

            return cancion;

        } catch (SQLException e) {
            throw new Exception("La operación buscar canción del catálogo del usuario no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> buscarPorNombre(int idUsuario, String nombre) throws Exception {
        try {
            Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                throw new UsuarioNoExisteException("El usuario no existe.");
            }

            return CompraDAO.buscarPorNombre(idUsuario, nombre);

        } catch (SQLException e) {
            throw new Exception("La operación buscar canción por nombre en catálogo del usuario no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> buscarPorGenero(int idUsuario, String genero) throws Exception {
        try {
            Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                throw new UsuarioNoExisteException("El usuario no existe.");
            }

            return CompraDAO.buscarPorGenero(idUsuario, genero);

        } catch (SQLException e) {
            throw new Exception("La operación buscar canción por género en catálogo del usuario no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> buscarPorArtista(int idUsuario, String artista) throws Exception {
        try {
            Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                throw new UsuarioNoExisteException("El usuario no existe.");
            }

            return CompraDAO.buscarPorArtista(idUsuario, artista);

        } catch (SQLException e) {
            throw new Exception("La operación buscar canción por artista en catálogo del usuario no pudo ser realizada.", e);
        }
    }
}