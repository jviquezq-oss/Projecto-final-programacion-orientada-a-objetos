package LogicaNegocio;

import DAO.CancionDAO;
import Entidades.Cancion;
import Excepciones.CancionNoEncontradaException;
import Excepciones.ParametroInvalidoException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorCancion {

    public static Cancion registrarCancion(String nombre, String genero, String artista, String compositor, LocalDate fechaLanzamiento, String album, String caratula, double precio, int duracion) throws Exception {
        try {
            Cancion nuevaCancion = new Cancion(nombre, genero, artista, compositor, fechaLanzamiento, album, caratula, precio, duracion);
            return CancionDAO.insertar(nuevaCancion);

        } catch (SQLException e) {
            throw new Exception("La operación registrar canción no pudo ser realizada.", e);
        }
    }

    public static Cancion buscarPorId(int idCancion) throws SQLException, CancionNoEncontradaException {
        try {
            Cancion cancion = CancionDAO.buscarPorId(idCancion);

            if (cancion == null) {
                throw new CancionNoEncontradaException("La canción no existe.");
            }

            return cancion;

        } catch (SQLException e) {
            throw new SQLException("La operación buscar canción por ID no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> buscarPorNombre(String nombre) throws Exception {
        try {
            return CancionDAO.buscarPorNombre(nombre);

        } catch (SQLException e) {
            throw new Exception("La operación buscar canción por nombre no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> buscarPorGenero(String genero) throws Exception {
        try {
            return CancionDAO.buscarPorGenero(genero);

        } catch (SQLException e) {
            throw new Exception("La operación buscar canción por género no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> buscarPorArtista(String artista) throws Exception {
        try {
            return CancionDAO.buscarPorArtista(artista);

        } catch (SQLException e) {
            throw new Exception("La operación buscar canción por artista no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> buscarGeneral(String criterio) throws Exception {
        try {
            return CancionDAO.buscarGeneral(criterio);

        } catch (SQLException e) {
            throw new Exception("La operación buscar canciones no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> obtenerTodas() throws Exception {
        try {
            return CancionDAO.obtenerTodas();

        } catch (SQLException e) {
            throw new Exception("La operación obtener canciones no pudo ser realizada.", e);
        }
    }

    public static void actualizarCancion(int idCancion, String nombre, String genero, String artista, String compositor, LocalDate fechaLanzamiento, String album, String caratula, double precio, int duracion) throws Exception {
        try {
            Cancion cancion = CancionDAO.buscarPorId(idCancion);

            if (cancion == null) {
                throw new Exception("La canción no existe.");
            }

            cancion.setNombre(nombre);
            cancion.setGenero(genero);
            cancion.setArtista(artista);
            cancion.setCompositor(compositor);
            cancion.setFechaLanzamiento(fechaLanzamiento);
            cancion.setAlbum(album);
            cancion.setCaratula(caratula);
            cancion.setPrecio(precio);
            cancion.setDuracion(duracion);

            CancionDAO.actualizar(cancion);

        } catch (SQLException e) {
            throw new Exception("La operación actualizar canción no pudo ser realizada.", e);
        }
    }

    public static void eliminarCancion(int idCancion) throws Exception {
        try {
            Cancion cancion = CancionDAO.buscarPorId(idCancion);

            if (cancion == null) {
                throw new Exception("La canción no existe.");
            }

            CancionDAO.eliminar(idCancion);

        } catch (SQLException e) {
            throw new Exception("La operación eliminar canción no pudo ser realizada.", e);
        }
    }

    public static void agregarCalificacion(int idCancion, double calificacion) throws Exception {
        try {
            Cancion cancion = CancionDAO.buscarPorId(idCancion);

            if (cancion == null) {
                throw new Exception("La canción no existe.");
            }

            cancion.agregarCalificacion(calificacion);
            CancionDAO.agregarCalificacion(idCancion, calificacion);

        } catch (SQLException e) {
            throw new Exception("La operación agregar calificación a canción no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> buscarGeneral(List<Cancion> canciones, String criterio) throws ParametroInvalidoException {
        if (canciones == null) {
            throw new ParametroInvalidoException(
                    "La lista de canciones no puede ser nula."
            );
        }

        if (criterio == null || criterio.isBlank()) {
            throw new ParametroInvalidoException(
                    "El criterio de búsqueda no puede estar vacío."
            );
        }

        List<Cancion> resultados = new ArrayList<>();
        String criterioNormalizado = criterio.trim().toLowerCase();

        for (Cancion cancion : canciones) {
            if (cancion.getNombre().toLowerCase().contains(criterioNormalizado) || cancion.getGenero().toLowerCase().contains(criterioNormalizado) || cancion.getArtista().toLowerCase().contains(criterioNormalizado)) {
                resultados.add(cancion);
            }
        }

        return resultados;
    }
}