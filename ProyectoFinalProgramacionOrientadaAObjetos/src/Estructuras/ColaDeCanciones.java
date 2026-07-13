package Estructuras;

import Entidades.Cancion;
import Excepciones.ColaVaciaException;
import java.util.ArrayList;

public class ColaDeCanciones {
    private final ArrayList<Cancion> colaCanciones = new ArrayList();

    public ColaDeCanciones() {
    }

    public void insertarElemento(Cancion cancion) {
        this.colaCanciones.add(cancion);
    }

    public Cancion removerElemento() throws ColaVaciaException {
        if (this.colaCanciones.isEmpty()) {
            throw new ColaVaciaException("La cola de canciones está vacía.");
        } else {
            Cancion temp = (Cancion)this.colaCanciones.get(0);
            this.colaCanciones.remove(0);
            return temp;
        }
    }

    public Cancion verFrente() throws ColaVaciaException {
        if (this.colaCanciones.isEmpty()) {
            throw new ColaVaciaException("La cola de canciones está vacía.");
        } else {
            return (Cancion)this.colaCanciones.get(0);
        }
    }

    public void limpiarCola() {
        this.colaCanciones.clear();
    }
}
