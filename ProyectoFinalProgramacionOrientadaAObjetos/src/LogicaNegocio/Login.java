package LogicaNegocio;

import Entidades.Administador;
import Entidades.Usuario;
import LogicaNegocio.AdministradorUsuarios;
import Menus.MenuAdministrador;
import Menus.MenuUsuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Login {

    public static void iniciar()
            throws IOException {

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                System.in));

        while (true) {

            System.out.println();
            System.out.println("===== Reproductor de musica =====");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Crear Usuario");
            System.out.println("3. Iniciar sesion administrador");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            String opcionTexto =
                    reader.readLine();

            int opcion;

            try {

                opcion =
                        Integer.parseInt(
                                opcionTexto);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Debe ingresar un número válido.");

                continue;
            }

            switch (opcion) {

                case 1:

                    iniciarSesion(reader);
                    break;


                case 2:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    AdministradorUsuarios.solicitarUsuario();
                    break;

                case 3:
                    iniciarSesionAdmin(reader);
                    break;
                case 4:
                    return;
                default:

                    System.out.println(
                            "Seleccione una opción entre 1 y 4.");
            }
        }
    }

    private static void iniciarSesion(
            BufferedReader reader)
            throws IOException {

        System.out.println();
        System.out.println(
                "===== INICIO DE SESIÓN =====");

        System.out.print(
                "Nombre de usuario: ");

        String username =
                reader.readLine();

        System.out.print(
                "Contraseña: ");

        String password =
                reader.readLine();

        Usuario usuario =
                AdministradorUsuarios
                        .autenticarUsuario(
                                username,
                                password);

        if (usuario == null) {

            System.out.println("Usuario o contraseña incorrectos.");
            return;

        }

        System.out.println(
                "Bienvenido "
                        + usuario.getNombrCompleto());
        MenuUsuario.mostrarMenu();
    }

    private static void iniciarSesionAdmin(
            BufferedReader reader)
            throws IOException {

        System.out.println();
        System.out.println(
                "===== INICIO DE SESIÓN =====");

        System.out.print(
                "Nombre de usuario: ");

        String username =
                reader.readLine();

        System.out.print(
                "Contraseña: ");

        String password =
                reader.readLine();

        Administador admin =
                AdministradorUsuarios.autenticarUsuarioAdmin(
                                username,
                                password);

        if (admin == null) {

            System.out.println(
                    "Usuario o contraseña incorrectos.");
            return;
        }

        MenuAdministrador.mostrarMenuAdministrador();
    }
}