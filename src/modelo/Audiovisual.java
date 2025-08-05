package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Audiovisual implements Serializable {
    private String titulo;
    private int duracion;
    private String clasificacion;
    private ArrayList<String> comentarios;

    // Constructor por defecto
    public Audiovisual() {
        this.titulo = "";
        this.duracion = 0;
        this.clasificacion = "";
        this.comentarios = new ArrayList<>();
    }

    // Constructor con parámetros
    public Audiovisual(String titulo, int duracion, String clasificacion) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.comentarios = new ArrayList<>();
    }

    // Método estático corregido para agregar comentarios
    public static void agregarComentario(Audiovisual audiovisual, String comentario) {
        audiovisual.getComentarios().add(comentario);
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public ArrayList<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<String> comentarios) {
        this.comentarios = comentarios;
    }
}
