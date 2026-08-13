package LogicaNegocio;

import Entidades.Cancion;
import Entidades.ListaRepoduccion;
import Entidades.Usuario;
import Estructuras.ColaDeCanciones;
import Excepciones.ColaVaciaException;

import java.util.List;

public class GestorColaDeCanciones {

    public static void agregarCancionAlInicio(ColaDeCanciones cola, Cancion cancion) {
        cola.insertarInicio(cancion);
    }

    public static void agregarCancionACola(ColaDeCanciones cola, Cancion cancion) {
        cola.insertarElemento(cancion);
    }

    public static void reproducirPlaylist(ColaDeCanciones cola, ListaRepoduccion lista, Usuario usuario) throws Exception {
        if (lista == null) {
            throw new Exception("La lista de reproducción no es válida.");
        }

        List<Cancion> canciones = GestorListaRepoduccion.obtenerCanciones(lista.getIdLista(), usuario);

        if (canciones == null || canciones.isEmpty()) {
            throw new Exception("La lista de reproducción no contiene canciones.");
        }

        cola.limpiarCola();

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