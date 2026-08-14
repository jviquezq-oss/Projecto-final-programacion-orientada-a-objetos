package Menus;
import java.io.IOException;
import Controlador.Controlador;
import util.ValidadorEntrada;

public class MenuAdministrador extends Menu {
    private MenuAdministrador() {
    }

    public static int mostrarMenu() throws IOException {
        System.out.println("\n===== MENÚ ADMINISTRADOR =====");
        System.out.println("1. Administrar canciones");
        System.out.println("2. Administrar usuarios");
        System.out.println("3. Administrar reproductor");
        System.out.println("4. Reproducir playlist");
        System.out.println("5. Cambiar contraseña");
        System.out.println("0. Cerrar sesión");
        System.out.print("Seleccione una opción: ");

        return ValidadorEntrada.validarEntero(reader.readLine(), "opción");
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

      return ValidadorEntrada.validarEntero(reader.readLine(),"opcion");
    }
    public static int administrarUsuarios() throws IOException {
        System.out.println("\n===== ADMINISTRAR USUARIOS =====");
        System.out.println("1. Mostrar usuarios");
        System.out.println("2. Buscar usuario");
        System.out.println("3. Modificar usuario");
        System.out.println("4. Eliminar usuario");
        System.out.println("5. Agregar saldo");
        System.out.println("0. Regresar");
        System.out.print("Seleccione una opción: ");

        return ValidadorEntrada.validarEntero(reader.readLine(), "opción");
    }
    public static int seleccionarUsuario() throws IOException {
        System.out.println("\n===== SELECCIONAR USUARIO =====");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por nombre de usuario");
        System.out.println("0. Regresar");
        System.out.print("Seleccione una opción: ");

        return ValidadorEntrada.validarEntero(reader.readLine(), "opción");
    }
}