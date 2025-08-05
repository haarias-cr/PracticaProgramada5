package servidor;

import modelo.Pelicula;
import modelo.Serie;
import modelo.Documental;
import java.net.*;
import java.io.*;
import java.util.List;

public class ServidorBD {
    private static final int PUERTO = 6000;

    public static void main(String[] args) {
        PeliculaDAO peliculaDAO = new PeliculaDAO();
        SerieDAO serieDAO = new SerieDAO();
        DocumentalDAO documentalDAO = new DocumentalDAO();

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor BD escuchando en el puerto " + PUERTO);

            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + socketCliente.getInetAddress());
                new Thread(new ManejadorCliente(socketCliente, peliculaDAO, serieDAO, documentalDAO)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ManejadorCliente implements Runnable {
        private Socket socket;
        private PeliculaDAO peliculaDAO;
        private SerieDAO serieDAO;
        private DocumentalDAO documentalDAO;

        public ManejadorCliente(Socket socket, PeliculaDAO peliculaDAO, SerieDAO serieDAO, DocumentalDAO documentalDAO) {
            this.socket = socket;
            this.peliculaDAO = peliculaDAO;
            this.serieDAO = serieDAO;
            this.documentalDAO = documentalDAO;
        }

        @Override
        public void run() {
            try (
                    ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream())
            ) {
                String comando = (String) entrada.readObject();
                System.out.println("[ServidorBD] Comando recibido: " + comando);

                switch (comando) {
                    // --- PELICULAS ---
                    case "INSERTAR_PELICULA": {
                        Pelicula p = (Pelicula) entrada.readObject();
                        System.out.println("[ServidorBD] Insertando película: " + p.getTitulo());
                        boolean insertada = peliculaDAO.insertar(p);
                        salida.writeObject(insertada ? "OK" : "ERROR");
                        break;
                    }
                    case "LISTAR_PELICULAS": {
                        List<Pelicula> peliculas = peliculaDAO.listar();
                        salida.writeObject(peliculas);
                        break;
                    }
                    case "ACTUALIZAR_PELICULA": {
                        Pelicula p = (Pelicula) entrada.readObject();
                        System.out.println("[ServidorBD] Actualizando película ID: " + p.getId());
                        boolean actualizado = peliculaDAO.actualizar(p);
                        salida.writeObject(actualizado ? "OK" : "ERROR");
                        break;
                    }
                    case "ELIMINAR_PELICULA": {
                        int idEliminar = entrada.readInt();
                        System.out.println("[ServidorBD] Eliminando película ID: " + idEliminar);
                        boolean eliminado = peliculaDAO.eliminar(idEliminar);
                        salida.writeObject(eliminado ? "OK" : "ERROR");
                        break;
                    }

                    // --- SERIES ---
                    case "INSERTAR_SERIE": {
                        Serie s = (Serie) entrada.readObject();
                        System.out.println("[ServidorBD] Insertando serie: " + s.getTitulo());
                        boolean serieInsertada = serieDAO.insertar(s);
                        salida.writeObject(serieInsertada ? "OK" : "ERROR");
                        break;
                    }
                    case "LISTAR_SERIES": {
                        List<Serie> series = serieDAO.listar();
                        salida.writeObject(series);
                        break;
                    }
                    case "ACTUALIZAR_SERIE": {
                        Serie s = (Serie) entrada.readObject();
                        System.out.println("[ServidorBD] Actualizando serie ID: " + s.getId());
                        boolean actualizado = serieDAO.actualizar(s);
                        salida.writeObject(actualizado ? "OK" : "ERROR");
                        break;
                    }
                    case "ELIMINAR_SERIE": {
                        int idEliminar = entrada.readInt();
                        System.out.println("[ServidorBD] Eliminando serie ID: " + idEliminar);
                        boolean eliminado = serieDAO.eliminar(idEliminar);
                        salida.writeObject(eliminado ? "OK" : "ERROR");
                        break;
                    }

                    // --- DOCUMENTALES ---
                    case "INSERTAR_DOCUMENTAL": {
                        Documental d = (Documental) entrada.readObject();
                        System.out.println("[ServidorBD] Insertando documental: " + d.getTitulo());
                        boolean docInsertado = documentalDAO.insertar(d);
                        salida.writeObject(docInsertado ? "OK" : "ERROR");
                        break;
                    }
                    case "LISTAR_DOCUMENTALES": {
                        List<Documental> docs = documentalDAO.listar();
                        salida.writeObject(docs);
                        break;
                    }
                    case "ACTUALIZAR_DOCUMENTAL": {
                        Documental d = (Documental) entrada.readObject();
                        System.out.println("[ServidorBD] Actualizando documental ID: " + d.getId());
                        boolean actualizado = documentalDAO.actualizar(d);
                        salida.writeObject(actualizado ? "OK" : "ERROR");
                        break;
                    }
                    case "ELIMINAR_DOCUMENTAL": {
                        int idEliminar = entrada.readInt();
                        System.out.println("[ServidorBD] Eliminando documental ID: " + idEliminar);
                        boolean eliminado = documentalDAO.eliminar(idEliminar);
                        salida.writeObject(eliminado ? "OK" : "ERROR");
                        break;
                    }

                    default: {
                        System.out.println("[ServidorBD] Comando no reconocido: " + comando);
                        salida.writeObject("COMANDO_NO_RECONOCIDO");
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try { socket.close(); } catch (IOException ex) { ex.printStackTrace(); }
            }
        }
    }
}
