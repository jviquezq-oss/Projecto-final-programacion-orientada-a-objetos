package LogicaNegocio;

import Entidades.Cancion;
import Estructuras.ColaDeCanciones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class AdministradorColaDeCanciones {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void agregarCancionAlaCola(ColaDeCanciones colaActual, List<Cancion>catalogoDisponible) throws IOException {
        int seleccionDeMenu;
        Cancion cancionAgregar;
        if(catalogoDisponible.isEmpty()){
            System.out.println("El catalogo se encuentra disponible");
            return;
        }
        System.out.println(
                "=====Agregar cancion a la cola=====\n" +
                        "1.Buscar una cancion para agregar\n" +
                        "2.Seleccionar del catalogo de canciones disponibles\n" +
                        "3.Volver al menu principal");
        while (true) {
            try {
                seleccionDeMenu = Integer.parseInt(reader.readLine());
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valdio");
            }
        }
        switch (seleccionDeMenu){
            case 1:
                cancionAgregar = AdministadorCatalogo.buscarCancion(catalogoDisponible,false);
                if(cancionAgregar != null){
                    colaActual.insertarElemento(cancionAgregar);
                    System.out.println("Cancion ingresada a la cola de repoduccion");
                }
                break;
            case 2:
                cancionAgregar = AdministadorCatalogo.seleccionarCancion(catalogoDisponible,false);
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
