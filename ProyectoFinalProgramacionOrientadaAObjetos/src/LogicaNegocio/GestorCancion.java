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
        Cancion nuevaCancion = new Cancion(nombre, genero, artista, compositor, fechaLanzamiento, album, caratula, precio, duracion);
        return CancionDAO.insertar(nuevaCancion);
    }

    public static Cancion buscarPorId(int idCancion) throws SQLException, CancionNoEncontradaException {
        Cancion cancion = CancionDAO.buscarPorId(idCancion);

        if (cancion == null) {
            throw new CancionNoEncontradaException("La canción no existe.");
        }

        return cancion;
    }

    public static List<Cancion> buscarPorNombre(String nombre) throws Exception {
        return CancionDAO.buscarPorNombre(nombre);
    }

    public static List<Cancion> buscarPorGenero(String genero) throws Exception {
        return CancionDAO.buscarPorGenero(genero);
    }

    public static List<Cancion> buscarPorArtista(String artista) throws Exception {
        return CancionDAO.buscarPorArtista(artista);
    }

    public static List<Cancion> buscarGeneral(String criterio) throws Exception {
        return CancionDAO.buscarGeneral(criterio);
    }

    public static List<Cancion> obtenerTodas() throws Exception {
        return CancionDAO.obtenerTodas();
    }

    public static void actualizarCancion(int idCancion, String nombre, String genero, String artista, String compositor, LocalDate fechaLanzamiento, String album, String caratula, double precio, int duracion) throws Exception {
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
    }

    public static void eliminarCancion(int idCancion) throws Exception {
        Cancion cancion = CancionDAO.buscarPorId(idCancion);

        if (cancion == null) {
            throw new Exception("La canción no existe.");
        }

        CancionDAO.eliminar(idCancion);
    }

    public static void agregarCalificacion(int idCancion, double calificacion) throws Exception {
        Cancion cancion = CancionDAO.buscarPorId(idCancion);

        if (cancion == null) {
            throw new Exception("La canción no existe.");
        }

        cancion.agregarCalificacion(calificacion);
        CancionDAO.agregarCalificacion(idCancion, calificacion);
    }
    public static List<Cancion> buscarGeneral(
            List<Cancion> canciones,
            String criterio) throws ParametroInvalidoException {

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