package LogicaNegocio;

import Entidades.Cancion;
import Entidades.ListaRepoduccion;
import Estructuras.ColaDeCanciones;
import Excepciones.ColaVaciaException;
import java.util.List;

public class GestorColaDeCanciones {

    public static void reproducirCancion(ColaDeCanciones cola, Cancion cancion) {
        cola.insertarInicio(cancion);
    }

    public static void reproducirPlaylist(ColaDeCanciones cola, ListaRepoduccion lista) throws Exception {
        cola.limpiarCola();

        List<Cancion> canciones = GestorListaRepoduccion.obtenerCanciones(lista.getIdLista());

        for (Cancion cancion : canciones) {
            cola.insertarElemento(cancion);
        }
    }

    public static Cancion siguienteCancion(ColaDeCanciones cola) throws ColaVaciaException {
        return cola.removerElemento();
    }

    public static Cancion verCancionActual(ColaDeCanciones cola) throws ColaVaciaException {
        return cola.verFrente();
    }

    public static void limpiarCola(ColaDeCanciones cola) {
        cola.limpiarCola();
    }
}