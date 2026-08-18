package Entidades;

import Excepciones.DatosInvalidosException;
import java.time.LocalDate;

public class Cancion {

    private int idCancion;
    private String nombre;
    private String genero;
    private String artista;
    private String compositor;
    private LocalDate fechaLanzamiento;
    private String album;
    private String caratula;
    private double sumaCalificaciones;
    private int cantidadCalificaciones;
    private double precio;
    private int duracion;

    public Cancion(String nombre, String genero, String artista, String compositor, LocalDate fechaLanzamiento, String album, String caratula, double precio, int duracion) {
        this.nombre = nombre;
        this.genero = genero;
        this.artista = artista;
        this.compositor = compositor;
        this.fechaLanzamiento = fechaLanzamiento;
        this.album = album;
        this.caratula = caratula;
        this.sumaCalificaciones = 0.0;
        this.cantidadCalificaciones = 0;
        this.precio = precio;
        this.duracion = duracion;
    }
    public Cancion(int idCancion, String nombre, String genero, String artista, String compositor, LocalDate fechaLanzamiento, String album, String caratula, double sumaCalificaciones, int cantidadCalificaciones, double precio, int duracion) {
        this.idCancion = idCancion;
        this.nombre = nombre;
        this.genero = genero;
        this.artista = artista;
        this.compositor = compositor;
        this.fechaLanzamiento = fechaLanzamiento;
        this.album = album;
        this.caratula = caratula;
        this.sumaCalificaciones = sumaCalificaciones;
        this.cantidadCalificaciones = cantidadCalificaciones;
        this.precio = precio;
        this.duracion = duracion;
    }

    public int getIdCancion() {
        return this.idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getArtista() {
        return this.artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getCompositor() {
        return this.compositor;
    }

    public void setCompositor(String compositor) {
        this.compositor = compositor;
    }

    public LocalDate getFechaLanzamiento() {
        return this.fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCaratula() {
        return this.caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public double getSumaCalificaciones() {
        return this.sumaCalificaciones;
    }

    public int getCantidadCalificaciones() {
        return this.cantidadCalificaciones;
    }

    public void agregarCalificacion(double calificacion) throws DatosInvalidosException {

        if (calificacion < 0.0 || calificacion > 5.0) {
            throw new DatosInvalidosException(
                    "La calificación debe estar entre 0.0 y 5.0."
            );
        }
        double valorMultiplicado = calificacion * 10.0;
        if (Math.abs(valorMultiplicado - Math.rint(valorMultiplicado)) > 0.0000001) {
            throw new DatosInvalidosException(
                    "La calificación debe tener como máximo una posición decimal."
            );
        }
        this.sumaCalificaciones += calificacion;
        this.cantidadCalificaciones++;
    }

    public double getCalificacion() {
        if (this.cantidadCalificaciones == 0) {
            return 0.0;
        }
        double promedio = this.sumaCalificaciones / this.cantidadCalificaciones;
        return Math.round(promedio * 10.0) / 10.0;
    }

    public double getPrecio() {
        return this.precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public int getDuracion() {return this.duracion;}
    public void setDuracion(int duracion) {this.duracion = duracion;}

    @Override
    public String toString() {
        return "ID: " + idCancion +
                "\nNombre: " + nombre +
                "\nGénero: " + genero +
                "\nArtista: " + artista +
                "\nCompositor: " + compositor +
                "\nFecha de lanzamiento: " + fechaLanzamiento +
                "\nÁlbum: " + album +
                "\nCarátula: " + caratula +
                "\nPrecio: " + precio +
                "\nDuración: " + duracion + " segundos";
    }
}