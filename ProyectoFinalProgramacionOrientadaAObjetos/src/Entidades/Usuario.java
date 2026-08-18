package Entidades;

import Estructuras.ColaDeCanciones;
import Excepciones.DatosInvalidosException;

import java.time.LocalDate;
import java.time.Period;

public class Usuario extends Cuenta {

    public static final double BONO_BIENVENIDA = 4.99;
    public static final String AVATAR_PREDETERMINADO = "avatar_predeterminado.png";
    private String nombrCompleto;
    private LocalDate fechaDeNacimiento;
    private String nacionalidad;
    private String cedula;
    private String avatar;
    private double saldo;
    private final ColaDeCanciones colaReproduccion;

    public Usuario(String nombrCompleto, LocalDate fechaDeNacimiento, String nacionalidad, String cedula, String avatar, String nombreDeUsuario, String correoElectronico, String contrasena) {
        super(correoElectronico, contrasena, nombreDeUsuario);
        this.nombrCompleto = nombrCompleto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;
        this.avatar = (avatar == null || avatar.isBlank())
                ? AVATAR_PREDETERMINADO
                : avatar;
        this.saldo = BONO_BIENVENIDA;
        this.colaReproduccion = new ColaDeCanciones();
    }

    public Usuario(int idCuenta, String nombrCompleto, LocalDate fechaDeNacimiento, String nacionalidad, String cedula, String avatar, String nombreDeUsuario, String correoElectronico, String contrasena, double saldoInicial) {
        super(idCuenta, correoElectronico, contrasena, nombreDeUsuario);
        this.nombrCompleto = nombrCompleto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;
        this.avatar = (avatar == null || avatar.isBlank())
                ? AVATAR_PREDETERMINADO
                : avatar;
        this.saldo = saldoInicial;
        this.colaReproduccion = new ColaDeCanciones();
    }

    public Usuario(int idCuenta, String nombrCompleto, LocalDate fechaDeNacimiento, String nacionalidad, String cedula, String avatar, String nombreDeUsuario, String correoElectronico, double saldoInicial) {
        super(idCuenta, correoElectronico, nombreDeUsuario);
        this.nombrCompleto = nombrCompleto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;
        this.avatar = (avatar == null || avatar.isBlank())
                ? AVATAR_PREDETERMINADO
                : avatar;
        this.saldo = saldoInicial;
        this.colaReproduccion = new ColaDeCanciones();
    }

    public Usuario(int idCuenta, String nombrCompleto, LocalDate fechaDeNacimiento, String nacionalidad, String cedula, String avatar, String nombreDeUsuario, String correoElectronico, String contrasena) {
        super(idCuenta, correoElectronico, contrasena, nombreDeUsuario);
        this.nombrCompleto = nombrCompleto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;
        this.avatar = (avatar == null || avatar.isBlank())
                ? AVATAR_PREDETERMINADO
                : avatar;
        this.saldo = BONO_BIENVENIDA;
        this.colaReproduccion = new ColaDeCanciones();
    }

    public String getNombrCompleto() {
        return nombrCompleto;
    }

    public void setNombrCompleto(String nombrCompleto) {
        this.nombrCompleto = nombrCompleto;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = (avatar == null || avatar.isBlank())
                ? AVATAR_PREDETERMINADO
                : avatar;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo.");
        }
        this.saldo = Math.round(saldo * 100.0) / 100.0;
    }

    public void agregarSaldo(double cantidad) throws DatosInvalidosException {
        if (cantidad <= 0) {
            throw new DatosInvalidosException("La recarga debe ser mayor que cero.");
        }
        setSaldo(this.saldo + cantidad);
    }

    public ColaDeCanciones getColaReproduccion() {
        return colaReproduccion;
    }

    public void agregarCancionACola(Cancion cancion)
            throws DatosInvalidosException {

        if (cancion == null) {
            throw new DatosInvalidosException("La canción seleccionada no es válida.");
        }
        colaReproduccion.insertarElemento(cancion);
    }

    public static boolean esMayorDeEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) {
            return false;
        }

        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }

    @Override
    public String toString() {
        return "\nID de cuenta: " + getIdCuenta() +
                "\nNombre completo: " + nombrCompleto +
                "\nNombre de usuario: " + getNombreUsuario() +
                "\nCorreo electrónico: " + getCorreoElectronico() +
                "\nFecha de nacimiento: " + fechaDeNacimiento +
                "\nNacionalidad: " + nacionalidad +
                "\nCédula: " + cedula +
                "\nAvatar: " + avatar +
                "\nSaldo: $" + String.format("%.2f", saldo);
    }
}