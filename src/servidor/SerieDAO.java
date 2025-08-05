package servidor;

import modelo.Serie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SerieDAO {

    public boolean insertar(Serie serie) {
        // VALIDACIONES DE ENTRADA
        if (serie.getTitulo() == null || serie.getTitulo().trim().isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return false;
        }
        if (serie.getClasificacion() == null || serie.getClasificacion().trim().isEmpty()) {
            System.out.println("El género no puede estar vacío.");
            return false;
        }
        if (serie.getDuracion() <= 0) {
            System.out.println("La duración debe ser mayor a cero.");
            return false;
        }
        if (serie.getTemporadas() <= 0) {
            System.out.println("El número de temporadas debe ser mayor a cero.");
            return false;
        }
        if (serie.getAnioEstreno() <= 1800) {
            System.out.println("El año de estreno debe ser válido.");
            return false;
        }
        if (serie.getCalificacion() < 0 || serie.getCalificacion() > 10) {
            System.out.println("La calificación debe estar entre 0 y 10.");
            return false;
        }
        if (serie.getCreador() == null || serie.getCreador().trim().isEmpty()) {
            System.out.println("El creador no puede estar vacío.");
            return false;
        }

        // CONTROL DE DUPLICADOS
        if (existe(serie.getTitulo(), serie.getAnioEstreno())) {
            System.out.println("Ya existe una serie con ese título y año.");
            return false;
        }

        String sql = "INSERT INTO series (titulo, genero, duracion, temporadas, anio_estreno, calificacion, creador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, serie.getTitulo());
            stmt.setString(2, serie.getClasificacion());
            stmt.setInt(3, serie.getDuracion());
            stmt.setInt(4, serie.getTemporadas());
            stmt.setInt(5, serie.getAnioEstreno());
            stmt.setDouble(6, serie.getCalificacion());
            stmt.setString(7, serie.getCreador());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Serie> listar() {
        List<Serie> lista = new ArrayList<>();
        String sql = "SELECT * FROM series";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Serie s = new Serie(
                        rs.getString("titulo"),
                        rs.getString("genero"),
                        rs.getInt("duracion"),
                        rs.getInt("temporadas"),
                        rs.getInt("anio_estreno"),
                        rs.getDouble("calificacion"),
                        rs.getString("creador")
                );
                s.setId(rs.getInt("id"));
                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(Serie serie) {
        String sql = "UPDATE series SET titulo=?, genero=?, duracion=?, temporadas=?, anio_estreno=?, calificacion=?, creador=? WHERE id=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, serie.getTitulo());
            stmt.setString(2, serie.getClasificacion());
            stmt.setInt(3, serie.getDuracion());
            stmt.setInt(4, serie.getTemporadas());
            stmt.setInt(5, serie.getAnioEstreno());
            stmt.setDouble(6, serie.getCalificacion());
            stmt.setString(7, serie.getCreador());
            stmt.setInt(8, serie.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM series WHERE id=?";
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
        String sql = "DELETE FROM series";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("¡Todos los registros de series han sido eliminados!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Control de duplicados
    public boolean existe(String titulo, int anioEstreno) {
        String sql = "SELECT COUNT(*) FROM series WHERE titulo = ? AND anio_estreno = ?";
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
