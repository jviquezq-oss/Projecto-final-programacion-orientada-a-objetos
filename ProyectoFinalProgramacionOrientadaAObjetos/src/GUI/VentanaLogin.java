package GUI;

import LogicaNegocio.AdministradorUsuarios;
import Entidades.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    private JTextField txtNombreUsuario;
    private JPasswordField txtContrasena;

    private JButton btnIngresar;
    private JButton btnCancelar;
    private JButton btnCrearUsuario;

    public VentanaLogin() {

        configurarVentana();
        inicializarComponentes();

        setVisible(true);
    }

    private void configurarVentana() {

        setTitle("Inicio de Sesión");

        setSize(500, 300);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal =
                new JPanel(
                        new BorderLayout(
                                15,
                                15));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20));

        JLabel lblTitulo =
                new JLabel(
                        "Iniciar Sesión");

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
                        10,
                        10,
                        10,
                        10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        txtNombreUsuario =
                new JTextField(20);

        txtContrasena =
                new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;

        formulario.add(
                new JLabel(
                        "Nombre de Usuario"),
                gbc);

        gbc.gridx = 1;

        formulario.add(
                txtNombreUsuario,
                gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        formulario.add(
                new JLabel(
                        "Contraseña"),
                gbc);

        gbc.gridx = 1;

        formulario.add(
                txtContrasena,
                gbc);

        panelPrincipal.add(
                formulario,
                BorderLayout.CENTER);

        JPanel panelBotones =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT));

        btnCancelar =
                new JButton(
                        "Cancelar");

        btnIngresar =
                new JButton(
                        "Ingresar");
        btnCrearUsuario = new JButton(

                            "Crear usuario");
        panelBotones.add(
                btnCancelar);

        panelBotones.add(
                btnIngresar);
        panelBotones.add(btnCrearUsuario);
        panelPrincipal.add(
                panelBotones,
                BorderLayout.SOUTH);

        add(panelPrincipal);

        configurarEventos();
    }

    private void configurarEventos() {

        btnCancelar
                .addActionListener(
                        e -> System.exit(0));

        btnCrearUsuario
                .addActionListener(
                        e-> {
                            dispose();
                            new VentanaCrearUsuario();
                        });

        btnIngresar
                .addActionListener(e -> {

                    try {

                        validarCampos();

                        Usuario usuario =
                                AdministradorUsuarios.autenticarUsuario(
                                                txtNombreUsuario
                                                        .getText()
                                                        .trim(),

                                                String.valueOf(
                                                        txtContrasena
                                                                .getPassword()));

                        if (usuario == null) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Usuario o contraseña incorrectos.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);

                            txtContrasena.setText("");

                            return;
                        }

                        JOptionPane.showMessageDialog(
                                this,
                                "Bienvenido "
                                        + usuario.getNombrCompleto());

                        dispose();
                        //MenuUsuario.mostrarMenu();

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

        if (txtNombreUsuario
                .getText()
                .trim()
                .isEmpty()) {

            throw new Exception(
                    "Debe ingresar un nombre de usuario.");
        }

        if (String.valueOf(
                        txtContrasena
                                .getPassword())
                .trim()
                .isEmpty()) {

            throw new Exception(
                    "Debe ingresar una contraseña.");
        }
    }
}