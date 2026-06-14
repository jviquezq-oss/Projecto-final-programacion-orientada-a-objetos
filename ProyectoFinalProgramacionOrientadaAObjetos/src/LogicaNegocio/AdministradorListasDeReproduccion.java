package LogicaNegocio;

import Entidades.Cancion;
import Entidades.CatalogoGeneral;
import Entidades.ListaRepoduccion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

public class AdministradorListasDeReproduccion {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void crearListaReproduccion()
            throws IOException {

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                System.in));

        System.out.println();
        System.out.println(
                "===== CREAR LISTA DE REPRODUCCION =====");

        String nombreLista;

        while (true) {

            System.out.print(
                    "Nombre de la lista: ");

            nombreLista =
                    reader.readLine()
                            .trim();

            if (!nombreLista.isEmpty()) {
                break;
            }

            System.out.println(
                    "El nombre de la lista no puede estar vacío.");
        }

        ListaRepoduccion nuevaLista =
                new ListaRepoduccion(
                        nombreLista,
                        LocalDate.now(),
                        0.0);

        SesionUsuario.getUsuarioActivo().getListasPersonales()
                .add(nuevaLista);

        System.out.println();
        System.out.println(
                "Lista creada correctamente.");
    }

    public static void mostrarListasDeReproduccion(List<ListaRepoduccion>listasDisponibles){
        String listasAMostrar = "=====ListasDisponibles=====\n";
        if(listasDisponibles.isEmpty()){
            System.out.println("No se encuentran listas disponibles");
            return;
        }
        for(int i =0;i<listasDisponibles.size();i++){
            ListaRepoduccion listaReproduccion =  listasDisponibles.get(i);
            listasAMostrar+=(++i)+". "+
                            "Nombre: "+listaReproduccion.getNombre()+"\n"+
                            "Fecha de creacion: "+listaReproduccion.getFechaCreacion().toString()+"\n"+
                            "Calificacion: "+listaReproduccion.getCalificacion()+"\n"+
                            "-----------------------------------------------------------";
        }
        System.out.println(listasAMostrar);
    }
    public static void AgregarCancionAListaDeReproduccion(List<ListaRepoduccion>listasDisponibles){
        int seleccionDeMenu = 0;
        ListaRepoduccion listaAgregar = null;
        if(listasDisponibles.isEmpty()){
            System.out.println("No hay listsa disponibles");
            return;
        }
        System.out.println(
                "=====Agregar cancion a lista de reproduccion=====\n" +
                        "1.Buscar lista de reproduccionr\n" +
                        "2.Seleccionar de las listas existentes\n" +
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
                listaAgregar = buscarListaReproduccion(listasDisponibles);
                if(listaAgregar == null){
                    return;
                }

                break;
            case 2:
                listaAgregar = buscarListaReproduccion(listasDisponibles);
                break;

        }
        if(listaAgregar != null) {
            Cancion cancionAgregar = AdministadorCatalogo.opcionesDeCancio(SesionUsuario.getUsuarioActivo().getColeccionComprada());
            if (cancionAgregar != null) {
                listaAgregar.agregarCanciones(cancionAgregar);
                System.out.println("Cancion: " + cancionAgregar.getNombre() + " agregada existosamente.");
            }
        }
    }
    private static ListaRepoduccion buscarListaReproduccion(List<ListaRepoduccion>listasDisponibles){
        String nombreLista;
        System.out.println("=====Busqueda de listas de reproduccion====\n Ingrese el nombre de la lista de reproduccion");
        try {
            nombreLista = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(ListaRepoduccion listaReproduccion:listasDisponibles){
            if(listaReproduccion.getNombre().equalsIgnoreCase(nombreLista)){
                System.out.println("La lista "+listaReproduccion.getNombre()+" fue enontrada");
                return listaReproduccion;
            }
        }
        System.out.println("La lista "+nombreLista+" no fue enontrada");
        return null;

    }

}
