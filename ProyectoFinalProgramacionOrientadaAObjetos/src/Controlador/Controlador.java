package Controlador;

import Excepciones.CredencialesInvalidasException;
import Excepciones.ParametroInvalidoException;
import LogicaNegocio.*;
import Entidades.*;
import Menus.MenuAdministrador;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Controlador {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private Controlador() {
    }

    public static void iniciarSesion() {
        System.out.println("\n===== INICIO DE SESIÓN =====");

        for (int intento = 1; intento <= 3; intento++) {
            try {
                System.out.print("Correo electrónico: ");
                String correoElectronico = reader.readLine().trim();

                if (correoElectronico.isEmpty()) {
                    System.out.println("El correo electrónico es obligatorio.");
                    continue;
                }

                System.out.print("Contraseña: ");
                String contrasena = reader.readLine();

                if (contrasena == null || contrasena.isEmpty()) {
                    System.out.println("La contraseña es obligatoria.");
                    continue;
                }

                Login.iniciarSesion(correoElectronico, contrasena);

                System.out.println("Inicio de sesión exitoso.");
                return;

            } catch (CredencialesInvalidasException e) {
                System.out.println(e.getMessage());
                System.out.println("Intentos restantes: " + (3 - intento));
            } catch (IOException e) {
                System.out.println("Error al leer los datos de inicio de sesión.");
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        System.out.println("Se agotaron los 3 intentos de inicio de sesión.");
    }
    public static void menuAdministrador() {
        boolean continuar = true;

        while (continuar) {
            try {
                int opcion = MenuAdministrador.mostrarMenu();

                switch (opcion) {
                    case 1:
                        administrarCanciones();
                        break;

                    case 2:
                        // Administrar usuarios
                        break;

                    case 3:
                        // Consultar estadísticas
                        break;

                    case 4:
                        // Reproducir canciones
                        break;

                    case 5:
                        // Reproducir listas de reproducción
                        break;

                    case 0:
                        SesionUsuario.cerrarSesion();
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }

            } catch (IOException e) {
                System.out.println("Error al leer la opción.");
            }
        }
    }
    public static void administrarCanciones() {
        boolean continuar = true;

        while (continuar) {
            try {
                int opcion = MenuAdministrador.menuCanciones();

                switch (opcion) {
                    case 1:
                        crearCancion();
                        break;

                    case 2:
                        // Buscar canción
                        break;

                    case 3:
                        // Modificar canción
                        break;

                    case 4:
                        // Eliminar canción
                        break;

                    case 0:
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }

            } catch (IOException e) {
                System.out.println("Error al leer la opción.");
            }
        }
    }
    public static void crearCancion() {
        try {
            System.out.println("\n===== CREAR CANCIÓN =====");

            System.out.print("Nombre: ");
            String nombre = validarParametro(reader.readLine(), "nombre");

            System.out.print("Género: ");
            String genero = validarParametro(reader.readLine(), "género");

            System.out.print("Artista: ");
            String artista = validarParametro(reader.readLine(), "artista");

            System.out.print("Compositor: ");
            String compositor = validarParametro(reader.readLine(), "compositor");

            System.out.print("Fecha de lanzamiento (YYYY-MM-DD): ");
            String fechaEntrada = validarParametro(reader.readLine(), "fecha de lanzamiento");
            LocalDate fechaLanzamiento = LocalDate.parse(fechaEntrada);

            System.out.print("Álbum: ");
            String album = validarParametro(reader.readLine(), "álbum");

            System.out.print("Carátula: ");
            String caratula = validarParametro(reader.readLine(), "carátula");

            System.out.print("Precio: ");
            double precio = validarDecimal(reader.readLine(), "precio");

            System.out.print("Duración en segundos: ");
            int duracion = validarEntero(reader.readLine(), "duración");

            Cancion cancion = GestorCancion.registrarCancion(nombre, genero, artista, compositor, fechaLanzamiento, album, caratula, precio, duracion);
            System.out.println("Canción registrada correctamente.");
            System.out.println("ID de la canción: " + cancion.getIdCancion());

        } catch (DateTimeParseException e) {
            System.out.println("La fecha de lanzamiento debe tener un formato válido (YYYY-MM-DD).");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static String validarParametro(String parametro, String nombreParametro) throws ParametroInvalidoException {
        if (parametro == null || parametro.isBlank()) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " no puede estar vacío.");
        }
        return parametro;
    }
    private static int validarEntero(String parametro, String nombreParametro) throws ParametroInvalidoException {
        if (parametro == null || parametro.isBlank()) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " no puede estar vacío.");
        }

        try {
            return Integer.parseInt(parametro);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El campo " + nombreParametro + " debe ser un número entero.");
        }
    }
    private static double validarDecimal(String parametro, String nombreParametro) throws ParametroInvalidoException {
        if (parametro == null || parametro.isBlank()) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " no puede estar vacío.");
        }

        try {
            return Double.parseDouble(parametro);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El campo " + nombreParametro + " debe ser un número válido.");
        }
    }
}