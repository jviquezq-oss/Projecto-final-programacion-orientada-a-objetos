package DAO;

import Entidades.Usuario;
import dl.Connector;
import dl.DBAccess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UsuarioDAO {

    private static final DBAccess db;

    static {
        try {
            db = Connector.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Usuario insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO USUARIO (id_cuenta, nombre_completo, fecha_nacimiento, nacionalidad, cedula, avatar, saldo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        db.ejecutarUpdate(sql, usuario.getIdCuenta(), usuario.getNombrCompleto(), usuario.getFechaDeNacimiento(), usuario.getNacionalidad(), usuario.getCedula(), usuario.getAvatar(), usuario.getSaldo());
        return usuario;
    }

    public static Usuario buscarPorId(int idCuenta) throws SQLException {
        String sql = "SELECT CUENTA.id_cuenta, CUENTA.correo_electronico, CUENTA.contrasena, CUENTA.nombre_usuario, USUARIO.nombre_completo, USUARIO.fecha_nacimiento, USUARIO.nacionalidad, USUARIO.cedula, USUARIO.avatar, USUARIO.saldo FROM CUENTA INNER JOIN USUARIO ON CUENTA.id_cuenta = USUARIO.id_cuenta WHERE USUARIO.id_cuenta = ?";
        ResultSet resultado = db.ejecutarQuery(sql, idCuenta);
        if (resultado.next()) {
            return convertirUsuario(resultado);
        }
        return null;
    }

    public static void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE USUARIO SET nombre_completo = ?, fecha_nacimiento = ?, nacionalidad = ?, cedula = ?, avatar = ?, saldo = ? WHERE id_cuenta = ?";
        db.ejecutarUpdate(sql, usuario.getNombrCompleto(), usuario.getFechaDeNacimiento(), usuario.getNacionalidad(), usuario.getCedula(), usuario.getAvatar(), usuario.getSaldo(), usuario.getIdCuenta());
    }

    public static void eliminar(int idCuenta) throws SQLException {
        String sql = "DELETE FROM CUENTA WHERE id_cuenta = ?";
        db.ejecutarUpdate(sql, idCuenta);
    }

    public static void agregarSaldo(int idCuenta, double cantidad) throws SQLException {
        String sql = "UPDATE USUARIO SET saldo = saldo + ? WHERE id_cuenta = ?";
        db.ejecutarUpdate(sql, cantidad, idCuenta);
    }

    private static Usuario convertirUsuario(ResultSet resultado) throws SQLException {
        int idCuenta = resultado.getInt("id_cuenta");
        String correoElectronico = resultado.getString("correo_electronico");
        String contrasena = resultado.getString("contrasena");
        String nombreDeUsuario = resultado.getString("nombre_usuario");
        String nombrCompleto = resultado.getString("nombre_completo");
        java.sql.Date fecha = resultado.getDate("fecha_nacimiento");
        LocalDate fechaDeNacimiento = fecha != null ? fecha.toLocalDate() : null;
        String nacionalidad = resultado.getString("nacionalidad");
        String cedula = resultado.getString("cedula");
        String avatar = resultado.getString("avatar");
        double saldo = resultado.getDouble("saldo");
        return new Usuario(idCuenta, nombrCompleto, fechaDeNacimiento, nacionalidad, cedula, avatar, nombreDeUsuario, correoElectronico, contrasena, saldo);
    }
}