package Entidades;

public class Administador extends Cuenta {

    public Administador(String nombreUsuario, String correoElectronico, String contrasena) {
        super(correoElectronico, contrasena, nombreUsuario);
    }

    public Administador(int idCuenta, String nombreUsuario, String correoElectronico, String contrasena) {
        super(idCuenta, correoElectronico, contrasena, nombreUsuario);
    }
    @Override
    public String toString() {
        return "Administrador\n" + super.toString();
    }
}