package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    private String nombre;
    private String contrasena;
    private ArrayList<Audiovisual> favoritos;

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.favoritos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    // **Agrega este setter**
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public ArrayList<Audiovisual> getFavoritos() {
        return favoritos;
    }

    public void agregarFavorito(Audiovisual a) {
        favoritos.add(a);
    }

    public void eliminarFavorito(String titulo) {
        favoritos.removeIf(a -> a.getTitulo().equalsIgnoreCase(titulo));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("modelo.Usuario: ").append(nombre).append("\n");
        sb.append("Favoritos:\n");
        for (Audiovisual a : favoritos) {
            sb.append("- ").append(a.getTitulo()).append(" (").append(a.getClass().getSimpleName()).append(")\n");
        }
        return sb.toString();
    }
}
