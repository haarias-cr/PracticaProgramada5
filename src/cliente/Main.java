package cliente;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Verifica primero el servidor de usuarios
        boolean usuariosOK = ClienteSocket.servidorDisponible();
        // Verifica el servidor BD usando el puerto 6000 (puedes agregar método si quieres)
        boolean bdOK = ClienteSocket.servidorBDDisponible();

        if (!usuariosOK && !bdOK) {
            JOptionPane.showMessageDialog(null,
                    "Ningún servidor está disponible.\n" +
                            "Ejecute primero 'ServidorUsuarios.java' y 'ServidorBD.java' en la carpeta 'servidor'.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } else if (!usuariosOK) {
            JOptionPane.showMessageDialog(null,
                    "El servidor de usuarios no está disponible.\n" +
                            "Ejecute primero 'ServidorUsuarios.java' en la carpeta 'servidor'.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } else if (!bdOK) {
            JOptionPane.showMessageDialog(null,
                    "El servidor de base de datos no está disponible.\n" +
                            "Ejecute primero 'ServidorBD.java' en la carpeta 'servidor'.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Iniciar GUI de inicio de sesión
        SwingUtilities.invokeLater(() -> {
            new VentanaInicioSesion().setVisible(true);
        });
    }
}
