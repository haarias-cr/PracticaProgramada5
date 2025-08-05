package cliente;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaInicioSesion extends JFrame {

    public VentanaInicioSesion() {
        setTitle("Inicio de Sesión - Fideflix");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null); // Centrar ventana

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titulo = new JLabel("Fideflix", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);

        // Campos de entrada
        JPanel centro = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField();
        JLabel lblContrasena = new JLabel("Contraseña:");
        JPasswordField txtContrasena = new JPasswordField();
        centro.add(lblUsuario);
        centro.add(txtUsuario);
        centro.add(lblContrasena);
        centro.add(txtContrasena);
        panel.add(centro, BorderLayout.CENTER);

        // Botones
        JPanel botones = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnCrear = new JButton("Crear Usuario");
        botones.add(btnLogin);
        botones.add(btnCrear);
        panel.add(botones, BorderLayout.SOUTH);

        // Acción: Login
        btnLogin.addActionListener(e -> {
            String nombre = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());

            if (!nombre.isEmpty() && !contrasena.isEmpty()) {
                Usuario usuario = ClienteSocket.iniciarSesion(nombre, contrasena);
                if (usuario != null) {
                    new VentanaMenuPrincipal(usuario.getNombre()).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciales inválidas. Intente nuevamente.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
            }
        });

        // Acción: Crear usuario
        btnCrear.addActionListener(e -> {
            new VentanaCrearUsuario().setVisible(true);
        });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaInicioSesion().setVisible(true));
    }
}
