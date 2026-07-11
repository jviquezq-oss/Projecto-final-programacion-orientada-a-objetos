package Entidades;

import Estructuras.ColaDeCanciones;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends Cuenta{
    private String nombrCompleto;
    private LocalDate fechaDeNacimiento;
    private String nacionalidad;
    private String cedula;
    private String avatar;
    private double saldo;
    private List<ListaRepoduccion> listasPersonales;
    private List<Cancion>coleccionComprada;
    private ColaDeCanciones colaReproduccion;

    public Usuario(String nombrCompleto, LocalDate fechaDeNacimiento, String nacionalidad, String cedula, String avatar, String nombreDeUsuario,String correoElectronico,String contrasena) {
        super(correoElectronico,contrasena,nombreDeUsuario);
        this.nombrCompleto = nombrCompleto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;
        this.avatar = avatar;
        this.colaReproduccion = new ColaDeCanciones();
        this.coleccionComprada = new ArrayList<>();
        this.listasPersonales = new ArrayList<>();
    }

    public String getNombrCompleto() {
         return nombrCompleto;
    }

    public void setNombrCompleto(String nombrCompleto) {
        this.nombrCompleto = nombrCompleto;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<ListaRepoduccion> getListasPersonales() {
        return listasPersonales;
    }

    public void setListasPersonales(List<ListaRepoduccion> listasPersonales) {
        this.listasPersonales = listasPersonales;
    }

    public List<Cancion> getColeccionComprada() {
        return coleccionComprada;
    }

    public void setColeccionComprada(List<Cancion> coleccionComprada) {
        this.coleccionComprada = coleccionComprada;
    }

    public ColaDeCanciones getColaReproduccion() {
        return colaReproduccion;
    }

    public void setColaReproduccion(ColaDeCanciones colaReproduccion) {
        this.colaReproduccion = colaReproduccion;
    }

    public void comprarCancion(Cancion cancion){
        if(this.saldo>=cancion.getPrecio()){
            this.saldo = this.saldo - cancion.getPrecio();
            coleccionComprada.add(cancion);
            System.out.println("Cancion adquirida. Ahora disponible en la coleccion personal");
        }else{
            System.out.println("Fondos insuficientes");
        }
    }
    public void agregarCancionALista(Cancion cancion, ListaRepoduccion listaReproduccion) throws IOException {
        if(listaReproduccion.getCancionesContenidas().contains(cancion)){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("La lista: "+listaReproduccion.getNombre()+" ya contiene la cancion seleccionada.\n Deasea agregar la cancion de todas formas? (Y/N)");
            String continuar = reader.readLine();
            if(continuar.toUpperCase().trim().equals("Y")){
                listaReproduccion.agregarCanciones(cancion);
                System.out.println("Cancion agregada a la lista de reproduccion");
            }else{
                listaReproduccion.agregarCanciones(cancion);
                System.out.println("Cancion agregada a la lista de reproduccion");
            }


        }
    }
    public void agregarCancionACola(ColaDeCanciones colaActual,Cancion cancion){
        colaActual.insertarElemento(cancion);
        System.out.println("Cancion agregada a la cola de reproduccion");
    }

    @Override
    public String toString() {
        return "Usuario\n" +
                "nombrCompleto='" + nombrCompleto + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", cedula='" + cedula + '\'' +
                ", avatar='" + avatar + '\'' +
                ", saldo=" + saldo;
    }
}
