package Entidades;

import Estructuras.ColaDeCanciones;
import Excepciones.ColaVaciaException;
import Excepciones.DatosInvalidosException;
import Excepciones.ParametroInvalidoException;

public class Reproductor {

    private Cancion cancionActual;
    private int segundoActual;
    private boolean reproduciendo;
    private final ColaDeCanciones cola;
    private Thread hiloReproduccion;

    public Reproductor() {
        this.cancionActual = null;
        this.segundoActual = 0;
        this.reproduciendo = false;
        this.cola = new ColaDeCanciones();
        this.hiloReproduccion = null;
    }

    public synchronized void agregarCancion(Cancion cancion) {
        if (cancion == null) {
            throw new ParametroInvalidoException("La canción no puede ser nula.");
        }

        cola.insertarElemento(cancion);
    }

    public synchronized void reproducir() throws ColaVaciaException {
        if (cancionActual == null || segundoActual >= cancionActual.getDuracion()) {
            cancionActual = cola.removerElemento();
            segundoActual = 0;
        }

        reproduciendo = true;
        iniciarHiloReproduccion();
    }

    public synchronized void pausar() throws DatosInvalidosException {
        if (cancionActual == null) {
            throw new DatosInvalidosException("No hay una canción para pausar.");
        }

        reproduciendo = false;
    }

    public synchronized void reanudar() throws DatosInvalidosException {
        if (cancionActual == null) {
            throw new DatosInvalidosException("No hay una canción para reanudar.");
        }

        reproduciendo = true;

        iniciarHiloReproduccion();
    }

    public synchronized void avanzar() throws DatosInvalidosException {
        if (cancionActual == null) {
            throw new DatosInvalidosException("No hay una canción seleccionada.");
        }

        segundoActual += 10;

        if (segundoActual >= cancionActual.getDuracion()) {
            segundoActual = cancionActual.getDuracion();
            reproduciendo = false;
        }
    }

    public synchronized void retroceder() throws DatosInvalidosException {
        if (cancionActual == null) {
            throw new DatosInvalidosException("No hay una canción seleccionada.");
        }

        segundoActual -= 10;

        if (segundoActual < 0) {
            segundoActual = 0;
        }
    }

    public synchronized void siguiente() throws DatosInvalidosException {
        if (cancionActual == null) {
            throw new DatosInvalidosException("No hay una canción reproduciéndose.");
        }

        try {
            cancionActual = cola.removerElemento();
            segundoActual = 0;
            reproduciendo = true;

            iniciarHiloReproduccion();

        } catch (ColaVaciaException e) {
            cancionActual = null;
            segundoActual = 0;
            reproduciendo = false;
        }
    }

    private synchronized void iniciarHiloReproduccion() {
        if (hiloReproduccion != null && hiloReproduccion.isAlive()) {
            return;
        }

        hiloReproduccion = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);

                    synchronized (this) {
                        if (!reproduciendo || cancionActual == null) {
                            break;
                        }

                        segundoActual++;

                        if (segundoActual >= cancionActual.getDuracion()) {
                            segundoActual = cancionActual.getDuracion();
                            reproduciendo = false;

                            System.out.println("\nLa canción \"" + cancionActual.getNombre() + "\" ha terminado.");

                            try {
                                cancionActual = cola.removerElemento();
                                segundoActual = 0;
                                reproduciendo = true;

                                System.out.println("\nIniciando siguiente canción:");
                                System.out.println(obtenerEstadoReproduccion());

                            } catch (ColaVaciaException e) {
                                cancionActual = null;
                                segundoActual = 0;
                                reproduciendo = false;

                                System.out.println("No hay más canciones en la cola.");
                                break;
                            }
                        }
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        hiloReproduccion.start();
    }

    public synchronized String obtenerEstadoReproduccion() {
        if (cancionActual == null) {
            return "\nNo hay ninguna canción en reproducción.";
        }

        return "\n===== REPRODUCTOR =====" +
                "\nCanción: " + cancionActual.getNombre() +
                "\nArtista: " + cancionActual.getArtista() +
                "\nEstado: " + obtenerEstado() +
                "\nTiempo: " + formatearTiempo(segundoActual) + " / " + formatearTiempo(cancionActual.getDuracion());
    }

    private String obtenerEstado() {
        if (segundoActual >= cancionActual.getDuracion()) {
            return "FINALIZADA";
        }

        return reproduciendo ? "REPRODUCIENDO" : "PAUSADA";
    }

    private String formatearTiempo(int segundos) {
        int minutos = segundos / 60;
        int segundosRestantes = segundos % 60;

        return String.format("%02d:%02d", minutos, segundosRestantes);
    }

    public synchronized Cancion getCancionActual() {
        return cancionActual;
    }

    public synchronized int getSegundoActual() {
        return segundoActual;
    }

    public synchronized boolean isReproduciendo() {
        return reproduciendo;
    }

    public ColaDeCanciones getCola() {
        return cola;
    }
    public void limpiarCola(){
        this.cola.limpiarCola();
    }
}