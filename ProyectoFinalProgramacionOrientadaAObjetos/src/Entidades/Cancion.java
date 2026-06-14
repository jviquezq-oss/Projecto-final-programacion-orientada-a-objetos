package Entidades;

import java.time.LocalDate;

public class Cancion {
    private  static int idCanciones;
    private int idCancion;
    private String nombre;
    private String genero;
    private String artista;
    private String compositor;
    private LocalDate fechaLanzamiento;
    private String album;
    private String caratula;
    private double calificacion;
    private double precio;

    public Cancion(String nombre, String genero, String artista, String compositor, LocalDate fechaLanzamiento, String album, String caratula, double calificacion, double precio) {
        this.idCancion = idCanciones ++;
        this.nombre = nombre;
        this.genero = genero;
        this.artista = artista;
        this.compositor = compositor;
        this.fechaLanzamiento = fechaLanzamiento;
        this.album = album;
        this.caratula = caratula;
        this.calificacion = calificacion;
        this.precio = precio;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getCompositor() {
        return compositor;
    }

    public void setCompositor(String compositor) {
        this.compositor = compositor;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Cancion:" +
                "idCancion:" + idCancion +
                ", nombre:" + nombre + '\'' +
                ", genero:" + genero + '\'' +
                ", artista:" + artista + '\'' +
                ", compositor:" + compositor + '\'' +
                ", fechaLanzamiento:" + fechaLanzamiento +
                ", album:" + album + '\'' +
                ", caratula:" + caratula + '\'' +
                ", calificacion:" + calificacion +
                ", precio:" + precio;
    }
}
