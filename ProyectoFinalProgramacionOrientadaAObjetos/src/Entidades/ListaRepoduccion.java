package Entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListaRepoduccion {
    private String nombre;
    private LocalDate fechaCreacion;
    private double calificacion;
    private List<Cancion> cancionesContenidas = new ArrayList();

    public ListaRepoduccion(String nombre, LocalDate fechaCreacion, double calificacion) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public List<Cancion> getCancionesContenidas() {
        return this.cancionesContenidas;
    }

    public void agregarCanciones(Cancion nuevaCancion) {
        this.cancionesContenidas.add(nuevaCancion);
    }

    public String verCancionesEnLista() {
        String contenidosDeLista = "Canciones contenidas en lista de reproduccion: " + this.nombre + "\n";

        for(Cancion cancion : this.cancionesContenidas) {
            contenidosDeLista = contenidosDeLista + "Nombre: " + cancion.getNombre() + "\nAlbum: " + cancion.getAlbum() + "\nArtista: " + cancion.getArtista() + "\n-------------------------------------\n";
        }

        return contenidosDeLista;
    }

    public String toString() {
        String cancionesContenidas = "";

        for(Cancion cancion : this.cancionesContenidas) {
            cancionesContenidas = cancionesContenidas + cancion.getNombre() + "\n";
        }

        String var10000 = this.nombre;
        return "Lista de repoduccion:\nNombre: " + var10000 + "\nFecha de creacion: " + this.fechaCreacion.toString() + "\nCalificacion: " + this.calificacion + "\nCanciones contenidad: \n" + cancionesContenidas;
    }
}
