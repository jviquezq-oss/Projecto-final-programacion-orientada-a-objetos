package LogicaNegocio;

import Entidades.Cancion;
import Entidades.CatalogoGeneral;
import Excepciones.CancionNoEncontradaException;
import Excepciones.SaldoInsuficienteException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import util.BuscadorDeCanciones;

public class AdministadorCatalogo {
    private static final BufferedReader reader;

    public AdministadorCatalogo() {
    }

    public static void comprarCancion() throws IOException {
        int seleccionDeMenu = 0;
        if (CatalogoGeneral.getCatalogoGlobal().isEmpty()) {
            System.out.println("No hay canciones disponibles para la compra");
        } else {
            System.out.println("=====Compra de canciones=====\n1.Buscar una cancion para comprar\n2.Seleccionar del catalogo de canciones disponibles\n3.Volver al menu principal");

            while(true) {
                try {
                    seleccionDeMenu = Integer.parseInt(reader.readLine());
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (NumberFormatException var4) {
                    System.out.println("Ingrese un numero valdio");
                }
            }

            Cancion cancionACompar;
            switch (seleccionDeMenu) {
                case 1:
                    try {
                        cancionACompar = buscarCancion(CatalogoGeneral.getCatalogoGlobal(), true);
                        if (cancionACompar != null) {
                            SesionUsuario.getUsuarioActivo().comprarCancion(cancionACompar);
                        }
                    } catch (CancionNoEncontradaException | SaldoInsuficienteException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        cancionACompar = seleccionarCancion(CatalogoGeneral.getCatalogoGlobal(), true);
                        SesionUsuario.getUsuarioActivo().comprarCancion(cancionACompar);
                    } catch (SaldoInsuficienteException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }

        }
    }

    public static Cancion opcionesDeCancio(List<Cancion> catalogoDisponible) {
        int seleccionDeMenu = 0;
        Cancion cancionACompar = null;
        if (catalogoDisponible.isEmpty()) {
            System.out.println("No hay canciones disponibles");
            return null;
        } else {
            System.out.println("=====Opciones de cancion=====\n1.Buscar una cancion \n2.Seleccionar del catalogo de canciones disponibles\n3.Volver al menu principal");

            while(true) {
                try {
                    seleccionDeMenu = Integer.parseInt(reader.readLine());
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (NumberFormatException var6) {
                    System.out.println("Ingrese un numero valdio");
                }
            }

            switch (seleccionDeMenu) {
                case 1:
                    try {
                        cancionACompar = buscarCancion(catalogoDisponible, false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (CancionNoEncontradaException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }

                    if (cancionACompar != null) {
                        return cancionACompar;
                    }

                    return null;
                case 2:
                    cancionACompar = seleccionarCancion(catalogoDisponible, false);
                    return cancionACompar;
                default:
                    throw new IllegalStateException("Unexpected value: " + seleccionDeMenu);
            }
        }
    }

    public static Cancion buscarCancion(List<Cancion> catalogoDeBusqueda, boolean mostrarCompleto) throws IOException, CancionNoEncontradaException {
        System.out.println("Ingrese la cancion que desea buscar (Nombre, Artista, Genero)");
        String parametroBusqueda = reader.readLine();
        List<Cancion> resultadosBusqueda = BuscadorDeCanciones.buscar(catalogoDeBusqueda, parametroBusqueda);
        if (resultadosBusqueda.isEmpty()) {
            throw new CancionNoEncontradaException("La cancion buscada no fue encontrada");
        } else {
            System.out.println("=====Resultados de la busqueda=====");
            return seleccionarCancion(resultadosBusqueda, mostrarCompleto);
        }
    }

    public static Cancion seleccionarCancion(List<Cancion> catalogoDisponible, boolean busquedaCompleta) {
        String mensajeMostrar = "";
        int opcionSeleccionada = 0;

        for(int i = 0; i < catalogoDisponible.size(); ++i) {
            Cancion cancion = (Cancion)catalogoDisponible.get(i);
            if (busquedaCompleta) {
                mensajeMostrar = i + ". Nombre: " + cancion.getNombre() + "\nAlbum: " + cancion.getAlbum() + "\nArtista: " + cancion.getArtista() + "\nCompositor: " + cancion.getCompositor() + "\nGenero: " + cancion.getGenero() + "\nFecha de lanzamiento: " + cancion.getFechaLanzamiento().toString() + "\nCalificacion: " + cancion.getCalificacion() + "\nPrecio: " + cancion.getPrecio() + "\n--------------------------------------------";
            } else {
                mensajeMostrar = i + ". Nombre: " + cancion.getNombre() + "\nAlbum: " + cancion.getAlbum() + "\nArtista: " + cancion.getArtista() + "\nCompositor: " + cancion.getCompositor() + "\nGenero: " + cancion.getGenero() + "\nFecha de lanzamiento: " + cancion.getFechaLanzamiento().toString() + "\nCalificacion: " + cancion.getCalificacion() + "\n--------------------------------------------";
            }

            System.out.println(mensajeMostrar);
        }

        System.out.println("Seleccione una de las canciones: ");

        while(true) {
            try {
                opcionSeleccionada = Integer.parseInt(reader.readLine());
                if (opcionSeleccionada <= catalogoDisponible.size()) {
                    return (Cancion)catalogoDisponible.get(opcionSeleccionada);
                }

                System.out.println("Escoja una opcion valida");
            } catch (NumberFormatException var6) {
                System.out.println("Debe ingresar un numbero valido");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void agregarCancion() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        System.out.println("===== Agregar una nueva cancion =====");
        System.out.print("Nombre: ");
        String nombre = reader.readLine().trim();
        System.out.print("Genero: ");
        String genero = reader.readLine().trim();
        System.out.print("Artista: ");
        String artista = reader.readLine().trim();
        System.out.print("Compositor: ");
        String compositor = reader.readLine().trim();

        LocalDate fechaLanzamiento;
        while(true) {
            try {
                System.out.print("Fecha lanzamiento (YYYY-MM-DD): ");
                fechaLanzamiento = LocalDate.parse(reader.readLine().trim());
                break;
            } catch (Exception var15) {
                System.out.println("Fecha invalida.");
            }
        }

        System.out.print("Album: ");
        String album = reader.readLine().trim();
        System.out.print("Caratula: ");
        String caratula = reader.readLine().trim();

        double calificacion;
        while(true) {
            try {
                System.out.print("Calificacion: ");
                calificacion = Double.parseDouble(reader.readLine());
                break;
            } catch (NumberFormatException var14) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }

        double precio;
        while(true) {
            try {
                System.out.print("Precio: ");
                precio = Double.parseDouble(reader.readLine());
                break;
            } catch (NumberFormatException var13) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }

        Cancion nuevaCancion = new Cancion(nombre, genero, artista, compositor, fechaLanzamiento, album, caratula, calificacion, precio);
        CatalogoGeneral.agregarCancionAlCatalogo(nuevaCancion);
        System.out.println();
        System.out.println("Cancion agregada correctamente.");
    }

    static {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
}