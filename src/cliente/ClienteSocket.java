package cliente;

import modelo.Usuario;
import modelo.Pelicula;
import modelo.Serie;
import modelo.Documental;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteSocket {
    private static final String HOST = "localhost"; // Cambia por la IP si aplica
    private static final int PUERTO_USUARIOS = 5000;
    private static final int PUERTO_BD = 6000;

    // ---------- Métodos para Usuarios ----------
    public static boolean servidorDisponible() {
        try (Socket socket = new Socket(HOST, PUERTO_USUARIOS)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String crearUsuario(Usuario usuario) {
        try (
                Socket socket = new Socket(HOST, PUERTO_USUARIOS);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
        ) {
            salida.writeObject("CREAR_USUARIO");
            salida.writeObject(usuario);

            String respuesta = (String) entrada.readObject();
            return respuesta; // Puede ser "USUARIO_CREADO", "USUARIO_DUPLICADO" o "ERROR"
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public static Usuario iniciarSesion(String nombre, String contrasena) {
        try (
                Socket socket = new Socket(HOST, PUERTO_USUARIOS);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("LOGIN");
            salida.writeObject(nombre);
            salida.writeObject(contrasena);
            String respuesta = (String) entrada.readObject();
            if ("LOGIN_CORRECTO".equals(respuesta)) {
                return (Usuario) entrada.readObject();
            } else {
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ================= PELÍCULAS =================

    public static List<Pelicula> obtenerPeliculas() {
        List<Pelicula> lista = new ArrayList<>();
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("LISTAR_PELICULAS");
            Object obj = entrada.readObject();
            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    if (o instanceof Pelicula) {
                        lista.add((Pelicula) o);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean insertarPelicula(Pelicula pelicula) {
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("INSERTAR_PELICULA");
            salida.writeObject(pelicula);
            String respuesta = (String) entrada.readObject();
            return "OK".equals(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean actualizarPelicula(Pelicula pelicula) {
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("ACTUALIZAR_PELICULA");
            salida.writeObject(pelicula);
            String respuesta = (String) entrada.readObject();
            return "OK".equals(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean eliminarPelicula(int id) {
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("ELIMINAR_PELICULA");
            salida.writeInt(id);
            salida.flush();
            String respuesta = (String) entrada.readObject();
            return "OK".equals(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= SERIES =================

    public static List<Serie> obtenerSeries() {
        List<Serie> lista = new ArrayList<>();
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("LISTAR_SERIES");
            Object obj = entrada.readObject();
            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    if (o instanceof Serie) {
                        lista.add((Serie) o);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean insertarSerie(Serie serie) {
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("INSERTAR_SERIE");
            salida.writeObject(serie);
            String respuesta = (String) entrada.readObject();
            return "OK".equals(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean actualizarSerie(Serie serie) {
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("ACTUALIZAR_SERIE");
            salida.writeObject(serie);
            String respuesta = (String) entrada.readObject();
            return "OK".equals(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean eliminarSerie(int id) {
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("ELIMINAR_SERIE");
            salida.writeInt(id);
            salida.flush();
            String respuesta = (String) entrada.readObject();
            return "OK".equals(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= DOCUMENTALES =================

    public static List<Documental> obtenerDocumentales() {
        List<Documental> lista = new ArrayList<>();
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("LISTAR_DOCUMENTALES");
            Object obj = entrada.readObject();
            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    if (o instanceof Documental) {
                        lista.add((Documental) o);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean insertarDocumental(Documental documental) {
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("INSERTAR_DOCUMENTAL");
            salida.writeObject(documental);
            String respuesta = (String) entrada.readObject();
            return "OK".equals(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean actualizarDocumental(Documental documental) {
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("ACTUALIZAR_DOCUMENTAL");
            salida.writeObject(documental);
            String respuesta = (String) entrada.readObject();
            return "OK".equals(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean eliminarDocumental(int id) {
        try (
                Socket socket = new Socket(HOST, PUERTO_BD);
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            salida.writeObject("ELIMINAR_DOCUMENTAL");
            salida.writeInt(id);
            salida.flush();
            String respuesta = (String) entrada.readObject();
            return "OK".equals(respuesta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean servidorBDDisponible() {
        try (Socket socket = new Socket(HOST, PUERTO_BD)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
