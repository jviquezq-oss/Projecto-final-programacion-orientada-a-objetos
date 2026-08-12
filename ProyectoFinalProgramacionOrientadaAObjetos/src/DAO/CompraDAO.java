package DAO;

import Entidades.Cancion;
import dl.Connector;
import dl.DBAccess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    private static final DBAccess db;

    static {
        try {
            db = Connector.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertar(int idUsuario, int idCancion, double precio) throws SQLException {
        String sql = "INSERT INTO COMPRA (id_usuario, id_cancion, fecha_compra, precio) VALUES (?, ?, NOW(), ?)";
        db.ejecutarUpdate(sql, idUsuario, idCancion, precio);
    }

    public static boolean existe(int idUsuario, int idCancion) throws SQLException {
        String sql = "SELECT id_usuario FROM COMPRA WHERE id_usuario = ? AND id_cancion = ?";
        ResultSet resultado = db.ejecutarQuery(sql, idUsuario, idCancion);
        return resultado.next();
    }

    public static List<Cancion> obtenerCancionesCompradas(int idUsuario) throws SQLException {
        String sql = "SELECT CANCION.* FROM CANCION INNER JOIN COMPRA ON CANCION.id_cancion = COMPRA.id_cancion WHERE COMPRA.id_usuario = ? ORDER BY COMPRA.fecha_compra";
        ResultSet resultado = db.ejecutarQuery(sql, idUsuario);
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }

    public static void eliminar(int idUsuario, int idCancion) throws SQLException {
        String sql = "DELETE FROM COMPRA WHERE id_usuario = ? AND id_cancion = ?";
        db.ejecutarUpdate(sql, idUsuario, idCancion);
    }
    public static Cancion buscarPorId(int idUsuario, int idCancion) throws SQLException {
        String sql = "SELECT CANCION.* FROM CANCION INNER JOIN COMPRA ON CANCION.id_cancion = COMPRA.id_cancion WHERE COMPRA.id_usuario = ? AND CANCION.id_cancion = ?";
        ResultSet resultado = db.ejecutarQuery(sql, idUsuario, idCancion);

        if (resultado.next()) {
            return convertirCancion(resultado);
        }

        return null;
    }

    public static List<Cancion> buscarPorNombre(int idUsuario, String nombre) throws SQLException {
        String sql = "SELECT CANCION.* FROM CANCION INNER JOIN COMPRA ON CANCION.id_cancion = COMPRA.id_cancion WHERE COMPRA.id_usuario = ? AND CANCION.nombre LIKE ?";
        ResultSet resultado = db.ejecutarQuery(sql, idUsuario, "%" + nombre + "%");
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }

    public static List<Cancion> buscarPorGenero(int idUsuario, String genero) throws SQLException {
        String sql = "SELECT CANCION.* FROM CANCION INNER JOIN COMPRA ON CANCION.id_cancion = COMPRA.id_cancion WHERE COMPRA.id_usuario = ? AND CANCION.genero LIKE ?";
        ResultSet resultado = db.ejecutarQuery(sql, idUsuario, "%" + genero + "%");
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }

    public static List<Cancion> buscarPorArtista(int idUsuario, String artista) throws SQLException {
        String sql = "SELECT CANCION.* FROM CANCION INNER JOIN COMPRA ON CANCION.id_cancion = COMPRA.id_cancion WHERE COMPRA.id_usuario = ? AND CANCION.artista LIKE ?";
        ResultSet resultado = db.ejecutarQuery(sql, idUsuario, "%" + artista + "%");
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
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