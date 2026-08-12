package Entidades;

import Excepciones.DatosInvalidosException;

public class Cuenta {

    private int idCuenta;
    private String correoElectronico;
    private String contrasena;
    private String nombreUsuario;

    public Cuenta(String correoElectronico, String contrasena, String nombreUsuario) {
        this.idCuenta = 0;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
    }

    public Cuenta(int idCuenta, String correoElectronico, String contrasena, String nombreUsuario) {
        this.idCuenta = idCuenta;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
    }
    public Cuenta(int idCuenta, String correoElectronico, String nombreDeUsuario) {
        this.idCuenta = idCuenta;
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreDeUsuario;
    }

    public int getIdCuenta() {
        return idCuenta;
    }
    public void setIdCuenta(int idCuenta){
        this.idCuenta = idCuenta;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }
    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void cambiarContrasena(String contrasenaActual, String nuevaContrasena, String confirmacionContrasena) throws DatosInvalidosException {
        if (contrasenaActual == null || !contrasena.equals(contrasenaActual)) {
            throw new DatosInvalidosException("La contraseña actual es incorrecta.");
        }
        validarContrasena(nuevaContrasena);
        if (!nuevaContrasena.equals(confirmacionContrasena)) {
            throw new DatosInvalidosException("La nueva contraseña y su confirmación no coinciden.");
        }
        if (nuevaContrasena.equals(contrasenaActual)) {
            throw new DatosInvalidosException("La nueva contraseña debe ser distinta a la actual.");
        }

        this.contrasena = nuevaContrasena;
    }

    public static void validarContrasena(String contrasena) throws DatosInvalidosException {
        if (contrasena == null || !contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,12}$")) {
            throw new DatosInvalidosException("La contraseña debe tener entre 8 y 12 caracteres, " + "una mayúscula, una minúscula, un número y un carácter especial.");
        }
    }

    @Override
    public String toString() {
        return "Nombre de usuario: " + nombreUsuario +
                "\nCorreo electrónico: " + correoElectronico;
    }
}