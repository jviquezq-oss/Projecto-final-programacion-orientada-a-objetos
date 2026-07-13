import Entidades.Cancion;
import Entidades.CatalogoGeneral;
import LogicaNegocio.AdministradorUsuarios;
import LogicaNegocio.Login;
import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws IOException {
        CatalogoGeneral.agregarCancionAlCatalogo(new Cancion("One Too Many Mornings", "Folk", "Bob Dylan", "Bob Dylan", LocalDate.of(1964, 8, 8), "The Times They Are a-Changin'", "one_too_many_mornings.jpg", 4.8, 1.29));
        CatalogoGeneral.agregarCancionAlCatalogo(new Cancion("I Love You", "Post-Punk", "Fontaines D.C.", "Fontaines D.C.", LocalDate.of(2022, 4, 22), "Skinty Fia", "i_love_you.jpg", 4.9, 1.49));
        CatalogoGeneral.agregarCancionAlCatalogo(new Cancion("My Kind of Woman", "Indie Rock", "Mac DeMarco", "Mac DeMarco", LocalDate.of(2012, 10, 16), "2", "my_kind_of_woman.jpg", 4.9, 1.39));
        new Cancion("Chamber of Reflection", "Indie Rock", "Mac DeMarco", "Mac DeMarco", LocalDate.of(2014, 4, 1), "Salad Days", "chamber_of_reflection.jpg", 4.8, 1.49);
        AdministradorUsuarios.agregarAdmin("johnny", "dfsgdfg", "123456");
        Login.iniciar();
    }
}