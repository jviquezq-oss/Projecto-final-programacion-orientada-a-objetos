package LogicaNegocio;

import DAO.CompraDAO;
import DAO.UsuarioDAO;
import Entidades.Cancion;
import Entidades.Usuario;
import Excepciones.CancionNoEncontradaException;
import Excepciones.UsuarioNoExisteException;

import java.util.List;

public class GestorCatalogoUsuario {

    public static List<Cancion> obtenerCancionesCompradas(int idUsuario) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new Exception("El usuario no existe.");
        }

        return CompraDAO.obtenerCancionesCompradas(idUsuario);
    }

    public static Cancion buscarPorId(int idUsuario, int idCancion) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new UsuarioNoExisteException("El usuario no existe.");
        }

        Cancion cancion = CompraDAO.buscarPorId(idUsuario, idCancion);

        if (cancion == null) {
            throw new CancionNoEncontradaException("La canción no pertenece al catálogo del usuario.");
        }

        return cancion;
    }

    public static List<Cancion> buscarPorNombre(int idUsuario, String nombre) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new UsuarioNoExisteException("El usuario no existe.");
        }

        return CompraDAO.buscarPorNombre(idUsuario, nombre);
    }

    public static List<Cancion> buscarPorGenero(int idUsuario, String genero) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new UsuarioNoExisteException("El usuario no existe.");
        }

        return CompraDAO.buscarPorGenero(idUsuario, genero);
    }

    public static List<Cancion> buscarPorArtista(int idUsuario, String artista) throws Exception {
        Usuario usuario = UsuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new UsuarioNoExisteException("El usuario no existe.");
        }

        return CompraDAO.buscarPorArtista(idUsuario, artista);
    }
}