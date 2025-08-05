package modelo;

import java.io.Serializable;

public class Documental extends Audiovisual implements Comparable<Documental>, Serializable {
    private String tema;
    private int anioEstreno;
    private double calificacion;
    private String director;
    private int id;

    public Documental() {
        super();
        this.tema = "";
        this.anioEstreno = 0;
        this.calificacion = 0.0;
        this.director = "";
    }

    public Documental(String titulo, String tema, int duracion, int anioEstreno, double calificacion, String director) {
        super(titulo, duracion, tema); // 'tema' se guarda en clasificacion del padre
        this.tema = tema;
        this.anioEstreno = anioEstreno;
        this.calificacion = calificacion;
        this.director = director;
    }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public int getAnioEstreno() { return anioEstreno; }
    public void setAnioEstreno(int anioEstreno) { this.anioEstreno = anioEstreno; }

    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public int compareTo(Documental otro) {
        return this.getTitulo().compareTo(otro.getTitulo());
    }
}
