package Menus;

import Controlador.Controlador;

import javax.naming.ldap.Control;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import Entidades.Cancion;
import Entidades.ListaRepoduccion;

public class Menu {
    protected static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static int seleccionarCancion() throws IOException {
        System.out.println("\n========================================");
        System.out.println("         SELECCIONAR CANCIÓN");
        System.out.println("========================================");
        System.out.println("1. Buscar por nombre");
        System.out.println("2. Seleccionar del catalogo");
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
                    (i + 1) + ". " + lista.getNombre()+" calificacion: "+lista.getCalificacion()
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
    public static int mostrarMenuInicio() throws IOException {
        System.out.println("\n===== REPRODUCTOR DE MÚSICA =====");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Crear usuario");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        return Controlador.validarEntero(reader.readLine(), "opción");
    }
}
