package modelo;

import java.io.Serializable;

public class Pelicula extends Audiovisual implements Comparable<Pelicula>, Serializable {
    private String genero;
    private int anioEstreno;
    private double calificacion;
    private String director;
    private int id; // Si lo usas como clave primaria

    // Constructor por defecto
    public Pelicula() {
        super();
        this.genero = "";
        this.anioEstreno = 0;
        this.calificacion = 0.0;
        this.director = "";
    }

    // Constructor con parámetros
    public Pelicula(String titulo, String genero, int duracion, int anioEstreno, double calificacion, String director) {
        super(titulo, duracion, genero);
        this.genero = genero;
        this.anioEstreno = anioEstreno;
        this.calificacion = calificacion;
        this.director = director;
    }

    // Getters y Setters
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getAnioEstreno() { return anioEstreno; }
    public void setAnioEstreno(int anioEstreno) { this.anioEstreno = anioEstreno; }

    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Implementación de Comparable (ordenar por título)
    @Override
    public int compareTo(Pelicula otraPelicula) {
        return this.getTitulo().compareTo(otraPelicula.getTitulo());
    }
}
