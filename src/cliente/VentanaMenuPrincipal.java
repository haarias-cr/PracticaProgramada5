package cliente;

import javax.swing.*;
import java.awt.*;

public class VentanaMenuPrincipal extends JFrame {

    public VentanaMenuPrincipal(String nombreUsuario) {
        setTitle("Fideflix - Menú Principal");
        setSize(350, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel saludo = new JLabel("¡Bienvenido(a), " + nombreUsuario + "!", JLabel.CENTER);
        saludo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnPeliculas = new JButton("Gestionar Películas");
        JButton btnSeries = new JButton("Gestionar Series");
        JButton btnDocumentales = new JButton("Gestionar Documentales");
        JButton btnCerrar = new JButton("Cerrar sesión");

        btnPeliculas.addActionListener(e -> {
            new VentanaPeliculas().setVisible(true);
        });

        btnSeries.addActionListener(e -> {
            new VentanaSeries().setVisible(true);
        });

        btnDocumentales.addActionListener(e -> {
            new VentanaDocumentales().setVisible(true);
        });

        btnCerrar.addActionListener(e -> {
            dispose(); // Cierra el menú
            new VentanaInicioSesion().setVisible(true);
        });

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.add(btnPeliculas);
        panelBotones.add(btnSeries);
        panelBotones.add(btnDocumentales);
        panelBotones.add(btnCerrar);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panelPrincipal.add(saludo, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        add(panelPrincipal);
    }
}
