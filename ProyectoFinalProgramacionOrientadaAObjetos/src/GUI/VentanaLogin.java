package GUI;

import Entidades.Usuario;
import Excepciones.CredencialesInvalidasException;
import Excepciones.DatosInvalidosException;
import LogicaNegocio.AdministradorUsuarios;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaLogin extends JFrame {
    private JTextField txtNombreUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnCancelar;
    private JButton btnCrearUsuario;

    public VentanaLogin() {
        this.configurarVentana();
        this.inicializarComponentes();
        this.setVisible(true);
    }

    private void configurarVentana() {
        this.setTitle("Inicio de Sesión");
        this.setSize(500, 300);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setHorizontalAlignment(0);
        lblTitulo.setFont(new Font("Segoe UI", 1, 24));
        panelPrincipal.add(lblTitulo, "North");
        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = 2;
        this.txtNombreUsuario = new JTextField(20);
        this.txtContrasena = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formulario.add(new JLabel("Nombre de Usuario"), gbc);
        gbc.gridx = 1;
        formulario.add(this.txtNombreUsuario, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formulario.add(new JLabel("Contraseña"), gbc);
        gbc.gridx = 1;
        formulario.add(this.txtContrasena, gbc);
        panelPrincipal.add(formulario, "Center");
        JPanel panelBotones = new JPanel(new FlowLayout(2));
        this.btnCancelar = new JButton("Cancelar");
        this.btnIngresar = new JButton("Ingresar");
        this.btnCrearUsuario = new JButton("Crear usuario");
        panelBotones.add(this.btnCancelar);
        panelBotones.add(this.btnIngresar);
        panelBotones.add(this.btnCrearUsuario);
        panelPrincipal.add(panelBotones, "South");
        this.add(panelPrincipal);
        this.configurarEventos();
    }

    private void configurarEventos() {
        this.btnCancelar.addActionListener((e) -> System.exit(0));
        this.btnCrearUsuario.addActionListener((e) -> {
            this.dispose();
            new VentanaCrearUsuario();
        });
        this.btnIngresar.addActionListener((e) -> {
            try {
                this.validarCampos();
                Usuario usuario = AdministradorUsuarios.autenticarUsuario(this.txtNombreUsuario.getText().trim(), String.valueOf(this.txtContrasena.getPassword()));
                JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getNombrCompleto());
                this.dispose();
            } catch (DatosInvalidosException | CredencialesInvalidasException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", 0);
                this.txtContrasena.setText("");
            }

        });
    }

    private void validarCampos() throws DatosInvalidosException {
        if (this.txtNombreUsuario.getText().trim().isEmpty()) {
            throw new DatosInvalidosException("Debe ingresar un nombre de usuario.");
        } else if (String.valueOf(this.txtContrasena.getPassword()).trim().isEmpty()) {
            throw new DatosInvalidosException("Debe ingresar una contraseña.");
        }
    }
}
