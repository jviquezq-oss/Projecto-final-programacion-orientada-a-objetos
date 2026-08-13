package Menus;
import Controlador.Controlador;
import Entidades.Cancion;
import Entidades.ListaRepoduccion;
import Excepciones.ParametroInvalidoException;

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

    public static int solicitarCompraDespuesDePrevisualizar() throws IOException {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       PREVISUALIZACIÓN FINALIZADA");
        System.out.println("========================================");
        System.out.println("1. Comprar canción");
        System.out.println("0. Cancelar");
        System.out.println("========================================");
        System.out.println("Seleccione una opcion");
        return Controlador.validarEntero(reader.readLine(),"opcion");
    }
    public static ListaRepoduccion solicitarListaReproduccion(List<ListaRepoduccion> listas) {
        try {
            if (listas == null || listas.isEmpty()) {
                System.out.println("No hay listas de reproducción disponibles.");
                return null;
            }

            System.out.println("\n===== LISTAS DE REPRODUCCIÓN =====");

            for (int i = 0; i < listas.size(); i++) {
                System.out.println((i + 1) + ". " + listas.get(i).getNombre()+" calificicacion: "+listas.get(i).getCalificacion());
            }

            System.out.println("0. Regresar");

            int seleccion = Controlador.validarEntero(reader.readLine(), "selección");

            if (seleccion == 0) {
                return null;
            }

            if (seleccion < 1 || seleccion > listas.size()) {
                System.out.println("Selección inválida.");
                return null;
            }

            return listas.get(seleccion - 1);

        } catch (IOException | ParametroInvalidoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static boolean solicitarOtraCancion() throws IOException {
        while (true) {
            System.out.print("\n¿Desea agregar otra canción? (S/N): ");
            String respuesta = reader.readLine().trim();

            if (respuesta.equalsIgnoreCase("S")) {
                return true;
            }

            if (respuesta.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Ingrese S para continuar o N para finalizar.");
        }
    }
    public static Cancion solicitarCancionDeLista(List<Cancion> canciones) {
        try {
            if (canciones == null || canciones.isEmpty()) {
                System.out.println("La lista de reproducción no contiene canciones.");
                return null;
            }

            System.out.println("\n===== CANCIONES DE LA LISTA =====");

            for (int i = 0; i < canciones.size(); i++) {
                System.out.println((i + 1) + ". " + canciones.get(i));
            }

            System.out.println("0. Regresar");

            int seleccion = Controlador.validarEntero(reader.readLine(), "selección");

            if (seleccion == 0) {
                return null;
            }

            if (seleccion < 1 || seleccion > canciones.size()) {
                System.out.println("Selección inválida.");
                return null;
            }

            return canciones.get(seleccion - 1);

        } catch (IOException | ParametroInvalidoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static int seleccionarListaReproduccion() {
        System.out.println("\n===== SELECCIONAR LISTA DE REPRODUCCIÓN =====");
        System.out.println("1. Mostrar todas");
        System.out.println("2. Buscar por nombre");
        System.out.println("0. Regresar");

        try {
            return Controlador.validarEntero(
                    reader.readLine(),
                    "opción"
            );
        } catch (IOException | ParametroInvalidoException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
