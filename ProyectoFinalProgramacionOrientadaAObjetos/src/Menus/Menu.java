package Menus;

import Controlador.Controlador;

import javax.naming.ldap.Control;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import Entidades.ListaRepoduccion;

public class Menu {
    protected static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static int seleccionarCancion() throws IOException {
        System.out.println("\n========================================");
        System.out.println("         SELECCIONAR CANCIÓN");
        System.out.println("========================================");
        System.out.println("1. Buscar por nombre");
        System.out.println("2. Buscar por ID");
        System.out.println("0. Regresar");
        System.out.println("========================================");
        System.out.print("Seleccione una opción: ");

        return Controlador.validarEntero(reader.readLine(),"opcion");
    }
    public static int seleccionarReproductor() throws IOException {
        System.out.println("\n===== REPRODUCTOR =====");
        System.out.println("1. Agregar canción a la cola");
        System.out.println("2. Reproducir");
        System.out.println("3. Pausar");
        System.out.println("4. Reanudar");
        System.out.println("5. Avanzar 10 segundos");
        System.out.println("6. Retroceder 10 segundos");
        System.out.println("7. Siguiente");
        System.out.println("8. Ver estado de reproducción");
        System.out.println("9. Regresar");
        System.out.println("Seleccione una opción:");
        return Controlador.validarEntero(reader.readLine(),"opcion");
    }
    public static ListaRepoduccion solicitarListaReproduccion(List<ListaRepoduccion> listasDisponibles) {

        if (listasDisponibles == null || listasDisponibles.isEmpty()) {
            System.out.println("No hay listas de reproducción disponibles.");
            return null;
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println("    SELECCIONAR LISTA DE REPRODUCCIÓN");
        System.out.println("========================================");

        for (int i = 0; i < listasDisponibles.size(); i++) {
            ListaRepoduccion lista = listasDisponibles.get(i);

            System.out.println(
                    (i + 1) + ". " + lista.getNombre()
            );
        }

        System.out.println("0. Regresar");
        System.out.println("========================================");

        while (true) {

            int opcion = 0;
            try {
                opcion = Controlador.validarEntero(reader.readLine(), "opcion de lista");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (opcion == 0) {
                return null;
            }

            if (opcion >= 1 && opcion <= listasDisponibles.size()) {
                return listasDisponibles.get(opcion - 1);
            }

            System.out.println("Seleccione una opción válida.");
        }
    }
}
