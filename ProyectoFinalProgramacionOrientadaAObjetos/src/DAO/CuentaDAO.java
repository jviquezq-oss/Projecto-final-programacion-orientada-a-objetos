package DAO;

import Entidades.Cuenta;
import dl.Connector;
import dl.DBAccess;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CuentaDAO {

    private static final DBAccess db;

    static {
        try {
            db = Connector.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Cuenta insertar(Cuenta cuenta) throws SQLException {

        String sql = "INSERT INTO CUENTA (correo_electronico, contrasena, nombre_usuario) VALUES (?, ?, ?)";
        int idCuenta = db.ejecutarInsertarYObtenerId(sql, cuenta.getCorreoElectronico(), cuenta.getContrasena(), cuenta.getNombreUsuario());
        cuenta.setIdCuenta(idCuenta);
        return cuenta;
    }

    public static Cuenta buscarPorId(int idCuenta) throws SQLException {

        String sql = "SELECT * FROM CUENTA WHERE id_cuenta = ?";
        ResultSet resultado = db.ejecutarQuery(sql, idCuenta);

        if (resultado.next()) {
            return convertirCuenta(resultado);
        }

        return null;
    }

    public static Cuenta buscarPorCorreoElectronico(String correoElectronico) throws SQLException {

        String sql = "SELECT * FROM CUENTA WHERE correo_electronico = ?";
        ResultSet resultado = db.ejecutarQuery(sql, correoElectronico);

        if (resultado.next()) {
            return convertirCuenta(resultado);
        }

        return null;
    }

    public static Cuenta buscarPorNombreUsuario(String nombreUsuario) throws SQLException {

        String sql = "SELECT * FROM CUENTA WHERE nombre_usuario = ?";
        ResultSet resultado = db.ejecutarQuery(sql, nombreUsuario);

        if (resultado.next()) {
            return convertirCuenta(resultado);
        }

        return null;
    }

    public static void actualizar(Cuenta cuenta) throws SQLException {

        String sql = "UPDATE CUENTA SET correo_electronico = ?, contrasena = ?, nombre_usuario = ? WHERE id_cuenta = ?";
        db.ejecutarUpdate(sql, cuenta.getCorreoElectronico(), cuenta.getContrasena(), cuenta.getNombreUsuario(), cuenta.getIdCuenta());
    }

    public static void eliminar(int idCuenta) throws SQLException {

        String sql = "DELETE FROM CUENTA WHERE id_cuenta = ?";
        db.ejecutarUpdate(sql, idCuenta);
    }

    public static void eliminarPorCorreoElectronico(String correoElectronico) throws SQLException {

        String sql = "DELETE FROM CUENTA WHERE correo_electronico = ?";
        db.ejecutarUpdate(sql, correoElectronico);
    }

    public static void eliminarPorNombreUsuario(String nombreUsuario) throws SQLException {

        String sql = "DELETE FROM CUENTA WHERE nombre_usuario = ?";
        db.ejecutarUpdate(sql, nombreUsuario);
    }

    public static String obtenerTipoCuenta(int idCuenta) throws SQLException {
        String sql = "SELECT CASE WHEN a.id_cuenta IS NOT NULL THEN 'Administrador' WHEN u.id_cuenta IS NOT NULL THEN 'Usuario' ELSE 'Sin tipo' END AS tipo_cuenta FROM CUENTA c LEFT JOIN ADMINISTRADOR a ON c.id_cuenta = a.id_cuenta LEFT JOIN USUARIO u ON c.id_cuenta = u.id_cuenta WHERE c.id_cuenta = ?";
        ResultSet resultado = db.ejecutarQuery(sql, idCuenta);

        if (resultado.next()) {
            return resultado.getString("tipo_cuenta");
        }

        return null;
    }
    public static void actualizarContrasena(int idCuenta, String contrasena) throws SQLException {
        String sql = "UPDATE CUENTA SET contrasena = ? WHERE id_cuenta = ?";

        db.ejecutarUpdate(sql, contrasena, idCuenta);
    }

    private static Cuenta convertirCuenta(ResultSet resultado) throws SQLException {

        int idCuenta = resultado.getInt("id_cuenta");
        String correoElectronico = resultado.getString("correo_electronico");
        String contrasena = resultado.getString("contrasena");
        String nombreUsuario = resultado.getString("nombre_usuario");

        return new Cuenta(idCuenta, correoElectronico, contrasena, nombreUsuario);
    }
}