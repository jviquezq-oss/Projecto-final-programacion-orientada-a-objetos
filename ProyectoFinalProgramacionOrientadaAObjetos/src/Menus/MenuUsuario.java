package Menus;
import Controlador.Controlador;
import Entidades.Cancion;
import Entidades.ListaRepoduccion;
import Entidades.Nacionalidad;
import Excepciones.ParametroInvalidoException;
import util.ValidadorEntrada;

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
        return ValidadorEntrada.validarEntero(reader.readLine(),"opcion");
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

            int seleccion = ValidadorEntrada.validarEntero(reader.readLine(), "selección");

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

            int seleccion = ValidadorEntrada.validarEntero(reader.readLine(), "selección");

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
            return ValidadorEntrada.validarEntero(
                    reader.readLine(),
                    "opción"
            );
        } catch (IOException | ParametroInvalidoException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public static int mostrarMenuPrincipal() throws IOException {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Ver colección de canciones");
        System.out.println("2. Buscar canción");
        System.out.println("3. Comprar canción");
        System.out.println("4. Calificar canción");
        System.out.println("5. Administrar listas de reproducción");
        System.out.println("6. Administrar reproductor");
        System.out.println("7. Reproducir lista de reproducción");
        System.out.println("8. Ver Top 3");
        System.out.println("9. Cambiar contraseña");
        System.out.println("10. Recargar saldo");
        System.out.println("0. Cerrar sesión");
        System.out.print("Seleccione una opción: ");

        return ValidadorEntrada.validarEntero(reader.readLine(), "opción");
    }
    public static int menuListasReproduccion() {
        System.out.println("\n===== LISTAS DE REPRODUCCIÓN =====");
        System.out.println("1. Ver listas de reproducción");
        System.out.println("2. Crear lista");
        System.out.println("3. Agregar canción a lista");
        System.out.println("4. Eliminar canción de lista");
        System.out.println("5. Eliminar lista");
        System.out.println("0. Regresar");

        try {
            return ValidadorEntrada.validarEntero(reader.readLine(), "opción");
        } catch (IOException | ParametroInvalidoException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public static String solicitarNacionalidad() throws IOException, ParametroInvalidoException {
        Nacionalidad[] nacionalidades = Nacionalidad.values();

        System.out.println("\n===== NACIONALIDAD =====");

        for (int i = 0; i < nacionalidades.length; i++) {
            System.out.println((i + 1) + ". " + nacionalidades[i].name().replace("_", " "));
        }

        System.out.println("0. Regresar");

        int opcion = ValidadorEntrada.validarEntero(reader.readLine(), "opción");

        if (opcion == 0) {
            return null;
        }

        if (opcion < 1 || opcion > nacionalidades.length) {
            throw new ParametroInvalidoException("Seleccione una nacionalidad válida.");
        }

        return nacionalidades[opcion - 1].name();
    }
    public static void mostrarListasReproduccion(List<ListaRepoduccion> listas) {
        System.out.println("\n===== MIS LISTAS DE REPRODUCCIÓN =====");

        if (listas == null || listas.isEmpty()) {
            System.out.println("No tiene listas de reproducción.");
            return;
        }

        for (ListaRepoduccion lista : listas) {
            System.out.println("ID: " + lista.getIdLista());
            System.out.println("Nombre: " + lista.getNombre());
            System.out.println("Fecha de creación: " + lista.getFechaCreacion());
            System.out.println("Calificación: " + lista.getCalificacion());
            System.out.println("----------------------------------------");
        }
    }
    public static String solicitarReproduccionTop3() throws IOException {
        System.out.print("\n¿Desea reproducir uno de los Top 3? (S/N): ");
        return reader.readLine();
    }
    public static int seleccionarTop3() throws IOException {
        System.out.println("\n===== REPRODUCIR TOP 3 =====");
        System.out.println("1. Top 3 mejores calificaciones");
        System.out.println("2. Top 3 más compradas");
        System.out.println("3. Top 3 más incluidas en listas");
        System.out.println("0. Regresar");
        System.out.print("Seleccione una opción: ");

        return ValidadorEntrada.validarEntero(reader.readLine(), "opción");
    }
    private static void mostrarRanking(List<Cancion> canciones, boolean mostrarCalificacion) {
        if (canciones == null || canciones.isEmpty()) {
            System.out.println("No hay canciones disponibles.");
            return;
        }

        for (int i = 0; i < canciones.size(); i++) {
            Cancion cancion = canciones.get(i);

            if (mostrarCalificacion) {
                System.out.println((i + 1) + ". " + cancion.getNombre() + " - " + cancion.getArtista() + " - Calificación: " + cancion.getCalificacion());
            } else {
                System.out.println((i + 1) + ". " + cancion.getNombre() + " - " + cancion.getArtista());
            }
        }
    }
    public static List<Cancion> mostrarTop3(
            List<Cancion> mejoresCalificadas,
            List<Cancion> masCompradas,
            List<Cancion> masIncluidas) {

        System.out.println("\n===== TOP 3 - MEJORES CALIFICACIONES =====");
        mostrarRanking(mejoresCalificadas, true);

        System.out.println("\n===== TOP 3 - MÁS COMPRADAS =====");
        mostrarRanking(masCompradas, false);

        System.out.println("\n===== TOP 3 - MÁS INCLUIDAS EN LISTAS =====");
        mostrarRanking(masIncluidas, false);

        return null;
    }
}
