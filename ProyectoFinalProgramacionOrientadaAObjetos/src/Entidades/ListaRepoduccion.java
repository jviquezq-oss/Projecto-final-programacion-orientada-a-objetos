package Entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListaRepoduccion {
    private String nombre;
    private LocalDate fechaCreacion;
    private double calificacion;
    private List<Cancion> cancionesContenidas = new ArrayList<>();

    public ListaRepoduccion(String nombre, LocalDate fechaCreacion, double calificacion) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
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

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public List<Cancion> getCancionesContenidas() {
        return cancionesContenidas;
    }

   public void agregarCanciones(Cancion nuevaCancion){
        this.cancionesContenidas.add(nuevaCancion);
   }
   public String verCancionesEnLista(){
        String contenidosDeLista = "Canciones contenidas en lista de reproduccion: "+this.nombre+"\n";
        for(Cancion cancion: this.cancionesContenidas){
            contenidosDeLista+= "Nombre: "+cancion.getNombre()+"\n"+
                                "Album: "+cancion.getAlbum()+"\n"+
                                "Artista: "+cancion.getArtista()+"\n"+
                                "-------------------------------------\n";
        }
        return  contenidosDeLista;
   }
   public String toString(){
        String cancionesContenidas = "";
        for(Cancion cancion:this.cancionesContenidas){
            cancionesContenidas = cancionesContenidas+cancion.getNombre()+"\n";
        }
        return "Lista de repoduccion:\n"+
                "Nombre: "+this.nombre+"\n"+
                "Fecha de creacion: "+this.fechaCreacion.toString()+"\n"+
                "Calificacion: "+this.calificacion+"\n"+
                "Canciones contenidad: \n"+cancionesContenidas;
   }

}
