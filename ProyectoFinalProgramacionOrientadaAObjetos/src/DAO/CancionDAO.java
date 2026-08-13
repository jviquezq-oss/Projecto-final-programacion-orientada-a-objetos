package DAO;

import Entidades.Cancion;
import dl.Connector;
import dl.DBAccess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CancionDAO {

    private static final DBAccess db;

    static {
        try {
            db = Connector.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Cancion insertar(Cancion cancion) throws SQLException {
        String sql = "INSERT INTO CANCION (nombre, genero, artista, compositor, fecha_lanzamiento, album, caratula, suma_calificaciones, cantidad_calificaciones, precio, duracion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idCancion = db.ejecutarInsertarYObtenerId(sql, cancion.getNombre(), cancion.getGenero(), cancion.getArtista(), cancion.getCompositor(), cancion.getFechaLanzamiento(), cancion.getAlbum(), cancion.getCaratula(), cancion.getSumaCalificaciones(), cancion.getCantidadCalificaciones(), cancion.getPrecio(), cancion.getDuracion());
        cancion.setIdCancion(idCancion);
        return cancion;
    }

    public static Cancion buscarPorId(int idCancion) throws SQLException {
        String sql = "SELECT * FROM CANCION WHERE id_cancion = ?";
        ResultSet resultado = db.ejecutarQuery(sql, idCancion);

        if (resultado.next()) {
            return convertirCancion(resultado);
        }

        return null;
    }

    public static List<Cancion> buscarPorNombre(String nombre) throws SQLException {
        String sql = "SELECT * FROM CANCION WHERE nombre LIKE ?";
        ResultSet resultado = db.ejecutarQuery(sql, "%" + nombre + "%");
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }

    public static List<Cancion> buscarPorGenero(String genero) throws SQLException {
        String sql = "SELECT * FROM CANCION WHERE genero LIKE ?";
        ResultSet resultado = db.ejecutarQuery(sql, "%" + genero + "%");
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }

    public static List<Cancion> buscarPorArtista(String artista) throws SQLException {
        String sql = "SELECT * FROM CANCION WHERE artista LIKE ?";
        ResultSet resultado = db.ejecutarQuery(sql, "%" + artista + "%");
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }
    public static List<Cancion> buscarGeneral(String criterio) throws SQLException {
        String sql = "SELECT * FROM CANCION WHERE nombre LIKE ? OR genero LIKE ? OR artista LIKE ?";
        String parametro = "%" + criterio + "%";

        ResultSet resultado = db.ejecutarQuery(sql, parametro, parametro, parametro);
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }
    public static List<Cancion> obtenerTodas() throws SQLException {
        String sql = "SELECT * FROM CANCION";
        ResultSet resultado = db.ejecutarQuery(sql);
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }

    public static void actualizar(Cancion cancion) throws SQLException {
        String sql = "UPDATE CANCION SET nombre = ?, genero = ?, artista = ?, compositor = ?, fecha_lanzamiento = ?, album = ?, caratula = ?, precio = ?, duracion = ? WHERE id_cancion = ?";
        db.ejecutarUpdate(sql, cancion.getNombre(), cancion.getGenero(), cancion.getArtista(), cancion.getCompositor(), cancion.getFechaLanzamiento(), cancion.getAlbum(), cancion.getCaratula(), cancion.getPrecio(), cancion.getDuracion(), cancion.getIdCancion());
    }

    public static void eliminar(int idCancion) throws SQLException {
        String sql = "DELETE FROM CANCION WHERE id_cancion = ?";
        db.ejecutarUpdate(sql, idCancion);
    }

    public static void agregarCalificacion(int idCancion, double calificacion) throws SQLException {
        String sql = "UPDATE CANCION SET suma_calificaciones = suma_calificaciones + ?, cantidad_calificaciones = cantidad_calificaciones + 1 WHERE id_cancion = ?";
        db.ejecutarUpdate(sql, calificacion, idCancion);
    }

    private static Cancion convertirCancion(ResultSet resultado) throws SQLException {
        int idCancion = resultado.getInt("id_cancion");
        String nombre = resultado.getString("nombre");
        String genero = resultado.getString("genero");
        String artista = resultado.getString("artista");
        String compositor = resultado.getString("compositor");
        java.sql.Date fecha = resultado.getDate("fecha_lanzamiento");
        LocalDate fechaLanzamiento = fecha != null ? fecha.toLocalDate() : null;
        String album = resultado.getString("album");
        String caratula = resultado.getString("caratula");
        double sumaCalificaciones = resultado.getDouble("suma_calificaciones");
        int cantidadCalificaciones = resultado.getInt("cantidad_calificaciones");
        double precio = resultado.getDouble("precio");
        int duracion = resultado.getInt("duracion");

        return new Cancion(idCancion, nombre, genero, artista, compositor, fechaLanzamiento, album, caratula, sumaCalificaciones, cantidadCalificaciones, precio, duracion);
    }
}