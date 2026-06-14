package Estructuras;
import Entidades.Cancion;
import java.util.ArrayList;
public class ColaDeCanciones {
    private final ArrayList<Cancion> colaCanciones;
    public ColaDeCanciones(){
        colaCanciones = new ArrayList<>();
    }
    public void insertarElemento(Cancion cancion){
        colaCanciones.add(cancion);
    }
    public Cancion  removerElemento(){
        if(colaCanciones.isEmpty()){
            System.out.println("No hay canciones en la cola.");
            return null;
        }
        Cancion temp = colaCanciones.get(0);
        colaCanciones.remove(0);
        return temp;
    }
    public Cancion  verFrente(){
        if(colaCanciones.isEmpty()){
            System.out.println("No hay canciones en la cola");
            return null;
        }
        return colaCanciones.get(0);
    }
    public void limpiarCola(){
        this.colaCanciones.clear();
    }
}
