package LogicaNegocio;

import Entidades.Cancion;
import Estructuras.ColaDeCanciones;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class AdministradorColaDeCanciones {
    private static BufferedReader reader;

    public AdministradorColaDeCanciones() {
    }

    public static void agregarCancionAlaCola(ColaDeCanciones colaActual, List<Cancion> catalogoDisponible) throws IOException {
        if (catalogoDisponible.isEmpty()) {
            System.out.println("El catalogo se encuentra disponible");
        } else {
            System.out.println("=====Agregar cancion a la cola=====\n1.Buscar una cancion para agregar\n2.Seleccionar del catalogo de canciones disponibles\n3.Volver al menu principal");

            int seleccionDeMenu;
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

            Cancion cancionAgregar;
            switch (seleccionDeMenu) {
                case 1:
                    cancionAgregar = AdministadorCatalogo.buscarCancion(catalogoDisponible, false);
                    if (cancionAgregar != null) {
                        colaActual.insertarElemento(cancionAgregar);
                        System.out.println("Cancion ingresada a la cola de repoduccion");
                    }
                    break;
                case 2:
                    cancionAgregar = AdministadorCatalogo.seleccionarCancion(catalogoDisponible, false);
                    colaActual.insertarElemento(cancionAgregar);
                    System.out.println("Cancion ingresada a la cola de repoduccion");
                    break;
                case 3:
                    return;
                default:
                    throw new IllegalStateException("Valor invalido: " + seleccionDeMenu);
            }

        }
    }

    static {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
}