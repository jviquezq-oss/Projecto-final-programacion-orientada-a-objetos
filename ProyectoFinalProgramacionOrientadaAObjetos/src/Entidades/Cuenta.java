package Entidades;

public abstract class Cuenta {
    protected String correoElectronico;
    protected String contrasena;
    private String nombreUsuario;

    protected Cuenta(String correoElectronico, String contrasena, String nombreUsuario) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUsuario() {
        return this.nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public String toString() {
        return "Nombre de usuatio: " + this.nombreUsuario + "\nCorreo electronico: " + this.correoElectronico;
    }
}

