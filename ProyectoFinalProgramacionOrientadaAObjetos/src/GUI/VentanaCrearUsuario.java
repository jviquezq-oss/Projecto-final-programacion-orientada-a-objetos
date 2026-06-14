package GUI;

import LogicaNegocio.AdministradorUsuarios;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;

public class VentanaCrearUsuario extends JFrame {

    private JTextField txtNombreCompleto;
    private JTextField txtFechaNacimiento;
    private JTextField txtNacionalidad;
    private JTextField txtCedula;
    private JTextField txtAvatar;
    private JTextField txtNombreUsuario;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;

    private JButton btnSeleccionarAvatar;
    private JButton btnCrear;
    private JButton btnCancelar;

    public VentanaCrearUsuario() {

        configurarVentana();
        inicializarComponentes();

        setVisible(true);
    }

    private void configurarVentana() {

        setTitle("Registro de Usuario");

        setSize(750, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal =
                new JPanel(new BorderLayout(15, 15));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20));

        JLabel lblTitulo =
                new JLabel("Crear Usuario");

        lblTitulo.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblTitulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24));

        panelPrincipal.add(
                lblTitulo,
                BorderLayout.NORTH);

        JPanel formulario =
                new JPanel(
                        new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        8,
                        8,
                        8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        txtNombreCompleto =
                new JTextField(25);

        txtFechaNacimiento =
                new JTextField(25);

        txtNacionalidad =
                new JTextField(25);

        txtCedula =
                new JTextField(25);

        txtAvatar =
                new JTextField(25);

        txtAvatar.setEditable(false);

        txtNombreUsuario =
                new JTextField(25);

        txtCorreo =
                new JTextField(25);

        txtContrasena =
                new JPasswordField(25);

        agregarCampo(
                formulario,
                gbc,
                0,
                "Nombre Completo",
                txtNombreCompleto);

        agregarCampo(
                formulario,
                gbc,
                1,
                "Fecha Nacimiento (YYYY-MM-DD)",
                txtFechaNacimiento);

        agregarCampo(
                formulario,
                gbc,
                2,
                "Nacionalidad",
                txtNacionalidad);

        agregarCampo(
                formulario,
                gbc,
                3,
                "Cédula",
                txtCedula);

        agregarCampo(
                formulario,
                gbc,
                4,
                "Nombre de Usuario",
                txtNombreUsuario);

        agregarCampo(
                formulario,
                gbc,
                5,
                "Correo Electrónico",
                txtCorreo);

        agregarCampo(
                formulario,
                gbc,
                6,
                "Contraseña",
                txtContrasena);

        gbc.gridx = 0;
        gbc.gridy = 7;

        formulario.add(
                new JLabel("Avatar"),
                gbc);

        gbc.gridx = 1;

        formulario.add(
                txtAvatar,
                gbc);

        btnSeleccionarAvatar =
                new JButton("Seleccionar");

        gbc.gridx = 2;

        formulario.add(
                btnSeleccionarAvatar,
                gbc);

        panelPrincipal.add(
                formulario,
                BorderLayout.CENTER);

        JPanel panelBotones =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT));

        btnCancelar =
                new JButton("Cancelar");

        btnCrear =
                new JButton("Crear Usuario");

        panelBotones.add(btnCancelar);
        panelBotones.add(btnCrear);

        panelPrincipal.add(
                panelBotones,
                BorderLayout.SOUTH);

        add(panelPrincipal);

        configurarEventos();
    }

    private void agregarCampo(
            JPanel panel,
            GridBagConstraints gbc,
            int fila,
            String etiqueta,
            JComponent componente) {

        gbc.gridx = 0;
        gbc.gridy = fila;

        panel.add(
                new JLabel(etiqueta),
                gbc);

        gbc.gridx = 1;

        panel.add(
                componente,
                gbc);
    }

    private void configurarEventos() {

        btnSeleccionarAvatar.addActionListener(e -> {

            JFileChooser selector =
                    new JFileChooser();

            int resultado =
                    selector.showOpenDialog(this);

            if (resultado ==
                    JFileChooser.APPROVE_OPTION) {

                File archivo =
                        selector.getSelectedFile();

                txtAvatar.setText(
                        archivo.getAbsolutePath());
            }
        });

        btnCancelar.addActionListener(
                e -> {
                    dispose();
                    new VentanaLogin();
                });

        btnCrear.addActionListener(e -> {

            try {

                validarCampos();

                AdministradorUsuarios
                        .registrarUsuario(
                                txtNombreCompleto
                                        .getText()
                                        .trim(),

                                LocalDate.parse(
                                        txtFechaNacimiento
                                                .getText()
                                                .trim()),

                                txtNacionalidad
                                        .getText()
                                        .trim(),

                                txtCedula
                                        .getText()
                                        .trim(),

                                txtAvatar
                                        .getText()
                                        .trim(),

                                txtNombreUsuario
                                        .getText()
                                        .trim(),

                                txtCorreo
                                        .getText()
                                        .trim(),

                                String.valueOf(
                                        txtContrasena
                                                .getPassword()));

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario registrado correctamente.");

                dispose();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void validarCampos()
            throws Exception {

        if (txtNombreCompleto.getText()
                .trim()
                .isEmpty())
            throw new Exception(
                    "Nombre completo requerido.");

        if (txtFechaNacimiento.getText()
                .trim()
                .isEmpty())
            throw new Exception(
                    "Fecha de nacimiento requerida.");

        try {

            LocalDate.parse(
                    txtFechaNacimiento
                            .getText()
                            .trim());

        } catch (Exception e) {

            throw new Exception(
                    "Formato de fecha inválido. Use YYYY-MM-DD.");
        }

        if (txtNacionalidad.getText()
                .trim()
                .isEmpty())
            throw new Exception(
                    "Nacionalidad requerida.");

        if (txtCedula.getText()
                .trim()
                .isEmpty())
            throw new Exception(
                    "Cédula requerida.");

        String username =
                txtNombreUsuario
                        .getText()
                        .trim();

        if (username.isEmpty())
            throw new Exception(
                    "Nombre de usuario requerido.");

        if (AdministradorUsuarios
                .existeNombreUsuario(
                        username))
            throw new Exception(
                    "El nombre de usuario ya existe.");

        String correo =
                txtCorreo
                        .getText()
                        .trim();

        if (correo.isEmpty())
            throw new Exception(
                    "Correo electrónico requerido.");

        String regexCorreo =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!correo.matches(
                regexCorreo))
            throw new Exception(
                    "Correo electrónico inválido.");

        String password =
                String.valueOf(
                        txtContrasena
                                .getPassword());

        if (password.isBlank())
            throw new Exception(
                    "Contraseña requerida.");

        String regexPassword =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,12}$";

        if (!password.matches(
                regexPassword))
            throw new Exception(
                    """
                    La contraseña debe:
                    - Tener entre 8 y 12 caracteres
                    - Tener una mayúscula
                    - Tener una minúscula
                    - Tener un número
                    - Tener un carácter especial
                    """);
    }
}