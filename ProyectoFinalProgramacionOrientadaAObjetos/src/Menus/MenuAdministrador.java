package Menus;

import LogicaNegocio.AdministadorCatalogo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuAdministrador {
    private static BufferedReader reader;

    public MenuAdministrador() {
    }

    public static void mostrarMenuAdministrador() throws IOException {
        int opcion = 0;

        do {
            System.out.println();
            System.out.println("===== MENU ADMINISTRADOR =====");
            System.out.println("1. Agregar nueva canción al catálogo");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException var2) {
                System.out.println("Debe ingresar un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    AdministadorCatalogo.agregarCancion();
                    System.out.println("\nPresione ENTER para continuar...");
                    reader.readLine();
                    break;
                case 2:
                    System.out.println("Cerrando menú administrador...");
                    break;
                default:
                    System.out.println("Seleccione una opción válida.");
            }
        } while(opcion != 2);

    }

    static {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
}
