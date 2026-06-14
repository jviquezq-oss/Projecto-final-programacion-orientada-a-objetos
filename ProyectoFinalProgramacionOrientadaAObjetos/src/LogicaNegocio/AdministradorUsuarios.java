package LogicaNegocio;

import Entidades.Administador;
import Entidades.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdministradorUsuarios {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static List<Usuario> usuariosDeSistema = new ArrayList<>();
    private static List<Administador>administradoresDeSistema = new ArrayList<>();

    public static void registrarUsuario(
            String nombreCompleto,
            LocalDate fechaNacimiento,
            String nacionalidad,
            String cedula,
            String avatar,
            String nombreUsuario,
            String correo,
            String contrasena) {

        Usuario usuario =
                new Usuario(
                        nombreCompleto,
                        fechaNacimiento,
                        nacionalidad,
                        cedula,
                        avatar,
                        nombreUsuario,
                        correo,
                        contrasena);
        //TEMP usado para agregar dinero en default
        usuario.setSaldo(200.0);
        usuariosDeSistema.add(usuario);
    }
    public static boolean existeNombreUsuario(String nombreUsuario){
        for(Usuario usuario: usuariosDeSistema){
            if(usuario.getCuentaUsuario().getNombreUsuario().equalsIgnoreCase(nombreUsuario.trim())){
                return true;
            }
        }
        return false;
    }
    public static Usuario autenticarUsuario(String nombreUsuario, String contrasena){
        for(Usuario usuario :usuariosDeSistema){
            if(usuario.getCuentaUsuario().getNombreUsuario().equalsIgnoreCase(nombreUsuario) && usuario.getCuentaUsuario().getContrasena().equalsIgnoreCase(contrasena)){
                SesionUsuario.setUsuarioActivo(usuario);
                return usuario;
            }
        }
        return null;
    }
    public static Administador autenticarUsuarioAdmin(String nombreUsuario, String contrasena){
        for(Administador administador :administradoresDeSistema){
            if(administador.getCuenta().getNombreUsuario().equalsIgnoreCase(nombreUsuario) && administador.getCuenta().getContrasena().equalsIgnoreCase(contrasena)){
                return administador;
            }
        }
        return null;
    }
    public static void solicitarUsuario()
            throws IOException {

        System.out.println();
        System.out.println(
                "===== Registro de usuario =====");

        String nombreCompleto;
        String nacionalidad;
        String cedula;
        String avatar;
        String nombreUsuario;
        String correo;
        String password;
        LocalDate fechaNacimiento;

        while (true) {

            System.out.print(
                    "Nombre completo: ");

            nombreCompleto =
                    reader.readLine().trim();

            if (!nombreCompleto.isEmpty())
                break;

            System.out.println(
                    "El nombre no puede estar vacío.");
        }

        while (true) {

            try {

                System.out.print(
                        "Fecha nacimiento (YYYY-MM-DD): ");

                fechaNacimiento =
                        LocalDate.parse(
                                reader.readLine());

                break;

            } catch (Exception e) {

                System.out.println(
                        "Fecha inválida.");
            }
        }

        while (true) {

            System.out.print(
                    "Nacionalidad: ");

            nacionalidad =
                    reader.readLine().trim();

            if (!nacionalidad.isEmpty())
                break;

            System.out.println(
                    "La nacionalidad es obligatoria.");
        }

        while (true) {

            System.out.print(
                    "Cedula: ");

            cedula =
                    reader.readLine().trim();

            if (!cedula.isEmpty())
                break;

            System.out.println(
                    "La cédula es obligatoria.");
        }

        System.out.print(
                "Avatar (ruta de imagen): ");

        avatar =
                reader.readLine().trim();

        while (true) {

            System.out.print(
                    "Nombre de usuario: ");

            nombreUsuario =
                    reader.readLine().trim();

            if (nombreUsuario.isEmpty()) {

                System.out.println(
                        "Debe ingresar un nombre de usuario.");

                continue;
            }

            if (existeNombreUsuario(
                    nombreUsuario)) {

                System.out.println(
                        "Ese nombre de usuario ya existe.");

                continue;
            }

            break;
        }

        while (true) {

            System.out.print(
                    "Correo electrónico: ");

            correo =
                    reader.readLine().trim();

            String regexCorreo =
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

            if (correo.matches(
                    regexCorreo))
                break;

            System.out.println(
                    "Correo inválido.");
        }

        while (true) {

            System.out.print(
                    "Contraseña: ");

            password =
                    reader.readLine();

            String regexPassword =
                    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,12}$";

            if (password.matches(
                    regexPassword))
                break;

            System.out.println(
                    """
                    La contraseña debe:
                    - Tener entre 8 y 12 caracteres
                    - Tener una mayúscula
                    - Tener una minúscula
                    - Tener un número
                    - Tener un carácter especial
                    """);
        }

        registrarUsuario(
                nombreCompleto,
                fechaNacimiento,
                nacionalidad,
                cedula,
                avatar,
                nombreUsuario,
                correo,
                password);

        System.out.println();
        System.out.println(
                "Usuario registrado correctamente.");
    }
    public static void AgregarCreditos(Usuario usuario) throws IOException {
        int cantidadDeSaldo;
        System.out.println("=====Agregar saldo=====\n Ingrese la cantidad de saldo que desea agregar");
        while(true){
            try{
                cantidadDeSaldo = Integer.parseInt(reader.readLine());
                 break;
            }catch(NumberFormatException e){
                System.out.println("Debe ingresar un numero valido");
            }
        }
        usuario.setSaldo(cantidadDeSaldo);

    }
    public static void agregarAdmin(String usuario, String email, String contrasena) {
        administradoresDeSistema.add(new Administador(usuario, email, contrasena));
    }

}
