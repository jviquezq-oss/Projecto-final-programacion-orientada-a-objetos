package LogicaNegocio;

import Entidades.Administador;
import Entidades.Usuario;
import Excepciones.CredencialesInvalidasException;
import Excepciones.DatosInvalidosException;
import Excepciones.NombreUsuarioDuplicadoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdministradorUsuarios {
    private static final BufferedReader reader;
    private static List<Usuario> usuariosDeSistema;
    private static List<Administador> administradoresDeSistema;

    public AdministradorUsuarios() {
    }

    public static void registrarUsuario(String nombreCompleto, LocalDate fechaNacimiento, String nacionalidad, String cedula, String avatar, String nombreUsuario, String correo, String contrasena) throws NombreUsuarioDuplicadoException, DatosInvalidosException {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre completo no puede estar vacío.");
        }
        if (nacionalidad == null || nacionalidad.trim().isEmpty()) {
            throw new DatosInvalidosException("La nacionalidad no puede estar vacía.");
        }
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new DatosInvalidosException("La cédula no puede estar vacía.");
        }
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre de usuario no puede estar vacío.");
        }
        if (existeNombreUsuario(nombreUsuario)) {
            throw new NombreUsuarioDuplicadoException("Ese nombre de usuario ya existe.");
        }
        if (correo == null || !correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new DatosInvalidosException("Correo inválido.");
        }
        if (contrasena == null || !contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,12}$")) {
            throw new DatosInvalidosException("La contraseña debe:\n- Tener entre 8 y 12 caracteres\n- Tener una mayúscula\n- Tener una minúscula\n- Tener un número\n- Tener un carácter especial\n");
        }

        Usuario usuario = new Usuario(nombreCompleto, fechaNacimiento, nacionalidad, cedula, avatar, nombreUsuario, correo, contrasena);
        usuario.setSaldo((double)200.0F);
        usuariosDeSistema.add(usuario);
    }

    public static boolean existeNombreUsuario(String nombreUsuario) {
        for(Usuario usuario : usuariosDeSistema) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario.trim())) {
                return true;
            }
        }

        return false;
    }

    public static Usuario autenticarUsuario(String nombreUsuario, String contrasena) throws CredencialesInvalidasException {
        for(Usuario usuario : usuariosDeSistema) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario) && usuario.getContrasena().equalsIgnoreCase(contrasena)) {
                SesionUsuario.setUsuarioActivo(usuario);
                return usuario;
            }
        }

        throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
    }

    public static Administador autenticarUsuarioAdmin(String nombreUsuario, String contrasena) throws CredencialesInvalidasException {
        for(Administador administador : administradoresDeSistema) {
            if (administador.getNombreUsuario().equalsIgnoreCase(nombreUsuario) && administador.getContrasena().equalsIgnoreCase(contrasena)) {
                return administador;
            }
        }

        throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
    }

    public static void solicitarUsuario() throws IOException {
        System.out.println();
        System.out.println("===== Registro de usuario =====");

        while(true) {
            System.out.print("Nombre completo: ");
            String nombreCompleto = reader.readLine().trim();
            if (!nombreCompleto.isEmpty()) {
                LocalDate fechaNacimiento;
                while(true) {
                    try {
                        System.out.print("Fecha nacimiento (YYYY-MM-DD): ");
                        fechaNacimiento = LocalDate.parse(reader.readLine());
                        break;
                    } catch (Exception var9) {
                        System.out.println("Fecha inválida.");
                    }
                }

                while(true) {
                    System.out.print("Nacionalidad: ");
                    String nacionalidad = reader.readLine().trim();
                    if (!nacionalidad.isEmpty()) {
                        while(true) {
                            System.out.print("Cedula: ");
                            String cedula = reader.readLine().trim();
                            if (!cedula.isEmpty()) {
                                System.out.print("Avatar (ruta de imagen): ");
                                String avatar = reader.readLine().trim();

                                while(true) {
                                    System.out.print("Nombre de usuario: ");
                                    String nombreUsuario = reader.readLine().trim();
                                    if (nombreUsuario.isEmpty()) {
                                        System.out.println("Debe ingresar un nombre de usuario.");
                                    } else {
                                        if (!existeNombreUsuario(nombreUsuario)) {
                                            while(true) {
                                                System.out.print("Correo electrónico: ");
                                                String correo = reader.readLine().trim();
                                                String regexCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                                                if (correo.matches(regexCorreo)) {
                                                    while(true) {
                                                        System.out.print("Contraseña: ");
                                                        String password = reader.readLine();
                                                        regexCorreo = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,12}$";
                                                        if (password.matches(regexCorreo)) {
                                                            try {
                                                                registrarUsuario(nombreCompleto, fechaNacimiento, nacionalidad, cedula, avatar, nombreUsuario, correo, password);
                                                                System.out.println();
                                                                System.out.println("Usuario registrado correctamente.");
                                                                return;
                                                            } catch (NombreUsuarioDuplicadoException | DatosInvalidosException e) {
                                                                System.out.println(e.getMessage());
                                                            }
                                                        }

                                                        System.out.println("La contraseña debe:\n- Tener entre 8 y 12 caracteres\n- Tener una mayúscula\n- Tener una minúscula\n- Tener un número\n- Tener un carácter especial\n");
                                                    }
                                                }

                                                System.out.println("Correo inválido.");
                                            }
                                        }

                                        System.out.println("Ese nombre de usuario ya existe.");
                                    }
                                }
                            }

                            System.out.println("La cédula es obligatoria.");
                        }
                    }

                    System.out.println("La nacionalidad es obligatoria.");
                }
            }

            System.out.println("El nombre no puede estar vacío.");
        }
    }

    public static void AgregarCreditos(Usuario usuario) throws IOException {
        System.out.println("=====Agregar saldo=====\n Ingrese la cantidad de saldo que desea agregar");

        int cantidadDeSaldo;
        while(true) {
            try {
                cantidadDeSaldo = Integer.parseInt(reader.readLine());
                break;
            } catch (NumberFormatException var3) {
                System.out.println("Debe ingresar un numero valido");
            }
        }

        usuario.setSaldo((double)cantidadDeSaldo);
    }

    public static void agregarAdmin(String usuario, String email, String contrasena) {
        administradoresDeSistema.add(new Administador(usuario, email, contrasena));
    }

    static {
        reader = new BufferedReader(new InputStreamReader(System.in));
        usuariosDeSistema = new ArrayList();
        administradoresDeSistema = new ArrayList();
    }
}
