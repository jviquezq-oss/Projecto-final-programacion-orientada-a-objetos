package Entidades;

import java.time.LocalDate;

public class ListaRepoduccion {

    private int idLista;
    private Usuario propietario;
    private String nombre;
    private LocalDate fechaCreacion;
    private double calificacion;

    public ListaRepoduccion(Usuario propietario, String nombre, LocalDate fechaCreacion) {
        this.propietario = propietario;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
    }

    public ListaRepoduccion(int idLista, Usuario propietario, String nombre, LocalDate fechaCreacion,double calificacion) {
        this.idLista = idLista;
        this.propietario = propietario;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
    }

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public double getCalificacion(){
        return this.calificacion;
    }
    @Override
    public String toString() {
        return "ListaRepoduccion{" +
                "idLista=" + idLista +
                ", propietario=" + propietario +
                ", nombre='" + nombre + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}