package Menus;

import Entidades.CatalogoGeneral;
import Estructuras.ColaDeCanciones;
import LogicaNegocio.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuUsuario {

    public static void mostrarMenu()
            throws IOException {

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                System.in));

        int opcion = 0;

        do {

            System.out.println();
            System.out.println("===== Menu usuario =====");
            System.out.println("1. Comprar una canción");
            System.out.println("2. Agregar una canción a la cola");
            System.out.println("3. Crear lista de reproducción");
            System.out.println("4. Ver listas de reproducción");
            System.out.println("5. Agregar canción a lista de reproducción");
            System.out.println("6.Agregar creditos");
            System.out.println("6. Cerrar sesión");

            System.out.print("Seleccione una opción: ");

            try {

                opcion =
                        Integer.parseInt(
                                reader.readLine());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Debe ingresar un número válido.");

                continue;
            }

            switch (opcion) {

                case 1:
                    AdministadorCatalogo.comprarCancion();
                    break;

                case 2:

                    AdministradorColaDeCanciones.agregarCancionAlaCola(SesionUsuario.getUsuarioActivo().getColaReproduccion(),SesionUsuario.getUsuarioActivo().getColeccionComprada());
                    break;

                case 3:

                    AdministradorListasDeReproduccion.crearListaReproduccion();
                    break;

                case 4:

                    AdministradorListasDeReproduccion.mostrarListasDeReproduccion(SesionUsuario.getUsuarioActivo().getListasPersonales());
                    break;

                case 5:

                    AdministradorListasDeReproduccion.AgregarCancionAListaDeReproduccion(SesionUsuario.getUsuarioActivo().getListasPersonales());
                    break;
                case 6 :
                    AdministradorUsuarios.AgregarCreditos(SesionUsuario.getUsuarioActivo());
                case 7:

                    System.out.println(
                            "Sesión finalizada.");

                    break;

                default:

                    System.out.println(
                            "Seleccione una opción entre 1 y 7.");
            }

        } while (opcion != 6);
    }

}