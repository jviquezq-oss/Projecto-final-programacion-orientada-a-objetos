package LogicaNegocio;

import DAO.EstadisticasDAO;
import Entidades.Cancion;
import java.util.List;

public class GestorEstadisticas {

    public static List<Cancion> obtenerTop3MejorCalificadas() throws Exception {
        return EstadisticasDAO.obtenerTop3MejorCalificadas();
    }

    public static List<Cancion> obtenerTop3MasCompradas() throws Exception {
        return EstadisticasDAO.obtenerTop3MasCompradas();
    }

    public static List<Cancion> obtenerTop3MasIncluidasEnListas() throws Exception {
        return EstadisticasDAO.obtenerTop3MasIncluidasEnListas();
    }
}