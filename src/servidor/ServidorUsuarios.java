package servidor;

import modelo.Usuario;
import modelo.UsuarioNoEncontradoException;

import java.io.*;
import java.net.*;

public class ServidorUsuarios {

    private static final int PUERTO = 5000;
    private static GestorUsuarios gestor = new GestorUsuarios();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor escuchando en el puerto " + PUERTO + "...");

            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Cliente conectado desde: " + socketCliente.getInetAddress());
                new Thread(new ManejadorCliente(socketCliente)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Clase interna que maneja cada conexión de cliente
    private static class ManejadorCliente implements Runnable {
        private Socket socket;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                    ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            ) {
                String tipoPeticion = (String) entrada.readObject();

                if ("CREAR_USUARIO".equals(tipoPeticion)) {
                    Usuario nuevo = (Usuario) entrada.readObject();
                    boolean creado = gestor.agregarUsuario(nuevo);
                    if (creado) {
                        salida.writeObject("USUARIO_CREADO");
                    } else {
                        salida.writeObject("USUARIO_DUPLICADO");
                    }

                } else if ("LOGIN".equals(tipoPeticion)) {
                    String nombre = (String) entrada.readObject();
                    String contrasena = (String) entrada.readObject();

                    try {
                        Usuario u = gestor.buscarUsuario(nombre, contrasena);
                        salida.writeObject("LOGIN_CORRECTO");
                        salida.writeObject(u); // opcional, se puede enviar el usuario completo
                    } catch (UsuarioNoEncontradoException ex) {
                        salida.writeObject("LOGIN_FALLIDO");
                    }

                } else {
                    salida.writeObject("PETICION_NO_RECONOCIDA");
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) { e.printStackTrace(); }
            }
        }
    }
}
