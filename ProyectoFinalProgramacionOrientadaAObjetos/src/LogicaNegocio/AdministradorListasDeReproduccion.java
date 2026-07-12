package LogicaNegocio;

import Entidades.Cancion;
import Entidades.ListaRepoduccion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

public class AdministradorListasDeReproduccion {
    private static final BufferedReader reader;

    public AdministradorListasDeReproduccion() {
    }

    public static void crearListaReproduccion() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        System.out.println("===== CREAR LISTA DE REPRODUCCION =====");

        while(true) {
            System.out.print("Nombre de la lista: ");
            String nombreLista = reader.readLine().trim();
            if (!nombreLista.isEmpty()) {
                ListaRepoduccion nuevaLista = new ListaRepoduccion(nombreLista, LocalDate.now(), (double)0.0F);
                SesionUsuario.getUsuarioActivo().getListasPersonales().add(nuevaLista);
                System.out.println();
                System.out.println("Lista creada correctamente.");
                return;
            }

            System.out.println("El nombre de la lista no puede estar vacío.");
        }
    }

    public static void mostrarListasDeReproduccion(List<ListaRepoduccion> listasDisponibles) {
        String listasAMostrar = "=====ListasDisponibles=====\n";
        if (listasDisponibles.isEmpty()) {
            System.out.println("No se encuentran listas disponibles");
        } else {
            for(int i = 0; i < listasDisponibles.size(); ++i) {
                ListaRepoduccion listaReproduccion = (ListaRepoduccion)listasDisponibles.get(i);
                ++i;
                listasAMostrar = listasAMostrar + i + ". Nombre: " + listaReproduccion.getNombre() + "\nFecha de creacion: " + listaReproduccion.getFechaCreacion().toString() + "\nCalificacion: " + listaReproduccion.getCalificacion() + "\n-----------------------------------------------------------";
            }

            System.out.println(listasAMostrar);
        }
    }

    public static void AgregarCancionAListaDeReproduccion(List<ListaRepoduccion> listasDisponibles) {
        int seleccionDeMenu = 0;
        ListaRepoduccion listaAgregar = null;
        if (listasDisponibles.isEmpty()) {
            System.out.println("No hay listsa disponibles");
        } else {
            System.out.println("=====Agregar cancion a lista de reproduccion=====\n1.Buscar lista de reproduccionr\n2.Seleccionar de las listas existentes\n3.Volver al menu principal");

            while(true) {
                try {
                    seleccionDeMenu = Integer.parseInt(reader.readLine());
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (NumberFormatException var5) {
                    System.out.println("Ingrese un numero valdio");
                }
            }

            switch (seleccionDeMenu) {
                case 1:
                    listaAgregar = buscarListaReproduccion(listasDisponibles);
                    if (listaAgregar == null) {
                        return;
                    }
                    break;
                case 2:
                    listaAgregar = buscarListaReproduccion(listasDisponibles);
            }

            if (listaAgregar != null) {
                Cancion cancionAgregar = AdministadorCatalogo.opcionesDeCancio(SesionUsuario.getUsuarioActivo().getColeccionComprada());
                if (cancionAgregar != null) {
                    listaAgregar.agregarCanciones(cancionAgregar);
                    System.out.println("Cancion: " + cancionAgregar.getNombre() + " agregada existosamente.");
                }
            }

        }
    }

    private static ListaRepoduccion buscarListaReproduccion(List<ListaRepoduccion> listasDisponibles) {
        System.out.println("=====Busqueda de listas de reproduccion====\n Ingrese el nombre de la lista de reproduccion");

        String nombreLista;
        try {
            nombreLista = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(ListaRepoduccion listaReproduccion : listasDisponibles) {
            if (listaReproduccion.getNombre().equalsIgnoreCase(nombreLista)) {
                System.out.println("La lista " + listaReproduccion.getNombre() + " fue enontrada");
                return listaReproduccion;
            }
        }

        System.out.println("La lista " + nombreLista + " no fue enontrada");
        return null;
    }

    static {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
}
