package GUI;

import LogicaNegocio.AdministradorUsuarios;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
        this.configurarVentana();
        this.inicializarComponentes();
        this.setVisible(true);
    }

    private void configurarVentana() {
        this.setTitle("Registro de Usuario");
        this.setSize(750, 550);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(2);
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel lblTitulo = new JLabel("Crear Usuario");
        lblTitulo.setHorizontalAlignment(0);
        lblTitulo.setFont(new Font("Segoe UI", 1, 24));
        panelPrincipal.add(lblTitulo, "North");
        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = 2;
        this.txtNombreCompleto = new JTextField(25);
        this.txtFechaNacimiento = new JTextField(25);
        this.txtNacionalidad = new JTextField(25);
        this.txtCedula = new JTextField(25);
        this.txtAvatar = new JTextField(25);
        this.txtAvatar.setEditable(false);
        this.txtNombreUsuario = new JTextField(25);
        this.txtCorreo = new JTextField(25);
        this.txtContrasena = new JPasswordField(25);
        this.agregarCampo(formulario, gbc, 0, "Nombre Completo", this.txtNombreCompleto);
        this.agregarCampo(formulario, gbc, 1, "Fecha Nacimiento (YYYY-MM-DD)", this.txtFechaNacimiento);
        this.agregarCampo(formulario, gbc, 2, "Nacionalidad", this.txtNacionalidad);
        this.agregarCampo(formulario, gbc, 3, "Cédula", this.txtCedula);
        this.agregarCampo(formulario, gbc, 4, "Nombre de Usuario", this.txtNombreUsuario);
        this.agregarCampo(formulario, gbc, 5, "Correo Electrónico", this.txtCorreo);
        this.agregarCampo(formulario, gbc, 6, "Contraseña", this.txtContrasena);
        gbc.gridx = 0;
        gbc.gridy = 7;
        formulario.add(new JLabel("Avatar"), gbc);
        gbc.gridx = 1;
        formulario.add(this.txtAvatar, gbc);
        this.btnSeleccionarAvatar = new JButton("Seleccionar");
        gbc.gridx = 2;
        formulario.add(this.btnSeleccionarAvatar, gbc);
        panelPrincipal.add(formulario, "Center");
        JPanel panelBotones = new JPanel(new FlowLayout(2));
        this.btnCancelar = new JButton("Cancelar");
        this.btnCrear = new JButton("Crear Usuario");
        panelBotones.add(this.btnCancelar);
        panelBotones.add(this.btnCrear);
        panelPrincipal.add(panelBotones, "South");
        this.add(panelPrincipal);
        this.configurarEventos();
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent componente) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1;
        panel.add(componente, gbc);
    }

    private void configurarEventos() {
        this.btnSeleccionarAvatar.addActionListener((e) -> {
            JFileChooser selector = new JFileChooser();
            int resultado = selector.showOpenDialog(this);
            if (resultado == 0) {
                File archivo = selector.getSelectedFile();
                this.txtAvatar.setText(archivo.getAbsolutePath());
            }

        });
        this.btnCancelar.addActionListener((e) -> {
            this.dispose();
            new VentanaLogin();
        });
        this.btnCrear.addActionListener((e) -> {
            try {
                this.validarCampos();
                AdministradorUsuarios.registrarUsuario(this.txtNombreCompleto.getText().trim(), LocalDate.parse(this.txtFechaNacimiento.getText().trim()), this.txtNacionalidad.getText().trim(), this.txtCedula.getText().trim(), this.txtAvatar.getText().trim(), this.txtNombreUsuario.getText().trim(), this.txtCorreo.getText().trim(), String.valueOf(this.txtContrasena.getPassword()));
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", 0);
            }

        });
    }

    private void validarCampos() throws Exception {
        if (this.txtNombreCompleto.getText().trim().isEmpty()) {
            throw new Exception("Nombre completo requerido.");
        } else if (this.txtFechaNacimiento.getText().trim().isEmpty()) {
            throw new Exception("Fecha de nacimiento requerida.");
        } else {
            try {
                LocalDate.parse(this.txtFechaNacimiento.getText().trim());
            } catch (Exception var6) {
                throw new Exception("Formato de fecha inválido. Use YYYY-MM-DD.");
            }

            if (this.txtNacionalidad.getText().trim().isEmpty()) {
                throw new Exception("Nacionalidad requerida.");
            } else if (this.txtCedula.getText().trim().isEmpty()) {
                throw new Exception("Cédula requerida.");
            } else {
                String username = this.txtNombreUsuario.getText().trim();
                if (username.isEmpty()) {
                    throw new Exception("Nombre de usuario requerido.");
                } else if (AdministradorUsuarios.existeNombreUsuario(username)) {
                    throw new Exception("El nombre de usuario ya existe.");
                } else {
                    String correo = this.txtCorreo.getText().trim();
                    if (correo.isEmpty()) {
                        throw new Exception("Correo electrónico requerido.");
                    } else {
                        String regexCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                        if (!correo.matches(regexCorreo)) {
                            throw new Exception("Correo electrónico inválido.");
                        } else {
                            String password = String.valueOf(this.txtContrasena.getPassword());
                            if (password.isBlank()) {
                                throw new Exception("Contraseña requerida.");
                            } else {
                                String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,12}$";
                                if (!password.matches(regexPassword)) {
                                    throw new Exception("La contraseña debe:\n- Tener entre 8 y 12 caracteres\n- Tener una mayúscula\n- Tener una minúscula\n- Tener un número\n- Tener un carácter especial\n");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}