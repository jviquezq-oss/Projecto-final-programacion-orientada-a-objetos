package LogicaNegocio;

import DAO.EstadisticasDAO;
import Entidades.Cancion;

import java.sql.SQLException;
import java.util.List;

public class GestorEstadisticas {

    public static List<Cancion> obtenerTop3MejorCalificadas() throws Exception {
        try {
            return EstadisticasDAO.obtenerTop3MejorCalificadas();

        } catch (SQLException e) {
            throw new Exception("La operación obtener Top 3 de mejores calificadas no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> obtenerTop3MasCompradas() throws Exception {
        try {
            return EstadisticasDAO.obtenerTop3MasCompradas();

        } catch (SQLException e) {
            throw new Exception("La operación obtener Top 3 de más compradas no pudo ser realizada.", e);
        }
    }

    public static List<Cancion> obtenerTop3MasIncluidasEnListas() throws Exception {
        try {
            return EstadisticasDAO.obtenerTop3MasIncluidasEnListas();

        } catch (SQLException e) {
            throw new Exception("La operación obtener Top 3 de más incluidas en listas no pudo ser realizada.", e);
        }
    }
}