package LogicaNegocio;

import DAO.ListaRepoduccionDAO;
import Entidades.Cancion;
import Entidades.ListaRepoduccion;
import java.util.List;

public class GestorListaCancion {

    public static void agregarCancion(int idLista, int idCancion) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista);

        if (lista == null) {
            throw new Exception("La lista de reproducción no existe.");
        }

        if (ListaRepoduccionDAO.contieneCancion(idLista, idCancion)) {
            throw new Exception("La canción ya pertenece a la lista de reproducción.");
        }

        ListaRepoduccionDAO.agregarCancion(idLista, idCancion);
    }

    public static void eliminarCancion(int idLista, int idCancion) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista);

        if (lista == null) {
            throw new Exception("La lista de reproducción no existe.");
        }

        if (!ListaRepoduccionDAO.contieneCancion(idLista, idCancion)) {
            throw new Exception("La canción no pertenece a la lista de reproducción.");
        }

        ListaRepoduccionDAO.eliminarCancion(idLista, idCancion);
    }

    public static boolean contieneCancion(int idLista, int idCancion) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista);

        if (lista == null) {
            throw new Exception("La lista de reproducción no existe.");
        }

        return ListaRepoduccionDAO.contieneCancion(idLista, idCancion);
    }

    public static List<Cancion> obtenerCanciones(int idLista) throws Exception {
        ListaRepoduccion lista = ListaRepoduccionDAO.buscarPorId(idLista);

        if (lista == null) {
            throw new Exception("La lista de reproducción no existe.");
        }

        return ListaRepoduccionDAO.obtenerCanciones(idLista);
    }
}