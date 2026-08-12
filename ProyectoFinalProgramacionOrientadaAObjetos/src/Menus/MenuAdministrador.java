package Menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuAdministrador {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private MenuAdministrador() {
    }

    public static int mostrarMenu() throws IOException {
        System.out.println("\n========================================");
        System.out.println("          MENÚ DE ADMINISTRADOR");
        System.out.println("========================================");
        System.out.println("1. Administrar canciones");
        System.out.println("2. Administrar usuarios");
        System.out.println("3. Consultar estadísticas");
        System.out.println("4. Reproducir canciones");
        System.out.println("5. Reproducir listas de reproducción");
        System.out.println("0. Cerrar sesión");
        System.out.println("========================================");
        System.out.print("Seleccione una opción: ");

        String entrada = reader.readLine();

        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número valido.");
            return -1;
        }
    }
    public static int menuCanciones() throws IOException {
        System.out.println("\n========================================");
        System.out.println("       ADMINISTRACIÓN DE CANCIONES");
        System.out.println("========================================");
        System.out.println("1. Crear canción");
        System.out.println("2. Buscar canción");
        System.out.println("3. Modificar canción");
        System.out.println("4. Eliminar canción");
        System.out.println("0. Regresar");
        System.out.println("========================================");
        System.out.print("Seleccione una opción: ");

        String entrada = reader.readLine();

        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número entero.");
            return -1;
        }
    }
}