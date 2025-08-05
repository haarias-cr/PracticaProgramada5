package cliente;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaCrearUsuario extends JFrame {
    private JTextField campoNombre;
    private JPasswordField campoContrasena;

    public VentanaCrearUsuario() {
        setTitle("Crear Usuario - Fideflix");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        campoNombre = new JTextField(15);
        campoContrasena = new JPasswordField(15);
        JButton botonGuardar = new JButton("Guardar");

        botonGuardar.addActionListener(e -> {
            String nombre = campoNombre.getText();
            String contrasena = new String(campoContrasena.getPassword());

            if (!nombre.isEmpty() && !contrasena.isEmpty()) {
                Usuario nuevo = new Usuario(nombre, contrasena);
                String resultado = String.valueOf(ClienteSocket.crearUsuario(nuevo));

                if ("USUARIO_CREADO".equals(resultado)) {
                    JOptionPane.showMessageDialog(this, "Usuario creado correctamente.");
                    dispose();
                } else if ("USUARIO_DUPLICADO".equals(resultado)) {
                    JOptionPane.showMessageDialog(this, "El usuario ya existe. Intente con otro nombre.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al crear usuario. Intente de nuevo.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.");
            }
        });

        // Diseño mejorado
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 5, 5));
        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(campoNombre);
        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(campoContrasena);

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonGuardar);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(panelCampos);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(panelBoton);

        add(panelPrincipal);
    }
}
