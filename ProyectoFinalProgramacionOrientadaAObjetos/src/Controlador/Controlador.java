package Controlador;

import Excepciones.CredencialesInvalidasException;
import Excepciones.ParametroInvalidoException;
import LogicaNegocio.*;
import Entidades.*;
import Menus.Menu;
import Menus.MenuAdministrador;
import Menus.MenuUsuario;

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
                        administrarUsuarios();
                        break;

                    case 3:
                        administrarReproductor();
                        break;

                    case 4:
                        reproducirLista();
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
                        buscarCancion();
                        break;

                    case 3:
                        modificarCancion(SesionUsuario.getCuentaActiva());
                        break;

                    case 4:
                        eliminarCancion(SesionUsuario.getCuentaActiva());
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
    public static void buscarCancion() {
        try {
            System.out.print("\nIngrese el nombre, género o artista de la canción: ");
            String criterio = validarParametro(reader.readLine(), "criterio de búsqueda");
            List<Cancion> canciones = GestorCancion.buscarGeneral(criterio);
            mostrarCanciones(canciones);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void modificarCancion(Cuenta cuenta) {
        try {
            Cancion cancion = seleccionarCancion(cuenta);

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
            GestorCancion.actualizarCancion(cancion.getIdCancion(), nombre, genero, artista, compositor, fechaLanzamiento, album, caratula, precio, duracion);
            System.out.println("Canción modificada correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void eliminarCancion(Cuenta cuenta) {
        try {
            Cancion cancion = seleccionarCancion(cuenta);

            if (cancion == null) {
                return;
            }

            System.out.println("\n===== ELIMINAR CANCIÓN =====");
            System.out.println(cancion);

            System.out.print("¿Está seguro de que desea eliminar esta canción? (S/N): ");
            String confirmacion = reader.readLine();

            if (!confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Operación cancelada.");
                return;
            }

            GestorCancion.eliminarCancion(cancion.getIdCancion());

            System.out.println("Canción eliminada correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static Cancion seleccionarCancion(Cuenta cuenta) {
        try {
            List<Cancion> catalogo;

            if (cuenta instanceof Administador) {
                catalogo = GestorCancion.obtenerTodas();
            } else if (cuenta instanceof Usuario) {
                catalogo = GestorUsuario.obtenerCancionesCompradas(SesionUsuario.getCuentaActiva().getIdCuenta());
            } else {
                System.out.println("Tipo de usuario no válido.");
                return null;
            }

            int opcion = Menu.seleccionarCancion();

            switch (opcion) {
                case 1:
                    return seleccionarCancionPorNombre(catalogo);

                case 2:
                    return seleccionarCancionPorId(catalogo);

                case 3:
                    return seleccionarCancionDelCatalogo(catalogo);

                case 0:
                    return null;

                default:
                    System.out.println("Opción inválida.");
                    return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
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
    private static Cancion seleccionarCancionPorNombre(List<Cancion> canciones) {
        try {
            System.out.print("\nIngrese el nombre de la canción: ");
            String nombre = validarParametro(reader.readLine(), "nombre");

            List<Cancion> resultados = new ArrayList<>();

            for (Cancion cancion : canciones) {
                if (cancion.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    resultados.add(cancion);
                }
            }

            if (resultados.isEmpty()) {
                System.out.println("No se encontraron canciones.");
                return null;
            }

            mostrarCanciones(resultados);

            System.out.print("Seleccione el número de la canción: ");
            int seleccion = validarEntero(reader.readLine(), "selección");

            if (seleccion < 1 || seleccion > resultados.size()) {
                System.out.println("Selección inválida.");
                return null;
            }

            return resultados.get(seleccion - 1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    private static Cancion seleccionarCancionPorId(List<Cancion> canciones) {
        try {
            System.out.print("\nIngrese el ID de la canción: ");
            int idCancion = validarEntero(reader.readLine(), "ID de la canción");

            for (Cancion cancion : canciones) {
                if (cancion.getIdCancion() == idCancion) {
                    return cancion;
                }
            }

            System.out.println("La canción no se encuentra disponible en su catálogo.");
            return null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    private static Cancion seleccionarCancionDelCatalogo(List<Cancion> canciones) {
        try {
            if (canciones.isEmpty()) {
                System.out.println("No hay canciones disponibles.");
                return null;
            }

            mostrarCanciones(canciones);

            System.out.print("Seleccione el número de la canción: ");
            int seleccion = validarEntero(reader.readLine(), "selección");

            if (seleccion < 1 || seleccion > canciones.size()) {
                System.out.println("Selección inválida.");
                return null;
            }

            return canciones.get(seleccion - 1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    //Final de operaciones de canciones
    //Operaciones administracion de usuarios
    public static void administrarUsuarios() {
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

                    case 6:
                        crearUsuario();
                        break;

                    case 7:
                        cambiarContrasena();
                        break;

                    case 8:
                        break;

                    default:
                        System.out.println("Seleccione una opción válida.");
                }

            } while (opcion != 6);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void crearUsuario() {
        try {
            System.out.println("\n===== CREAR USUARIO =====");

            System.out.print("Ingrese el nombre completo: ");
            String nombreCompleto = validarParametro(reader.readLine(), "nombre completo");

            System.out.print("Ingrese la fecha de nacimiento: ");
            LocalDate fechaDeNacimiento = validarFecha(reader.readLine(), "fecha de nacimiento");

            System.out.print("Ingrese la nacionalidad: ");
            String nacionalidad = validarParametro(reader.readLine(), "nacionalidad");

            System.out.print("Ingrese la cédula: ");
            String cedula = validarParametro(reader.readLine(), "cédula");

            System.out.print("Ingrese el avatar: ");
            String avatar = validarParametro(reader.readLine(), "avatar");

            System.out.print("Ingrese el nombre de usuario: ");
            String nombreDeUsuario = validarParametro(reader.readLine(), "nombre de usuario");

            System.out.print("Ingrese el correo electrónico: ");
            String correoElectronico = validarParametro(reader.readLine(), "correo electrónico");

            System.out.print("Ingrese la contraseña: ");
            String contrasena = validarParametro(reader.readLine(), "contraseña");

            GestorUsuario.registrarUsuario(nombreCompleto, fechaDeNacimiento, nacionalidad, cedula, avatar, nombreDeUsuario, correoElectronico, contrasena);

            System.out.println("Usuario creado correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void cambiarContrasena() {
        try {
            Usuario usuario = seleccionarUsuario();

            if (usuario == null) {
                return;
            }

            System.out.println("\n===== CAMBIAR CONTRASEÑA =====");

            System.out.print("Ingrese la nueva contraseña: ");
            String contrasena = validarParametro(reader.readLine(), "contraseña");

            GestorCuenta.actualizarContrasena(usuario.getIdCuenta(), contrasena);

            System.out.println("Contraseña actualizada correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void mostrarUsuarios() {
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
    public static Usuario seleccionarUsuario() {
        try {
            int opcion = MenuAdministrador.seleccionarUsuario();

            switch (opcion) {
                case 1:
                    return seleccionarUsuarioPorId();

                case 2:
                    return seleccionarUsuarioPorNombre();

                case 0:
                    return null;

                default:
                    System.out.println("Opción inválida.");
                    return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    private static Usuario seleccionarUsuarioPorId() {
        try {
            System.out.print("\nIngrese el ID del usuario: ");
            int idCuenta = validarEntero(reader.readLine(), "ID del usuario");

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
    public static void  buscarUsuario(){
        Usuario usuario = seleccionarUsuario();
        if(usuario == null){
            System.out.println("Usuario no encontrado");
        }
        System.out.println(usuario);
    }
    public static void modificarUsuario() {
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
    public static void eliminarUsuario() {
        try {
            Usuario usuario = seleccionarUsuario();

            if (usuario == null) {
                return;
            }

            System.out.println("\n===== ELIMINAR USUARIO =====");
            System.out.println(usuario);

            System.out.print("¿Está seguro de que desea eliminar este usuario? (S/N): ");
            String confirmacion = reader.readLine();

            if (!confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Operación cancelada.");
                return;
            }

            GestorUsuario.eliminarUsuario(usuario.getIdCuenta());

            System.out.println("Usuario eliminado correctamente.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void agregarSaldoUsuario() {
        try {
            Usuario usuario = seleccionarUsuario();

            if (usuario == null) {
                return;
            }

            System.out.println("\n===== AGREGAR SALDO =====");
            System.out.println("Usuario: " + usuario.getNombrCompleto());
            System.out.println("Saldo actual: $" + usuario.getSaldo());
            System.out.println("Ingrese la cantidad a agregar");
            double cantidad = validarDecimal(reader.readLine(), "Monto a agregar");

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
    public static void administrarReproductor() {
        int opcion;

        do {
            try {
                opcion = Menu.seleccionarReproductor();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 9);
    }
    //Operaciones del reproductor
    public static void agregarCancionACola() {
        try {
            Cuenta cuenta = SesionUsuario.getCuentaActiva();

            if (cuenta == null) {
                System.out.println("No hay una sesión activa.");
                return;
            }

            Cancion cancion = seleccionarCancion(cuenta);

            if (cancion == null) {
                return;
            }

            SesionUsuario.getReproductor().agregarCancion(cancion);
            System.out.println("La canción fue agregada a la cola de reproducción.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void reproducirCancion() {
        try {
            SesionUsuario.getReproductor().reproducir();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void pausarCancion() {
        try {
            SesionUsuario.getReproductor().pausar();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void reanudarCancion() {
        try {
            SesionUsuario.getReproductor().reanudar();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void avanzarCancion() {
        try {
            SesionUsuario.getReproductor().avanzar();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void retrocederCancion() {
        try {
            SesionUsuario.getReproductor().retroceder();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void siguienteCancion() {
        try {
            SesionUsuario.getReproductor().siguiente();
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void mostrarEstadoReproduccion() {
        try {
            System.out.println(SesionUsuario.getReproductor().obtenerEstadoReproduccion());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //Fin de opeaciones de reproductor
    //Inicio de operaciones de usuario

    //Visualizar catalogo general
    public static void mostrarColeccionComprada() {
        try {

            if (!SesionUsuario.hayCuentaActiva()) {
                System.out.println("No existe una sesión activa.");
                return;
            }

            Cuenta cuenta = SesionUsuario.getCuentaActiva();

            if (!(cuenta instanceof Usuario)) {
                System.out.println(
                        "Esta operación solo está disponible para usuarios."
                );
                return;
            }

            List<Cancion> canciones = GestorUsuario.obtenerCancionesCompradas(cuenta.getIdCuenta());

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
    private static Cancion seleccionarCancionParaCompra(List<Cancion> catalogo) {
    try {
        while (true) {

            int opcion = MenuUsuario.solicitarMetodoSeleccionCancion();

            switch (opcion) {

                case 0:
                    return null;

                case 1:
                    return MenuUsuario.solicitarCancion(catalogo);

                case 2:
                    try {
                        String criterio = MenuUsuario.solicitarBusquedaCancion();
                        List<Cancion> resultados = GestorCancion.buscarGeneral(criterio);

                        if (resultados.isEmpty()) {
                            System.out.println("No se encontraron canciones.");break;
                        }

                        return MenuUsuario.solicitarCancion(resultados);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                    default:
                    System.out.println("Seleccione una opción válida.");
                }
            }
        }catch (IOException e){
            System.out.println("Error al leer datos del usuario");
            return null;
        }catch (ParametroInvalidoException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void comprarCancion() {
        try {
            if (!SesionUsuario.hayCuentaActiva()) {
                System.out.println("No existe una sesión activa.");
                return;
            }

            Cuenta cuenta = SesionUsuario.getCuentaActiva();

            if (!(cuenta instanceof Usuario)) {
                System.out.println(
                        "Esta operación solo está disponible para usuarios."
                );
                return;
            }

            List<Cancion> catalogo = GestorCancion.obtenerTodas();

            if (catalogo == null || catalogo.isEmpty()) {
                System.out.println(
                        "No hay canciones disponibles para comprar."
                );
                return;
            }

            Cancion cancion = seleccionarCancionParaCompra(catalogo);

            if (cancion == null) {
                return;
            }

            GestorCompra.comprarCancion(cuenta.getIdCuenta(), cancion.getIdCancion()
            );
            System.out.println("\nLa canción \"" + cancion.getNombre() + "\" fue comprada correctamente."
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Reproduccion de listas para el administrador

    public static void reproducirLista() {
        try {

            if (!SesionUsuario.hayCuentaActiva()) {
                System.out.println("No existe una sesión activa.");
                return;
            }

            Cuenta cuenta = SesionUsuario.getCuentaActiva();

            List<ListaRepoduccion> listasDisponibles = new ArrayList<>();

            if (cuenta instanceof Administador) {

                listasDisponibles = GestorListaRepoduccion.obtenerTodas();

            } else {

                // Aquí utilizaremos el método real
                // para obtener las listas del usuario.

            }

            ListaRepoduccion lista = Menu.solicitarListaReproduccion(listasDisponibles);

            if (lista == null) {
                return;
            }

            cargarListaEnReproductor(lista);

            administrarReproductor();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //Fin de listas de reproduccion para el administrado
    //Metodos auxiliares para la reproduccion de listas
    private static void cargarListaEnReproductor(
            ListaRepoduccion lista) throws Exception {

        if (lista == null) {
            throw new Exception("La lista de reproducción no es válida.");
        }

        List<Cancion> canciones = GestorListaCancion.obtenerCanciones(lista.getIdLista());

        if (canciones == null || canciones.isEmpty()) {
            throw new Exception("La lista de reproducción no contiene canciones.");
        }
        Reproductor reproductor = SesionUsuario.getReproductor();
        reproductor.limpiarCola();
        for (Cancion cancion : canciones) {
            reproductor.agregarCancion(cancion);
        }
        System.out.println("\nLista \"" + lista.getNombre() + "\" cargada correctamente.");
        reproductor.reproducir();
    }
    //Operaciones auxiliares generales
     public static String validarParametro(String parametro, String nombreParametro) throws ParametroInvalidoException {
        if (parametro == null || parametro.isBlank()) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " no puede estar vacío.");
        }
        return parametro;
    }
    public static int validarEntero(String parametro, String nombreParametro) throws ParametroInvalidoException {
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
    private static LocalDate validarFecha (String parametro, String nombreParametro) throws ParametroInvalidoException {
        if (parametro == null || parametro.isBlank()) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " no puede estar vacío.");
        }

        try {
            return LocalDate.parse(parametro);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El campo " + nombreParametro + " debe ser un número válido.");
        }
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

}