package LogicaNegocio;

import DAO.ListaRepoduccionDAO;
import Entidades.Cancion;
import Entidades.ListaRepoduccion;
import Entidades.Usuario;
import Excepciones.CancionNoEncontradaException;
import Excepciones.CancionYaExiste;
import Excepciones.ListaNoEncontrada;

import java.time.LocalDate;
import java.util.List;

public class GestorListaRepoduccion {

    public static ListaRepoduccion registrarLista(Usuario usuario, String nombre, LocalDate fechaCreacion) throws Exception {
        ListaRepoduccion nuevaLista = new ListaRepoduccion(0, usuario, nombre, fechaCreacion, 0.0);
        return ListaRepoduccionDAO.insertar(nuevaLista);
    }

    public static ListaRepoduccion buscarPorId(int idLista, Usuario usuario) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

        if (lista == null) {
            throw new ListaNoEncontrada("La lista de reproducción no existe.");
        }

        return lista;
    }

    public static List<ListaRepoduccion> buscarPorNombre(String nombre, Usuario usuario) throws Exception {
        return ListaRepoduccionDAO.buscarPorNombre(nombre, usuario);
    }

    public static List<ListaRepoduccion> obtenerPorUsuario(Usuario usuario) throws Exception {
        return ListaRepoduccionDAO.obtenerPorUsuario(usuario);
    }

    public static void actualizarLista(int idLista, Usuario usuario, String nombre) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

        if (lista == null) {
            throw new ListaNoEncontrada("La lista de reproducción no existe.");
        }

        lista.setNombre(nombre);
        ListaRepoduccionDAO.actualizar(lista);
    }

    public static void eliminarLista(int idLista, Usuario usuario) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

        if (lista == null) {
            throw new ListaNoEncontrada("La lista de reproducción no existe.");
        }

        ListaRepoduccionDAO.eliminar(idLista);
    }

    public static void agregarCancion(int idLista, int idCancion, Usuario usuario) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

        if (lista == null) {
            throw new ListaNoEncontrada("La lista de reproducción no existe.");
        }

        if (ListaRepoduccionDAO.contieneCancion(idLista, idCancion)) {
            throw new CancionYaExiste("La canción ya pertenece a la lista de reproducción.");
        }

        ListaRepoduccionDAO.agregarCancion(idLista, idCancion);
    }

    public static void eliminarCancion(int idLista, int idCancion, Usuario usuario) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

        if (lista == null) {
            throw new ListaNoEncontrada("La lista de reproducción no existe.");
        }

        if (!ListaRepoduccionDAO.contieneCancion(idLista, idCancion)) {
            throw new CancionNoEncontradaException("La canción no pertenece a la lista de reproducción.");
        }

        ListaRepoduccionDAO.eliminarCancion(idLista, idCancion);
    }

    public static List<ListaRepoduccion> obtenerTodas() throws Exception {
        return ListaRepoduccionDAO.obtenerTodas();
    }

    public static List<Cancion> obtenerCanciones(int idLista, Usuario usuario) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

        if (lista == null) {
            throw new ListaNoEncontrada("La lista de reproducción no existe.");
        }

        return ListaRepoduccionDAO.obtenerCanciones(idLista);
    }

    public static void recalcularCalificacion(int idLista, Usuario usuario) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

        if (lista == null) {
            throw new ListaNoEncontrada("La lista de reproducción no existe.");
        }

        List<Cancion> canciones = obtenerCanciones(idLista, usuario);
        double calificacion = 0.0;

        if (!canciones.isEmpty()) {
            double suma = 0.0;

            for (Cancion cancion : canciones) {
                suma += cancion.getCalificacion();
            }

            calificacion = Math.round((suma / canciones.size()) * 10.0) / 10.0;
        }

        ListaRepoduccionDAO.actualizarCalificacion(idLista, calificacion);
    }
    public static List<ListaRepoduccion> buscarPorNombre(String nombre) throws Exception {
        return ListaRepoduccionDAO.buscarPorNombre(nombre);
    }
}