package Entidades;

import java.util.ArrayList;
import java.util.List;

public class CatalogoGeneral {
    private  static List<Cancion> catalogoGlobal = new ArrayList<>();
    public static void agregarCancionAlCatalogo(Cancion cancion){
        catalogoGlobal.add(cancion);
    }
    public void removerCancion(Cancion cancion){
        catalogoGlobal.remove(cancion);
    }
    public static List<Cancion> getCatalogoGlobal(){
        return catalogoGlobal;
    }
    public static String verCatalogoGeneral(){
        String catalogoGeneral = "Todas las canciones disponibles: \n";
        for(Cancion cancion: catalogoGlobal){
            catalogoGeneral+="Nombre: "+cancion.getNombre()+"\n"+
                             "Album: "+cancion.getAlbum()+"\n"+
                             "Artista: "+cancion.getArtista()+"\n"+
                             "Compositor: "+cancion.getCompositor()+"\n"+
                             "Genero: "+cancion.getGenero()+"\n"+
                             "Fecha de lanzamiento: "+cancion.getFechaLanzamiento().toString()+"\n"+
                             "Calificacion: "+cancion.getCalificacion()+"\n"+
                             "Precio: "+cancion.getPrecio()+"\n";

        }
        return catalogoGeneral;
    }

}
