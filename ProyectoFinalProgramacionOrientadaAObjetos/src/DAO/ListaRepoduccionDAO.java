package DAO;

import Entidades.Cancion;
import Entidades.ListaRepoduccion;
import Entidades.Usuario;
import dl.Connector;
import dl.DBAccess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListaRepoduccionDAO {

    private static final DBAccess db;

    static {
        try {
            db = Connector.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ListaRepoduccion insertar(ListaRepoduccion lista) throws SQLException {
        String sql = "INSERT INTO LISTA_REPRODUCCION (id_usuario, nombre, fecha_creacion) VALUES (?, ?, ?)";
        int idLista = db.ejecutarInsertarYObtenerId(sql, lista.getPropietario().getIdCuenta(), lista.getNombre(), lista.getFechaCreacion());
        lista.setIdLista(idLista);
        return lista;
    }

    public static ListaRepoduccion buscarPorId(int idLista) throws SQLException {
        String sql = "SELECT id_lista, id_usuario, nombre, fecha_creacion, calificacion FROM LISTA_REPRODUCCION WHERE id_lista = ?";
        ResultSet resultado = db.ejecutarQuery(sql, idLista);
        return null;
    }

    public static List<ListaRepoduccion> obtenerPorUsuario(Usuario usuario) throws SQLException {
        String sql = "SELECT id_lista, id_usuario, nombre, fecha_creacion, calificacion FROM LISTA_REPRODUCCION WHERE id_usuario = ?";
        ResultSet resultado = db.ejecutarQuery(sql, usuario.getIdCuenta());
        List<ListaRepoduccion> listas = new ArrayList<>();

        while (resultado.next()) {
            listas.add(convertirLista(resultado, usuario));
        }

        return listas;
    }

    public static void actualizar(ListaRepoduccion lista) throws SQLException {
        String sql = "UPDATE LISTA_REPRODUCCION SET nombre = ? WHERE id_lista = ?";
        db.ejecutarUpdate(sql, lista.getNombre(), lista.getIdLista());
    }

    public static void eliminar(int idLista) throws SQLException {
        String sql = "DELETE FROM LISTA_REPRODUCCION WHERE id_lista = ?";
        db.ejecutarUpdate(sql, idLista);
    }

    public static void agregarCancion(int idLista, int idCancion) throws SQLException {
        String sql = "INSERT INTO LISTA_CANCION (id_lista, id_cancion) VALUES (?, ?)";
        db.ejecutarUpdate(sql, idLista, idCancion);
    }

    public static void eliminarCancion(int idLista, int idCancion) throws SQLException {
        String sql = "DELETE FROM LISTA_CANCION WHERE id_lista = ? AND id_cancion = ?";
        db.ejecutarUpdate(sql, idLista, idCancion);
    }

    public static boolean contieneCancion(int idLista, int idCancion) throws SQLException {
        String sql = "SELECT id_lista FROM LISTA_CANCION WHERE id_lista = ? AND id_cancion = ?";
        ResultSet resultado = db.ejecutarQuery(sql, idLista, idCancion);
        return resultado.next();
    }

    public static List<Cancion> obtenerCanciones(int idLista) throws SQLException {
        String sql = "SELECT CANCION.* FROM CANCION INNER JOIN LISTA_CANCION ON CANCION.id_cancion = LISTA_CANCION.id_cancion WHERE LISTA_CANCION.id_lista = ?";
        ResultSet resultado = db.ejecutarQuery(sql, idLista);
        List<Cancion> canciones = new ArrayList<>();

        while (resultado.next()) {
            canciones.add(convertirCancion(resultado));
        }

        return canciones;
    }
    public static List<ListaRepoduccion> buscarPorNombre(String nombre, Usuario usuario) throws SQLException {
        String sql = "SELECT id_lista, id_usuario, nombre, fecha_creacion FROM LISTA_REPRODUCCION WHERE id_usuario = ? AND nombre LIKE ?";
        ResultSet resultado = db.ejecutarQuery(sql, usuario.getIdCuenta(), "%" + nombre + "%");
        List<ListaRepoduccion> listas = new ArrayList<>();

        while (resultado.next()) {
            listas.add(convertirLista(resultado, usuario));
        }

        return listas;
    }
    public static List<ListaRepoduccion> obtenerTodas() throws SQLException {
        String sql = "SELECT L.id_lista, L.id_usuario, L.nombre, L.fecha_creacion, U.nombre_completo, U.fecha_nacimiento, U.nacionalidad, U.cedula, U.avatar, U.saldo, C.nombre_usuario, C.correo_electronico FROM LISTA_REPRODUCCION L INNER JOIN USUARIO U ON L.id_usuario = U.id_cuenta INNER JOIN CUENTA C ON U.id_cuenta = C.id_cuenta";
        ResultSet resultado = db.ejecutarQuery(sql);
        List<ListaRepoduccion> listas = new ArrayList<>();

        while (resultado.next()) {
            listas.add(convertirListaConUsuario(resultado));
        }

        return listas;
    }
    public static void actualizarCalificacion(int idLista, double calificacion) throws SQLException {
        String sql = "UPDATE LISTA_REPRODUCCION SET calificacion = ? WHERE id_lista = ?";
        db.ejecutarUpdate(sql, calificacion, idLista);
    }
    private static ListaRepoduccion convertirLista(ResultSet resultado, Usuario usuario) throws SQLException {
        int idLista = resultado.getInt("id_lista");
        String nombre = resultado.getString("nombre");
        java.sql.Date fecha = resultado.getDate("fecha_creacion");
        LocalDate fechaCreacion = fecha != null ? fecha.toLocalDate() : null;
        double calificaion = resultado.getDouble("calificacion");
        return new ListaRepoduccion(idLista, usuario, nombre, fechaCreacion,calificaion);
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
    private static ListaRepoduccion convertirListaConUsuario(ResultSet resultado) throws SQLException {
        int idCuenta = resultado.getInt("id_usuario");
        String nombreCompleto = resultado.getString("nombre_completo");
        java.sql.Date fecha = resultado.getDate("fecha_nacimiento");
        LocalDate fechaNacimiento = fecha != null ? fecha.toLocalDate() : null;
        String nacionalidad = resultado.getString("nacionalidad");
        String cedula = resultado.getString("cedula");
        String avatar = resultado.getString("avatar");
        double saldo = resultado.getDouble("saldo");
        String nombreUsuario = resultado.getString("nombre_usuario");
        String correoElectronico = resultado.getString("correo_electronico");

        Usuario usuario = new Usuario(idCuenta, nombreCompleto, fechaNacimiento, nacionalidad, cedula, avatar, nombreUsuario, correoElectronico, saldo);

        int idLista = resultado.getInt("id_lista");
        String nombre = resultado.getString("nombre");
        java.sql.Date fechaCreacionSql = resultado.getDate("fecha_creacion");
        LocalDate fechaCreacion = fechaCreacionSql != null ? fechaCreacionSql.toLocalDate() : null;
        double calificaion = resultado.getDouble("calificacion");

        return new ListaRepoduccion(idLista, usuario, nombre, fechaCreacion,calificaion);
    }
}