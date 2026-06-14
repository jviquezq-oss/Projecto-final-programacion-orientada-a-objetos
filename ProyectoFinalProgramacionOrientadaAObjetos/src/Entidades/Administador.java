package Entidades;

public class Administador {
    private Cuenta cuenta;

    public Administador(String nombreUsuario, String correoElectronico,String contrasena) {
        this.cuenta = new Cuenta(correoElectronico,contrasena,nombreUsuario);
    }
    public Cuenta getCuenta(){
        return this.cuenta;
    }
}
