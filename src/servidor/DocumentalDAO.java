package servidor;

import modelo.Documental;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentalDAO {

    public boolean insertar(Documental doc) {
        // VALIDACIONES DE ENTRADA
        if (doc.getTitulo() == null || doc.getTitulo().trim().isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return false;
        }
        if (doc.getTema() == null || doc.getTema().trim().isEmpty()) {
            System.out.println("El tema no puede estar vacío.");
            return false;
        }
        if (doc.getDuracion() <= 0) {
            System.out.println("La duración debe ser mayor a cero.");
            return false;
        }
        if (doc.getAnioEstreno() <= 1800) {
            System.out.println("El año de estreno debe ser válido.");
            return false;
        }
        if (doc.getCalificacion() < 0 || doc.getCalificacion() > 10) {
            System.out.println("La calificación debe estar entre 0 y 10.");
            return false;
        }
        if (doc.getDirector() == null || doc.getDirector().trim().isEmpty()) {
            System.out.println("El director no puede estar vacío.");
            return false;
        }

        // CONTROL DE DUPLICADOS
        if (existe(doc.getTitulo(), doc.getAnioEstreno())) {
            System.out.println("Ya existe un documental con ese título y año.");
            return false;
        }

        String sql = "INSERT INTO documentales (titulo, tema, duracion, anio_estreno, calificacion, director) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doc.getTitulo());
            stmt.setString(2, doc.getTema());
            stmt.setInt(3, doc.getDuracion());
            stmt.setInt(4, doc.getAnioEstreno());
            stmt.setDouble(5, doc.getCalificacion());
            stmt.setString(6, doc.getDirector());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Documental> listar() {
        List<Documental> lista = new ArrayList<>();
        String sql = "SELECT * FROM documentales";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Documental d = new Documental(
                        rs.getString("titulo"),
                        rs.getString("tema"),
                        rs.getInt("duracion"),
                        rs.getInt("anio_estreno"),
                        rs.getDouble("calificacion"),
                        rs.getString("director")
                );
                d.setId(rs.getInt("id"));
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(Documental doc) {
        String sql = "UPDATE documentales SET titulo=?, tema=?, duracion=?, anio_estreno=?, calificacion=?, director=? WHERE id=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doc.getTitulo());
            stmt.setString(2, doc.getTema());
            stmt.setInt(3, doc.getDuracion());
            stmt.setInt(4, doc.getAnioEstreno());
            stmt.setDouble(5, doc.getCalificacion());
            stmt.setString(6, doc.getDirector());
            stmt.setInt(7, doc.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM documentales WHERE id=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void eliminarTodos() {
        String sql = "DELETE FROM documentales";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("¡Todos los registros de documentales han sido eliminados!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Control de duplicados
    public boolean existe(String titulo, int anioEstreno) {
        String sql = "SELECT COUNT(*) FROM documentales WHERE titulo = ? AND anio_estreno = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setInt(2, anioEstreno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
