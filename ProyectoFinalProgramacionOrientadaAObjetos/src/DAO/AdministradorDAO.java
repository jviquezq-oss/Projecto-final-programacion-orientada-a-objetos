package DAO;

import Entidades.Administador;
import dl.Connector;
import dl.DBAccess;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministradorDAO {

    private static final DBAccess db;

    static {
        try {
            db = Connector.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Administador insertar(Administador administrador) throws SQLException {
        String sql = "INSERT INTO ADMINISTRADOR (id_cuenta) VALUES (?)";
        db.ejecutarUpdate(sql, administrador.getIdCuenta());
        return administrador;
    }

    public static Administador buscarPorId(int idCuenta) throws SQLException {
        String sql = "SELECT CUENTA.id_cuenta, CUENTA.correo_electronico, CUENTA.contrasena, CUENTA.nombre_usuario FROM CUENTA INNER JOIN ADMINISTRADOR ON CUENTA.id_cuenta = ADMINISTRADOR.id_cuenta WHERE ADMINISTRADOR.id_cuenta = ?";
        ResultSet resultado = db.ejecutarQuery(sql, idCuenta);

        if (resultado.next()) {
            return convertirAdministrador(resultado);
        }

        return null;
    }
    public static Administador buscarPorCorreoElectronico(String correoElectronico) throws SQLException {
        String sql = "SELECT CUENTA.id_cuenta, CUENTA.correo_electronico, CUENTA.contrasena, CUENTA.nombre_usuario FROM CUENTA INNER JOIN ADMINISTRADOR ON CUENTA.id_cuenta = ADMINISTRADOR.id_cuenta WHERE CUENTA.correo_electronico = ?";
        ResultSet resultado = db.ejecutarQuery(sql, correoElectronico);

        if (resultado.next()) {
            return convertirAdministrador(resultado);
        }

        return null;
    }
    public static void eliminar(int idCuenta) throws SQLException {
        String sql = "DELETE FROM CUENTA WHERE id_cuenta = ?";
        db.ejecutarUpdate(sql, idCuenta);
    }

    private static Administador convertirAdministrador(ResultSet resultado) throws SQLException {
        int idCuenta = resultado.getInt("id_cuenta");
        String correoElectronico = resultado.getString("correo_electronico");
        String contrasena = resultado.getString("contrasena");
        String nombreUsuario = resultado.getString("nombre_usuario");

        return new Administador(idCuenta, correoElectronico, contrasena, nombreUsuario);
    }
}