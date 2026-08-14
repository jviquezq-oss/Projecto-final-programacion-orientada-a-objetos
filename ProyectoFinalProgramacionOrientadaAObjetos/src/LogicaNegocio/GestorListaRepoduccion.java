package LogicaNegocio;

import DAO.ListaRepoduccionDAO;
import Entidades.Cancion;
import Entidades.ListaRepoduccion;
import Entidades.Usuario;
import Excepciones.CancionNoEncontradaException;
import Excepciones.CancionYaExiste;
import Excepciones.ListaNoEncontrada;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class GestorListaRepoduccion {

    public static ListaRepoduccion registrarLista(Usuario usuario, String nombre, LocalDate fechaCreacion) throws Exception {
        try {
            ListaRepoduccion nuevaLista = new ListaRepoduccion(0, usuario, nombre, fechaCreacion, 0.0);
            return ListaRepoduccionDAO.insertar(nuevaLista);

        } catch (SQLException e) {
            throw new Exception("La operación registrar lista de reproducción no pudo ser realizada.", e);
        }
    }

    public static ListaRepoduccion buscarPorId(int idLista, Usuario usuario) throws Exception {
        try {
            ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

            if (lista == null) {
                throw new ListaNoEncontrada("La lista de reproducción no existe.");
            }

            return lista;

        } catch (SQLException e) {
            throw new Exception("La operación buscar lista de reproducción por ID no pudo ser realizada.", e);
        }
    }

    public static List<ListaRepoduccion> buscarPorNombre(String nombre, Usuario usuario) throws Exception {
        try {
            return ListaRepoduccionDAO.buscarPorNombre(nombre, usuario);

        } catch (SQLException e) {
            throw new Exception("La operación buscar lista de reproducción por nombre no pudo ser realizada.", e);
        }
    }

    public static List<ListaRepoduccion> obtenerPorUsuario(Usuario usuario) throws Exception {
        try {
            return ListaRepoduccionDAO.obtenerPorUsuario(usuario);

        } catch (SQLException e) {
            throw new Exception("La operación obtener listas de reproducción del usuario no pudo ser realizada.", e);
        }
    }

    public static void actualizarLista(int idLista, Usuario usuario, String nombre) throws Exception {
        try {
            ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

            if (lista == null) {
                throw new ListaNoEncontrada("La lista de reproducción no existe.");
            }

            lista.setNombre(nombre);
            ListaRepoduccionDAO.actualizar(lista);

        } catch (SQLException e) {
            throw new Exception("La operación actualizar lista de reproducción no pudo ser realizada.", e);
        }
    }

    public static void eliminarLista(int idLista, Usuario usuario) throws Exception {
        try {
            ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

            if (lista == null) {
                throw new ListaNoEncontrada("La lista de reproducción no existe.");
            }

            ListaRepoduccionDAO.eliminar(idLista);

        } catch (SQLException e) {
            throw new Exception("La operación eliminar lista de reproducción no pudo ser realizada.", e);
        }
    }

    public static void agregarCancion(int idLista, int idCancion, Usuario usuario) throws Exception {
        try {
            ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

            if (lista == null) {
                throw new ListaNoEncontrada("La lista de reproducción no existe.");
            }

            if (ListaRepoduccionDAO.contieneCancion(idLista, idCancion)) {
                throw new CancionYaExiste("La canción ya pertenece a la lista de reproducción.");
            }

            ListaRepoduccionDAO.agregarCancion(idLista, idCancion);

        } catch (SQLException e) {
            throw new Exception("La operación agregar canción a lista de reproducción no pudo ser realizada.", e);
        }
    }

    public static void eliminarCancion(int idLista, int idCancion, Usuario usuario) throws Exception {
        try {
            ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

            if (lista == null) {
                throw new ListaNoEncontrada("La lista de reproducción no existe.");
            }

            if (!ListaRepoduccionDAO.contieneCancion(idLista, idCancion)) {
                throw new CancionNoEncontradaException("La canción no pertenece a la lista de reproducción.");
            }

            ListaRepoduccionDAO.eliminarCancion(idLista, idCancion);

        } catch (SQLException e) {
            throw new Exception("La operación eliminar canción de lista de reproducción no pudo ser realizada.", e);
        }
    }

    public static List<ListaRepoduccion> obtenerTodas() throws Exception {
        try {
            return ListaRepoduccionDAO.obtenerTodas();

        } catch (SQLException e) {
            throw new Exception("La operación obtener todas las listas de reproducción no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> obtenerCanciones(int idLista, Usuario usuario) throws Exception {
        try {
            ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista, usuario);

            if (lista == null) {
                throw new ListaNoEncontrada("La lista de reproducción no existe.");
            }

            return ListaRepoduccionDAO.obtenerCanciones(idLista);

        } catch (SQLException e) {
            throw new Exception("La operación obtener canciones de lista de reproducción no pudo ser realizada.", e);
        }
    }

    public static void recalcularCalificacion(int idLista, Usuario usuario) throws Exception {
        try {
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

        } catch (SQLException e) {
            throw new Exception("La operación recalcular calificación de lista de reproducción no pudo ser realizada.", e);
        }
    }

    public static List<ListaRepoduccion> buscarPorNombre(String nombre) throws Exception {
        try {
            return ListaRepoduccionDAO.buscarPorNombre(nombre);

        } catch (SQLException e) {
            throw new Exception("La operación buscar lista de reproducción por nombre no pudo ser realizada.", e);
        }
    }
}