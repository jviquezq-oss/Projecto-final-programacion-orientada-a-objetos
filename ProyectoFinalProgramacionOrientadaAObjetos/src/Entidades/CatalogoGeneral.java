package Entidades;

import java.util.ArrayList;
import java.util.List;

public class CatalogoGeneral {
    private static List<Cancion> catalogoGlobal = new ArrayList();

    public CatalogoGeneral() {
    }

    public static void agregarCancionAlCatalogo(Cancion cancion) {
        catalogoGlobal.add(cancion);
    }

    public void removerCancion(Cancion cancion) {
        catalogoGlobal.remove(cancion);
    }

    public static List<Cancion> getCatalogoGlobal() {
        return catalogoGlobal;
    }

    public static String verCatalogoGeneral() {
        String catalogoGeneral = "Todas las canciones disponibles: \n";

        for(Cancion cancion : catalogoGlobal) {
            catalogoGeneral = catalogoGeneral + "Nombre: " + cancion.getNombre() + "\nAlbum: " + cancion.getAlbum() + "\nArtista: " + cancion.getArtista() + "\nCompositor: " + cancion.getCompositor() + "\nGenero: " + cancion.getGenero() + "\nFecha de lanzamiento: " + cancion.getFechaLanzamiento().toString() + "\nCalificacion: " + cancion.getCalificacion() + "\nPrecio: " + cancion.getPrecio() + "\n";
        }

        return catalogoGeneral;
    }
}
