package Entidades;

public class Cuenta {
    private String correoElectronico;
    private String contrasena;
    private String nombreUsuario;

    public Cuenta(String correoElectronico, String contrasena, String nombreUsuario) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
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

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String toString(){
        return "Nombre de usuatio: "+this.nombreUsuario+"\n"+
                "Correo electronico: "+this.correoElectronico;
    }
}
