package servidor;

import modelo.Pelicula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    public boolean insertar(Pelicula pelicula) {
        // VALIDACIONES DE ENTRADA
        if (pelicula.getTitulo() == null || pelicula.getTitulo().trim().isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return false;
        }
        if (pelicula.getGenero() == null || pelicula.getGenero().trim().isEmpty()) {
            System.out.println("El género no puede estar vacío.");
            return false;
        }
        if (pelicula.getDuracion() <= 0) {
            System.out.println("La duración debe ser mayor a cero.");
            return false;
        }
        if (pelicula.getAnioEstreno() <= 1800) {
            System.out.println("El año de estreno debe ser válido.");
            return false;
        }
        if (pelicula.getCalificacion() < 0 || pelicula.getCalificacion() > 10) {
            System.out.println("La calificación debe estar entre 0 y 10.");
            return false;
        }
        if (pelicula.getDirector() == null || pelicula.getDirector().trim().isEmpty()) {
            System.out.println("El director no puede estar vacío.");
            return false;
        }

        // CONTROL DE DUPLICADOS
        if (existe(pelicula.getTitulo(), pelicula.getAnioEstreno())) {
            System.out.println("Ya existe una película con ese título y año.");
            return false;
        }

        String sql = "INSERT INTO peliculas (titulo, genero, duracion, anio_estreno, calificacion, director) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getGenero());
            stmt.setInt(3, pelicula.getDuracion());
            stmt.setInt(4, pelicula.getAnioEstreno());
            stmt.setDouble(5, pelicula.getCalificacion());
            stmt.setString(6, pelicula.getDirector());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Pelicula> listar() {
        List<Pelicula> lista = new ArrayList<>();
        String sql = "SELECT * FROM peliculas";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pelicula p = new Pelicula(
                        rs.getString("titulo"),
                        rs.getString("genero"),
                        rs.getInt("duracion"),
                        rs.getInt("anio_estreno"),
                        rs.getDouble("calificacion"),
                        rs.getString("director")
                );
                p.setId(rs.getInt("id"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(Pelicula pelicula) {
        String sql = "UPDATE peliculas SET titulo=?, genero=?, duracion=?, anio_estreno=?, calificacion=?, director=? WHERE id=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getGenero());
            stmt.setInt(3, pelicula.getDuracion());
            stmt.setInt(4, pelicula.getAnioEstreno());
            stmt.setDouble(5, pelicula.getCalificacion());
            stmt.setString(6, pelicula.getDirector());
            stmt.setInt(7, pelicula.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM peliculas WHERE id=?";
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
        String sql = "DELETE FROM peliculas";
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("¡Todos los registros han sido eliminados!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método de control de duplicados
    public boolean existe(String titulo, int anioEstreno) {
        String sql = "SELECT COUNT(*) FROM peliculas WHERE titulo = ? AND anio_estreno = ?";
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
