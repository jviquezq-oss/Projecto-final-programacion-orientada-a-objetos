package Estructuras;

import Entidades.Cancion;
import java.util.ArrayList;

public class ColaDeCanciones {
    private final ArrayList<Cancion> colaCanciones = new ArrayList();

    public ColaDeCanciones() {
    }

    public void insertarElemento(Cancion cancion) {
        this.colaCanciones.add(cancion);
    }

    public Cancion removerElemento() {
        if (this.colaCanciones.isEmpty()) {
            System.out.println("No hay canciones en la cola.");
            return null;
        } else {
            Cancion temp = (Cancion)this.colaCanciones.get(0);
            this.colaCanciones.remove(0);
            return temp;
        }
    }

    public Cancion verFrente() {
        if (this.colaCanciones.isEmpty()) {
            System.out.println("No hay canciones en la cola");
            return null;
        } else {
            return (Cancion)this.colaCanciones.get(0);
        }
    }

    public void limpiarCola() {
        this.colaCanciones.clear();
    }
}
