package util;

import Entidades.Cancion;
import java.util.ArrayList;
import java.util.List;

public class BuscadorDeCanciones {
    public BuscadorDeCanciones() {
    }

    public static List<Cancion> buscar(List<Cancion> canciones, String textoBusqueda) {
        List<Cancion> resultados = new ArrayList<>();
        String busqueda = textoBusqueda.toLowerCase().trim();

        for(Cancion cancion : canciones) {
            boolean coincide = cancion.getNombre().toLowerCase().contains(busqueda) || cancion.getGenero().toLowerCase().contains(busqueda) || cancion.getArtista().toLowerCase().contains(busqueda);
            if (coincide) {
                resultados.add(cancion);
            }
        }

        return resultados;
    }
}
