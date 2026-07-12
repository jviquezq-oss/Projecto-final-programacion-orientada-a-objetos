package Entidades;

public class Administador extends Cuenta {
    public Administador(String nombreUsuario, String correoElectronico, String contrasena) {
        super(correoElectronico, contrasena, nombreUsuario);
    }
}