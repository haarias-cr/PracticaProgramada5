package servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Cambia la IP, usuario y contraseña según tu configuración
    private static final String URL = "jdbc:mysql://192.168.1.69:3306/fideflix";
    private static final String USUARIO = "javauser";
    private static final String CLAVE = "fidelitas";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
