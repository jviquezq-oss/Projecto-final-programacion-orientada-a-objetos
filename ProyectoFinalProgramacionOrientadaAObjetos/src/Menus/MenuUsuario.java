package Menus;
import Controlador.Controlador;
import Entidades.Cancion;

import java.io.IOException;
import java.util.List;

public class MenuUsuario extends Menu{

    public static void mostrarColeccionCanciones(List<Cancion> canciones) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          MI COLECCIÓN DE CANCIONES");
        System.out.println("========================================");

        for (int i = 0; i < canciones.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + canciones.get(i)
            );
            System.out.println("----------------------------------------");
        }
    }
    public static int solicitarMetodoSeleccionCancion() throws IOException {
        System.out.println("\n===== SELECCIONAR CANCIÓN =====");
        System.out.println("1. Seleccionar de todo el catálogo");
        System.out.println("2. Buscar canción");
        System.out.println("0. Regresar");
        System.out.println("Seleccione una opcion: ");
        return Controlador.validarEntero(reader.readLine(),"Seleccione una opción: ");
    }
    public static String solicitarBusquedaCancion() throws IOException {
        System.out.println("\n===== BUSCAR CANCIÓN =====");
        System.out.print("Ingrese nombre, género o artista: ");
        return Controlador.validarParametro(reader.readLine().trim(),"Criterio de busqueda");
    }
    public static Cancion solicitarCancion(List<Cancion> canciones) throws IOException {

        if (canciones == null || canciones.isEmpty()) {
            System.out.println("No hay canciones disponibles.");
            return null;
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println("          SELECCIONAR CANCIÓN");
        System.out.println("========================================");

        for (int i = 0; i < canciones.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + canciones.get(i)
            );
            System.out.println("----------------------------------------");
        }
        System.out.println("Seleccione una opcion: ");
        while (true) {

            int opcion = Controlador.validarEntero(reader.readLine(),"Seleccione una canción (0 para regresar): ");

            if (opcion == 0) {
                return null;
            }

            if (opcion >= 1 && opcion <= canciones.size()) {
                return canciones.get(opcion - 1);
            }

            System.out.println("Seleccione una opción válida.");
        }
    }
}
