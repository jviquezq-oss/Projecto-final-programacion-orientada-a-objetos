package DAO;

import Entidades.Cancion;
import dl.Connector;
import dl.DBAccess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstadisticasDAO {

    private static final DBAccess db;

    static {
        try {
            db = Connector.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Cancion> obtenerTop3MejorCalificadas() throws SQLException {
        String sql = "SELECT * FROM CANCION WHERE cantidad_calificaciones > 0 ORDER BY suma_calificaciones / cantidad_calificaciones DESC LIMIT 3";
        ResultSet resultado = db.ejecutarQuery(sql);
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }

    public static List<Cancion> obtenerTop3MasCompradas() throws SQLException {
        String sql = "SELECT CANCION.* FROM CANCION INNER JOIN COMPRA ON CANCION.id_cancion = COMPRA.id_cancion GROUP BY CANCION.id_cancion ORDER BY COUNT(COMPRA.id_cancion) DESC LIMIT 3";
        ResultSet resultado = db.ejecutarQuery(sql);
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }

    public static List<Cancion> obtenerTop3MasIncluidasEnListas() throws SQLException {
        String sql = "SELECT CANCION.* FROM CANCION INNER JOIN LISTA_CANCION ON CANCION.id_cancion = LISTA_CANCION.id_cancion GROUP BY CANCION.id_cancion ORDER BY COUNT(LISTA_CANCION.id_cancion) DESC LIMIT 3";
        ResultSet resultado = db.ejecutarQuery(sql);
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