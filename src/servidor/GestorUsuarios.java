package servidor;


import java.io.*;
import java.util.ArrayList;
import modelo.Usuario;
import modelo.UsuarioNoEncontradoException;
import util.PasswordUtil;  // <- Recuerda tener este import

public class GestorUsuarios {
    private static final String ARCHIVO = "usuarios.dat";
    private ArrayList<Usuario> usuarios;

    public GestorUsuarios() {
        usuarios = cargarUsuarios();
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Agrega un usuario solo si NO existe uno con el mismo nombre (case-insensitive).
     * Guarda la contraseña hasheada.
     */
    public boolean agregarUsuario(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(usuario.getNombre())) {
                return false; // Ya existe un usuario con ese nombre
            }
        }
        // Hashear la contraseña antes de guardar
        String hash = PasswordUtil.hash(usuario.getContrasena());
        usuario.setContrasena(hash);
        usuarios.add(usuario);
        guardarUsuarios();
        return true;
    }

    /**
     * Busca usuario por nombre y contraseña.
     * Compara la contraseña hasheada, NO texto plano.
     */
    public Usuario buscarUsuario(String nombre, String contrasena) throws UsuarioNoEncontradoException {
        String hash = PasswordUtil.hash(contrasena);
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                if (u.getContrasena().equals(hash)) {
                    return u;
                } else {
                    throw new UsuarioNoEncontradoException("Contraseña incorrecta.");
                }
            }
        }
        throw new UsuarioNoEncontradoException("Usuario no encontrado.");
    }

    public void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Usuario> cargarUsuarios() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            return (ArrayList<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
