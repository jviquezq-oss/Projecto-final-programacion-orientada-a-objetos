package Controlador;

import Excepciones.CredencialesInvalidasException;
import Excepciones.ParametroInvalidoException;
import LogicaNegocio.*;
import Entidades.*;
import Menus.Menu;
import Menus.MenuAdministrador;
import Menus.MenuUsuario;
import util.ValidadorEntrada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Controlador {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private Controlador() {
    }
    public static void iniciarAplicacion() {
        boolean continuar = true;

        try {
            while (!GestorAdministrador.hayAdministradorRegistrado()) {
                registrarAdministradorInicial();
            }

            while (continuar) {
                int opcion = Menu.mostrarMenuInicio();

                switch (opcion) {
                    case 1:
                        iniciarSesion();
                        break;

                    case 2:
                        crearUsuario();
                        break;

                    case 0:
                        continuar = false;
                        System.out.println("Aplicación finalizada.");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void iniciarSesion() {
        System.out.println("\n===== INICIO DE SESIÓN =====");

        for (int intento = 1; intento <= 3; intento++) {
            try {
                System.out.print("Nombre de usuario: ");
                String nombreUsuario = reader.readLine().trim();

                if (nombreUsuario.isEmpty()) {
                    System.out.println("El nombre de usuario es obligatorio.");
                    continue;
                }

                System.out.print("Contraseña: ");
                String contrasena = reader.readLine();

                if (contrasena == null || contrasena.isEmpty()) {
                    System.out.println("La contraseña es obligatoria.");
                    continue;
                }

                Login.iniciarSesion(nombreUsuario, contrasena);

                System.out.println("Inicio de sesión exitoso.");

                Cuenta cuenta = SesionUsuario.getCuentaActiva();

                if (cuenta instanceof Usuario) {
                    mostrarListasUsuario((Usuario) cuenta);
                    menuUsuario();
                } else if (cuenta instanceof Administador) {
                    menuAdministrador();
                } else {
                    System.out.println("Tipo de cuenta no válido.");
                    SesionUsuario.cerrarSesion();
                }

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
    //Registrar Administrador
    private static void registrarAdministradorInicial() {
        try {
            System.out.println("\n===== REGISTRO DEL ADMINISTRADOR =====");

            System.out.print("Nombre de usuario: ");
            String nombreUsuario = validarParametro(reader.readLine(), "nombre de usuario");

            System.out.print("Correo electrónico: ");
            String correoElectronico = validarParametro(reader.readLine(), "correo electrónico");

            System.out.print("Contraseña: ");
            String contrasena = reader.readLine();

            GestorAdministrador.registrarAdministrador(correoElectronico, contrasena, nombreUsuario);

            System.out.println("Administrador registrado correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Debe registrar un administrador para poder continuar.");
        }
    }
    private static void menuAdministrador() {
        boolean continuar = true;

        while (continuar) {
            try {
                int opcion = MenuAdministrador.mostrarMenu();

                switch (opcion) {
                    case 1:
                        administrarCanciones();
                        break;

                    case 2:
                        administrarUsuarios();
                        break;

                    case 3:
                        administrarReproductor();
                        break;

                    case 4:
                        reproducirLista();
                        break;

                    case 5:
                        cambiarContrasena();
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
    // Operaciones de canciones
    private static void administrarCanciones() {
        boolean continuar = true;

        while (continuar) {
            try {
                int opcion = MenuAdministrador.menuCanciones();

                switch (opcion) {
                    case 1:
                        crearCancion();
                        break;

                    case 2:
                        buscarCancion();
                        break;

                    case 3:
                        modificarCancion();
                        break;

                    case 4:
                        eliminarCancion();
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
    private static void crearCancion() {
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
            double precio = ValidadorEntrada.validarDecimal(reader.readLine(), "precio");

            System.out.print("Duración en segundos: ");
            int duracion = ValidadorEntrada.validarEntero(reader.readLine(), "duración");

            Cancion cancion = GestorCancion.registrarCancion(nombre, genero, artista, compositor, fechaLanzamiento, album, caratula, precio, duracion);
            System.out.println("Canción registrada correctamente.");
            System.out.println("ID de la canción: " + cancion.getIdCancion());

        } catch (DateTimeParseException e) {
            System.out.println("La fecha de lanzamiento debe tener un formato válido (YYYY-MM-DD).");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void buscarCancion() {
        try {
            System.out.print("\nIngrese el nombre, género o artista de la canción: ");
            String criterio = validarParametro(reader.readLine(), "criterio de búsqueda");
            List<Cancion> canciones = GestorCancion.buscarGeneral(criterio);
            mostrarCanciones(canciones);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void modificarCancion() {
        try {
            List<Cancion> catalogo = GestorCancion.obtenerTodas();
            Cancion cancion = seleccionarCancion(catalogo);

            if (cancion == null) {
                return;
            }

            System.out.println("\n===== MODIFICAR CANCIÓN =====");
            System.out.println("Presione Enter para conservar el valor actual.");

            String nombre = obtenerNuevoParametro(cancion.getNombre(), "Nombre");
            String genero = obtenerNuevoParametro(cancion.getGenero(), "Género");
            String artista = obtenerNuevoParametro(cancion.getArtista(), "Artista");
            String compositor = obtenerNuevoParametro(cancion.getCompositor(), "Compositor");
            LocalDate fechaLanzamiento = obtenerNuevaFecha(cancion.getFechaLanzamiento(), "Fecha de lanzamiento");
            String album = obtenerNuevoParametro(cancion.getAlbum(), "Álbum");
            String caratula = obtenerNuevoParametro(cancion.getCaratula(), "Carátula");
            double precio = obtenerNuevoDouble(cancion.getPrecio(), "Precio");
            int duracion = obtenerNuevoEntero(cancion.getDuracion(), "Duración");
            GestorCancion.actualizarCancion(cancion.getIdCancion(), nombre, genero, artista, compositor, fechaLanzamiento, album, caratula, precio, duracion
            );

            System.out.println("Canción modificada correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void eliminarCancion() {
        try {
            List<Cancion> catalogo = GestorCancion.obtenerTodas();
            Cancion cancion = seleccionarCancion(catalogo);

            if (cancion == null) {
                return;
            }

            System.out.println("\n===== ELIMINAR CANCIÓN =====");
            System.out.println(cancion);

            if (!confirmarOperacion("¿Está seguro de que desea eliminar esta canción?")) {
                System.out.println("Operación cancelada.");
                return;
            }

            GestorCancion.eliminarCancion(cancion.getIdCancion());

            System.out.println("Canción eliminada correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Metodos auxiliares para canciones
    private static void mostrarCanciones(List<Cancion> canciones) {
        if (canciones.isEmpty()) {
            System.out.println("No se encontraron canciones.");
            return;
        }

        System.out.println("\n===== RESULTADOS DE LA BÚSQUEDA =====");

        for (Cancion cancion : canciones) {
            System.out.println(cancion);
            System.out.println("----------------------------------------");
        }
    }
    private static Cancion seleccionarCancion(List<Cancion> catalogo) {
        try {
            if (catalogo == null || catalogo.isEmpty()) {
                System.out.println("No hay canciones disponibles.");
                return null;
            }

            while (true) {
                int opcion = MenuUsuario.solicitarMetodoSeleccionCancion();
                switch (opcion) {
                    case 0:
                        return null;
                    case 1:
                        return MenuUsuario.solicitarCancion(catalogo);
                    case 2:
                        String criterio = MenuUsuario.solicitarBusquedaCancion();
                        List<Cancion> resultados = GestorCancion.buscarGeneral(catalogo, criterio);
                        if (resultados.isEmpty()) {
                            System.out.println("No se encontraron canciones.");
                            break;
                        }
                        return MenuUsuario.solicitarCancion(resultados);
                    default:
                        System.out.println("Seleccione una opción válida.");
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    //Final de operaciones de canciones
    //Operaciones administracion de usuarios
    private static void administrarUsuarios() {
        try {
            int opcion;

            do {
                opcion = MenuAdministrador.administrarUsuarios();

                switch (opcion) {
                    case 1:
                        mostrarUsuarios();
                        break;

                    case 2:
                        buscarUsuario();
                        break;

                    case 3:
                        modificarUsuario();
                        break;

                    case 4:
                        eliminarUsuario();
                        break;

                    case 5:
                        agregarSaldoUsuario();
                        break;

                    case 0:
                        break;

                    default:
                        System.out.println("Seleccione una opción válida.");
                        break;
                }

            } while (opcion != 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void crearUsuario() {
        try {
            System.out.println("\n===== CREAR USUARIO =====");

            System.out.print("Ingrese el nombre completo: ");
            String nombreCompleto = validarParametro(reader.readLine(), "nombre completo");

            System.out.print("Ingrese la fecha de nacimiento: ");
            LocalDate fechaDeNacimiento = ValidadorEntrada.validarFecha(reader.readLine(), "fecha de nacimiento");

            String nacionalidad = MenuUsuario.solicitarNacionalidad();

            if (nacionalidad == null) {
                System.out.println("Registro cancelado.");
                return;
            }

            System.out.print("Ingrese la cédula: ");
            String cedula = validarParametro(reader.readLine(), "cédula");

            System.out.print("Ingrese el avatar (Enter para usar el predeterminado): ");
            String avatar = reader.readLine();

            System.out.print("Ingrese el nombre de usuario: ");
            String nombreDeUsuario = validarParametro(reader.readLine(), "nombre de usuario");

            System.out.print("Ingrese el correo electrónico: ");
            String correoElectronico = validarParametro(reader.readLine(), "correo electrónico");

            System.out.print("Ingrese la contraseña: ");
            String contrasena = validarParametro(reader.readLine(), "contraseña");

            System.out.print("Confirme la contraseña: ");
            String confirmacion = reader.readLine();

            GestorUsuario.registrarUsuario(nombreCompleto, fechaDeNacimiento, nacionalidad, cedula, avatar, nombreDeUsuario, correoElectronico, contrasena, confirmacion);

            System.out.println("Usuario creado correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void cambiarContrasena() {
        try {
            Cuenta cuenta = SesionUsuario.getCuentaActiva();

            if (cuenta == null) {
                System.out.println("No existe una sesión activa.");
                return;
            }

            System.out.println("\n===== CAMBIAR CONTRASEÑA =====");
            System.out.print("Ingrese la contraseña actual: ");
            String contrasenaActual = reader.readLine();

            System.out.print("Ingrese la nueva contraseña: ");
            String nuevaContrasena = reader.readLine();

            System.out.print("Confirme la nueva contraseña: ");
            String confirmacion = reader.readLine();

            GestorCuenta.cambiarContrasena(
                    cuenta.getIdCuenta(),
                    contrasenaActual,
                    nuevaContrasena,
                    confirmacion
            );

            System.out.println("Contraseña actualizada correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void mostrarUsuarios() {
        try {
            List<Usuario> usuarios = GestorUsuario.obtenerTodos();

            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
                return;
            }

            System.out.println("\n===== USUARIOS REGISTRADOS =====");

            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static Usuario seleccionarUsuario() {
        try {
            int opcion = MenuAdministrador.seleccionarUsuario();

            return switch (opcion) {
                case 1 -> seleccionarUsuarioPorId();
                case 2 -> seleccionarUsuarioPorNombre();
                case 0 -> null;
                default -> {
                    System.out.println("Opción inválida.");
                    yield null;
                }
            };

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    private static Usuario seleccionarUsuarioPorId() {
        try {
            System.out.print("\nIngrese el ID del usuario: ");
            int idCuenta = ValidadorEntrada.validarEntero(reader.readLine(), "ID del usuario");

            return GestorUsuario.buscarPorId(idCuenta);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    private static Usuario seleccionarUsuarioPorNombre() {
        try {
            System.out.print("\nIngrese el nombre de usuario: ");
            String nombreUsuario = validarParametro(reader.readLine(), "nombre de usuario");

            return GestorUsuario.buscarPorNombreUsuario(nombreUsuario);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    private static void buscarUsuario() {
        Usuario usuario = seleccionarUsuario();
        if (usuario == null) {
            System.out.println("Usuario no encontrado");
            return;
        }
        System.out.println(usuario);
    }
    private static void modificarUsuario() {
        try {
            Usuario usuario = seleccionarUsuario();

            if (usuario == null) {
                return;
            }
            System.out.println("\n===== MODIFICAR USUARIO =====");
            String nombreCompleto = obtenerNuevoParametro(usuario.getNombrCompleto(), "Nombre completo");
            LocalDate fechaDeNacimiento = obtenerNuevaFecha(usuario.getFechaDeNacimiento(), "Fecha de nacimiento");
            String nacionalidad = obtenerNuevoParametro(usuario.getNacionalidad(), "Nacionalidad");
            String cedula = obtenerNuevoParametro(usuario.getCedula(), "Cédula");
            String avatar = obtenerNuevoParametro(usuario.getAvatar(), "Avatar");
            GestorUsuario.actualizarUsuario(usuario.getIdCuenta(), nombreCompleto, fechaDeNacimiento, nacionalidad, cedula, avatar);
            System.out.println("Usuario modificado correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void eliminarUsuario() {
        try {
            Usuario usuario = seleccionarUsuario();

            if (usuario == null) {
                return;
            }

            System.out.println("\n===== ELIMINAR USUARIO =====");
            System.out.println(usuario);

            if (!confirmarOperacion("¿Está seguro de que desea eliminar este usuario?")) {
                System.out.println("Operación cancelada.");
                return;
            }

            GestorUsuario.eliminarUsuario(usuario.getIdCuenta());

            System.out.println("Usuario eliminado correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void agregarSaldoUsuario() {
        try {
            Usuario usuario = seleccionarUsuario();

            if (usuario == null) {
                return;
            }

            System.out.println("\n===== AGREGAR SALDO =====");
            System.out.println("Usuario: " + usuario.getNombrCompleto());
            System.out.println("Saldo actual: $" + usuario.getSaldo());
            System.out.println("Ingrese la cantidad a agregar");
            double cantidad = ValidadorEntrada.validarDecimal(reader.readLine(), "Monto a agregar");

            if (cantidad <= 0) {
                System.out.println("El monto debe ser mayor que cero.");
                return;
            }

            GestorUsuario.agregarSaldo(usuario.getIdCuenta(), cantidad);

            System.out.println("Saldo agregado correctamente.");
            System.out.println("Nuevo saldo: $" + (usuario.getSaldo() + cantidad));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //Fin de operaciones de usuario
    //Aministracion del reproductor
    private static void administrarReproductor() {
        boolean continuar = true;

        while (continuar) {
            try {
                int opcion = Menu.seleccionarReproductor();

                switch (opcion) {
                    case 1:
                        agregarCancionACola();
                        break;

                    case 2:
                        reproducirCancion();
                        break;

                    case 3:
                        pausarCancion();
                        break;

                    case 4:
                        reanudarCancion();
                        break;

                    case 5:
                        avanzarCancion();
                        break;

                    case 6:
                        retrocederCancion();
                        break;

                    case 7:
                        siguienteCancion();
                        break;

                    case 8:
                        mostrarEstadoReproduccion();
                        break;

                    case 9:
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }

            } catch (IOException e) {
                System.out.println("Error al leer la opción. Intente nuevamente.");
            }
        }
    }
    //Operaciones del reproductor
    private static void agregarCancionACola() {
        try {
            if (!SesionUsuario.hayCuentaActiva()) {
                System.out.println("No hay una sesión activa.");
                return;
            }

            Cuenta cuenta = SesionUsuario.getCuentaActiva();
            List<Cancion> canciones;

            if (cuenta instanceof Administador) {
                canciones = GestorCancion.obtenerTodas();
            } else if (cuenta instanceof Usuario) {
                canciones = GestorUsuario.obtenerCancionesCompradas(cuenta.getIdCuenta());
            } else {
                System.out.println("Tipo de cuenta no válido.");
                return;
            }

            Cancion cancion = seleccionarCancion(canciones);

            if (cancion == null) {
                return;
            }

            GestorColaDeCanciones.agregarCancionACola(SesionUsuario.getReproductor().getCola(), cancion);
            System.out.println("La canción fue agregada a la cola de reproducción.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void reproducirCancion() {
        try {
            SesionUsuario.getReproductor().reproducir();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void pausarCancion() {
        try {
            SesionUsuario.getReproductor().pausar();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void reanudarCancion() {
        try {
            SesionUsuario.getReproductor().reanudar();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void avanzarCancion() {
        try {
            SesionUsuario.getReproductor().avanzar();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void retrocederCancion() {
        try {
            SesionUsuario.getReproductor().retroceder();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void siguienteCancion() {
        try {
            SesionUsuario.getReproductor().siguiente();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void mostrarEstadoReproduccion() {
        try {
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //Fin de opeaciones de reproductor
    //Inicio de operaciones de usuario

    //Visualizar catalogo general
    private static void mostrarColeccionComprada() {
        try {
            Usuario usuario = obtenerUsuarioActivo();

            if (usuario == null) {
                return;
            }

            List<Cancion> canciones = GestorUsuario.obtenerCancionesCompradas(usuario.getIdCuenta());

            if (canciones.isEmpty()) {
                System.out.println("\nNo tiene canciones compradas.");
                return;
            }

            MenuUsuario.mostrarColeccionCanciones(canciones);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //Inicio de metodos de compra  de canciones para usuarios
    private static void comprarCancion() {
        try {
            Usuario usuario = obtenerUsuarioActivo();

            if (usuario == null) {
                return;
            }

            List<Cancion> catalogo = GestorCancion.obtenerTodas();

            if (catalogo == null || catalogo.isEmpty()) {
                System.out.println("No hay canciones disponibles para comprar.");
                return;
            }

            Cancion cancion = seleccionarCancion(catalogo);

            if (cancion == null) {
                return;
            }

            previsualizarCancion(cancion);

            int opcion = MenuUsuario.solicitarCompraDespuesDePrevisualizar();

            if (opcion == 0) {
                System.out.println("Compra cancelada.");
                return;
            }

            if (opcion != 1) {
                System.out.println("Seleccione una opción válida.");
                return;
            }

            GestorCompra.comprarCancion(usuario.getIdCuenta(), cancion.getIdCancion());
            System.out.println("\nLa canción \"" + cancion.getNombre() + "\" fue comprada correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // Crear listas de reproduccion
    private static void crearListaReproduccion() {
        try {
            Usuario usuario = obtenerUsuarioActivo();

            if (usuario == null) {
                return;
            }

            System.out.println("\n===== CREAR LISTA DE REPRODUCCIÓN =====");
            System.out.print("Ingrese el nombre de la lista: ");
            String nombre = validarParametro(reader.readLine(), "nombre de la lista");

            ListaRepoduccion lista = GestorListaRepoduccion.registrarLista(usuario, nombre, LocalDate.now());

            System.out.println("Lista de reproducción creada correctamente.");
            System.out.println("ID de la lista: " + lista.getIdLista());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // Busqueda de canciones en el catalogo general del usuario
    private static void buscarCancionEnColeccion() {
        try {
            Usuario usuario = obtenerUsuarioActivo();

            if (usuario == null) {
                return;
            }

            List<Cancion> canciones = GestorUsuario.obtenerCancionesCompradas(usuario.getIdCuenta());

            if (canciones == null || canciones.isEmpty()) {
                System.out.println("No tiene canciones compradas.");
                return;
            }

            String criterio = MenuUsuario.solicitarBusquedaCancion();
            List<Cancion> resultados = GestorCancion.buscarGeneral(canciones, criterio);

            if (resultados.isEmpty()) {
                System.out.println("No se encontraron canciones en su colección con este criterio.");
                return;
            }

            mostrarCanciones(resultados);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //Mostrar listas al entrar
    private static void mostrarListasUsuario(Usuario usuario) {
        try {
            List<ListaRepoduccion> listas = GestorListaRepoduccion.obtenerPorUsuario(usuario);
            MenuUsuario.mostrarListasReproduccion(listas);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // Calificacion de canciones
    private static void calificarCancion() {
        try {
            Usuario usuario = obtenerUsuarioActivo();

            if (usuario == null) {
                return;
            }

            List<Cancion> cancionesCompradas = GestorUsuario.obtenerCancionesCompradas(usuario.getIdCuenta());
            Cancion cancion = seleccionarCancion(cancionesCompradas);

            if (cancion == null) {
                return;
            }

            System.out.print("Ingrese la calificación de la canción (0.0 - 5.0): ");
            double calificacion = ValidadorEntrada.validarDecimal(reader.readLine(), "calificación");

            if (calificacion < 0.0 || calificacion > 5.0) {
                System.out.println("La calificación debe estar entre 0.0 y 5.0.");
                return;
            }

            GestorCancion.agregarCalificacion(cancion.getIdCancion(), calificacion);
            System.out.println("La canción fue calificada correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // agregacion de cancion a una playlist
    private static void agregarCancionALista() {
        try {
            Usuario usuario = obtenerUsuarioActivo();

            if (usuario == null) {
                return;
            }

            ListaRepoduccion lista = seleccionarListaReproduccion(usuario);

            if (lista == null) {
                return;
            }

            boolean continuar = true;

            while (continuar) {
                List<Cancion> cancionesCompradas = GestorUsuario.obtenerCancionesCompradas(usuario.getIdCuenta());
                Cancion cancion = seleccionarCancion(cancionesCompradas);

                if (cancion == null) {
                    return;
                }

                GestorListaCancion.agregarCancion(lista.getIdLista(), cancion.getIdCancion(), usuario);
                GestorListaRepoduccion.recalcularCalificacion(lista.getIdLista(), usuario);

                System.out.println("La canción fue agregada correctamente a la lista de reproducción.");
                continuar = MenuUsuario.solicitarOtraCancion();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // Elminar canciones de lista
    private static void eliminarCancionDeLista() {
        try {
            Usuario usuario = obtenerUsuarioActivo();

            if (usuario == null) {
                return;
            }

            ListaRepoduccion lista = seleccionarListaReproduccion(usuario);

            if (lista == null) {
                return;
            }

            boolean continuar = true;

            while (continuar) {
                List<Cancion> canciones = GestorListaCancion.obtenerCanciones(lista.getIdLista(), usuario);
                Cancion cancion = MenuUsuario.solicitarCancionDeLista(canciones);

                if (cancion == null) {
                    return;
                }

                if (!confirmarOperacion("¿Está seguro de que desea eliminar esta canción de la lista?")) {
                    System.out.println("Operación cancelada.");
                    continuar = MenuUsuario.solicitarOtraCancion();
                    continue;
                }

                GestorListaCancion.eliminarCancion(lista.getIdLista(), cancion.getIdCancion(), usuario);
                GestorListaRepoduccion.recalcularCalificacion(lista.getIdLista(), usuario);

                System.out.println("La canción fue eliminada correctamente de la lista de reproducción.");

                continuar = MenuUsuario.solicitarOtraCancion();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //Eliminar una lista de reproduccion
    private static void eliminarListaReproduccion() {
        try {
            Usuario usuario = obtenerUsuarioActivo();

            if (usuario == null) {
                return;
            }

            ListaRepoduccion lista = seleccionarListaReproduccion(usuario);

            if (lista == null) {
                return;
            }

            System.out.println("\n===== ELIMINAR LISTA DE REPRODUCCIÓN =====");
            System.out.println("Lista: " + lista.getNombre());

            if (!confirmarOperacion("¿Está seguro de que desea eliminar esta lista?")) {
                System.out.println("Operación cancelada.");
                return;
            }

            GestorListaRepoduccion.eliminarLista(lista.getIdLista(), usuario);

            System.out.println("La lista de reproducción fue eliminada correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // Seleciconar lista de produccion
    private static ListaRepoduccion seleccionarListaReproduccion(Usuario usuario) {
        try {
            int opcion = MenuUsuario.seleccionarListaReproduccion();

            switch (opcion) {
                case 1:
                    List<ListaRepoduccion> listas = GestorListaRepoduccion.obtenerPorUsuario(usuario);
                    return seleccionarListaDeResultados(listas);

                case 2:
                    System.out.print("Ingrese el nombre de la lista de reproducción: ");
                    String nombre = validarParametro(reader.readLine(), "nombre de la lista");
                    List<ListaRepoduccion> resultados = GestorListaRepoduccion.buscarPorNombre(nombre, usuario);

                    if (resultados.isEmpty()) {
                        System.out.println("No se encontraron listas de reproducción.");
                        return null;
                    }

                    return seleccionarListaDeResultados(resultados);

                case 0:
                    return null;

                default:
                    System.out.println("Seleccione una opción válida.");
                    return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    //Seleccionar lista de reproduccion administrador
    private static ListaRepoduccion seleccionarListaReproduccionAdministrador() {
        try {
            int opcion = MenuUsuario.seleccionarListaReproduccion();

            switch (opcion) {
                case 1:
                    List<ListaRepoduccion> listas = GestorListaRepoduccion.obtenerTodas();
                    return seleccionarListaDeResultados(listas);

                case 2:
                    System.out.print("Ingrese el nombre de la lista de reproducción: ");
                    String nombre = validarParametro(reader.readLine(), "nombre de la lista");
                    List<ListaRepoduccion> resultados = GestorListaRepoduccion.buscarPorNombre(nombre);

                    if (resultados.isEmpty()) {
                        System.out.println("No se encontraron listas de reproducción.");
                        return null;
                    }

                    return seleccionarListaDeResultados(resultados);

                case 0:
                    return null;

                default:
                    System.out.println("Seleccione una opción válida.");
                    return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    //Reproduccion de listas para el administrador

    private static void reproducirLista() {
        try {
            if (!SesionUsuario.hayCuentaActiva()) {
                System.out.println("No existe una sesión activa.");
                return;
            }

            Cuenta cuenta = SesionUsuario.getCuentaActiva();
            ListaRepoduccion lista;

            if (cuenta instanceof Administador) {
                lista = seleccionarListaReproduccionAdministrador();
            } else if (cuenta instanceof Usuario) {
                lista = seleccionarListaReproduccion((Usuario) cuenta);
            } else {
                System.out.println("Tipo de cuenta no válido.");
                return;
            }

            if (lista == null) {
                return;
            }

            GestorColaDeCanciones.reproducirPlaylist(SesionUsuario.getReproductor().getCola(), lista, lista.getPropietario());
            System.out.println("\nLista \"" + lista.getNombre() + "\" cargada correctamente.");
            SesionUsuario.getReproductor().reproducir();
            administrarReproductor();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //Fin de listas de reproduccion para el administrado
    // Metodos de menus de usuario
    private static void menuUsuario() {
        boolean continuar = true;

        while (continuar) {
            try {
                int opcion = MenuUsuario.mostrarMenuPrincipal();

                switch (opcion) {
                    case 1:
                        mostrarColeccionComprada();
                        break;

                    case 2:
                        buscarCancionEnColeccion();
                        break;

                    case 3:
                        comprarCancion();
                        break;

                    case 4:
                        calificarCancion();
                        break;

                    case 5:
                        administrarListasUsuario();
                        break;

                    case 6:
                        administrarReproductor();
                        break;

                    case 7:
                        reproducirLista();
                        break;

                    case 8:
                        mostrarTop3();
                        break;

                    case 9:
                        cambiarContrasena();
                        break;

                    case 10:
                        recargarSaldo();
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
    //Recargar Saldo
    private static void recargarSaldo() {
        try {
            Usuario usuario = obtenerUsuarioActivo();

            if (usuario == null) {
                return;
            }

            System.out.println("\n===== RECARGAR SALDO =====");
            System.out.printf("Saldo actual: $%.2f%n", usuario.getSaldo());

            System.out.print("Ingrese el monto a recargar: ");
            double monto = ValidadorEntrada.validarDecimal(
                    reader.readLine(),
                    "monto"
            );

            if (monto <= 0) {
                System.out.println("El monto debe ser mayor que cero.");
                return;
            }

            GestorUsuario.agregarSaldo(usuario.getIdCuenta(), monto);

            System.out.printf("Recarga realizada correctamente. Nuevo saldo: $%.2f%n",
                    usuario.getSaldo() + monto);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //Metodos de estadistica
    private static void mostrarTop3() {
        try {
            List<Cancion> mejoresCalificadas = GestorEstadisticas.obtenerTop3MejorCalificadas();
            List<Cancion> masCompradas = GestorEstadisticas.obtenerTop3MasCompradas();
            List<Cancion> masIncluidas = GestorEstadisticas.obtenerTop3MasIncluidasEnListas();

            MenuUsuario.mostrarTop3(mejoresCalificadas, masCompradas, masIncluidas);

            String respuesta = MenuUsuario.solicitarReproduccionTop3();

            if (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N")) {
                System.out.println("Seleccione S o N.");
                return;
            }

            if (respuesta.equalsIgnoreCase("N")) {
                return;
            }

            int opcion = MenuUsuario.seleccionarTop3();
            List<Cancion> cancionesSeleccionadas;

            switch (opcion) {
                case 1:
                    cancionesSeleccionadas = mejoresCalificadas;
                    break;

                case 2:
                    cancionesSeleccionadas = masCompradas;
                    break;

                case 3:
                    cancionesSeleccionadas = masIncluidas;
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Seleccione una opción válida.");
                    return;
            }

            GestorColaDeCanciones.cargarCanciones(SesionUsuario.getReproductor().getCola(), cancionesSeleccionadas);

            System.out.println("\nTop 3 cargado correctamente.");
            SesionUsuario.getReproductor().reproducir();
            administrarReproductor();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // Adminsitra menu listas
    private static void administrarListasUsuario() {
        boolean continuar = true;

        while (continuar) {
            int opcion = MenuUsuario.menuListasReproduccion();
            switch (opcion) {
                case 1:
                    mostrarListasUsuario(obtenerUsuarioActivo());
                    break;

                case 2:
                    crearListaReproduccion();
                    break;

                case 3:
                    agregarCancionALista();
                    break;

                case 4:
                    eliminarCancionDeLista();
                    break;

                case 5:
                    eliminarListaReproduccion();
                    break;

                case 0:
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        }
    }
    //Metodos auxiliares para la reproduccion de listas
    //Operaciones auxiliares generales
     public static String validarParametro(String parametro, String nombreParametro) throws ParametroInvalidoException {
        if (parametro == null || parametro.isBlank()) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " no puede estar vacío.");
        }
        return parametro;
    }

    private static String obtenerNuevoParametro(String valorActual, String nombreParametro) throws IOException {
        System.out.print(nombreParametro + " actual: " + valorActual + ". Nuevo " + nombreParametro + " (Enter para conservar): ");
        String nuevoValor = reader.readLine();
        if (nuevoValor.isBlank()) {
            return valorActual;
        }
        return nuevoValor;
    }
    private static int obtenerNuevoEntero(int valorActual, String nombreParametro) throws IOException {
        while (true) {
            System.out.print(nombreParametro + " actual: " + valorActual + ". Nuevo " + nombreParametro + " (Enter para conservar): ");
            String entrada = reader.readLine();

            if (entrada.isBlank()) {
                return valorActual;
            }
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("El valor debe ser un número entero.");
            }
        }
    }
    private static double obtenerNuevoDouble(double valorActual, String nombreParametro) throws IOException {
        while (true) {
            System.out.print(nombreParametro + " actual: " + valorActual + ". Nuevo " + nombreParametro + " (Enter para conservar): ");
            String entrada = reader.readLine();

            if (entrada.isBlank()) {
                return valorActual;
            }

            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("El valor debe ser un número válido.");
            }
        }
    }
    private static LocalDate obtenerNuevaFecha(LocalDate valorActual, String nombreParametro) throws IOException {
        while (true) {
            System.out.print(nombreParametro + " actual: " + valorActual + ". Nueva " + nombreParametro + " (YYYY-MM-DD, Enter para conservar): ");
            String entrada = reader.readLine();

            if (entrada.isBlank()) {
                return valorActual;
            }

            try {
                return LocalDate.parse(entrada);
            } catch (DateTimeParseException e) {
                System.out.println("El valor debe tener el formato YYYY-MM-DD.");
            }
        }
    }
    private static Usuario obtenerUsuarioActivo() {
        if (!SesionUsuario.hayCuentaActiva()) {
            System.out.println("No existe una sesión activa.");
            return null;
        }

        Cuenta cuenta = SesionUsuario.getCuentaActiva();

        if (!(cuenta instanceof Usuario)) {
            System.out.println("Esta operación solo está disponible para usuarios.");
            return null;
        }

        return (Usuario) cuenta;
    }
    //Confirmacion de operaciones
    private static boolean confirmarOperacion(String mensaje) throws IOException {
        while (true) {
            System.out.print(mensaje + " (S/N): ");
            String respuesta = reader.readLine();

            if (respuesta == null) {
                return false;
            }

            if (respuesta.equalsIgnoreCase("S")) {
                return true;
            }

            if (respuesta.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Ingrese S o N.");
        }
    }
    //Auxiliar para listas de reproduccion
    private static ListaRepoduccion seleccionarListaDeResultados(List<ListaRepoduccion> listas) {
        if (listas == null || listas.isEmpty()) {
            System.out.println("No hay listas de reproducción disponibles.");
            return null;
        }

        return MenuUsuario.solicitarListaReproduccion(listas);
    }
    //Auxiliares compra
    private static void previsualizarCancion(Cancion cancion) {

        System.out.println();
        System.out.println("===== PREVISUALIZACIÓN =====");
        System.out.println("Canción: " + cancion.getNombre());
        System.out.println("Artista: " + cancion.getArtista());
        System.out.println("Estado: PREVISUALIZANDO");
        System.out.println("Duración: 30 segundos");
        System.out.println("La previsualización no requiere la compra de la canción.");
    }


}