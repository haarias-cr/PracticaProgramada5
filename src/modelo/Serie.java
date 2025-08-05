package modelo;

import java.io.Serializable;

public class Serie extends Audiovisual implements Comparable<Serie>, Serializable {
    private int temporadas;
    private int anioEstreno;
    private double calificacion;
    private String creador;
    private int id;

    public Serie(String titulo, String genero, int temporadas, int anio, double calif, String creador) {
        super();
        this.temporadas = 0;
        this.anioEstreno = 0;
        this.calificacion = 0.0;
        this.creador = "";
    }

    public Serie(String titulo, String genero, int duracion, int temporadas, int anioEstreno, double calificacion, String creador) {
        super(titulo, duracion, genero);
        this.temporadas = temporadas;
        this.anioEstreno = anioEstreno;
        this.calificacion = calificacion;
        this.creador = creador;
    }

    public int getTemporadas() { return temporadas; }
    public void setTemporadas(int temporadas) { this.temporadas = temporadas; }

    public int getAnioEstreno() { return anioEstreno; }
    public void setAnioEstreno(int anioEstreno) { this.anioEstreno = anioEstreno; }

    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }

    public String getCreador() { return creador; }
    public void setCreador(String creador) { this.creador = creador; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public int compareTo(Serie otraSerie) {
        return this.getTitulo().compareTo(otraSerie.getTitulo());
    }
}
