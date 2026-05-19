package com.bibliosena.model;

/**
 * Clase modelo que representa un libro en el sistema BiblioSENA.
 * Mapea directamente la tabla {@code libros} de la base de datos.
 *
 * @author Henry Duvier Gutiérrez Álvarez
 * @version 1.0
 */
public class Libro {

    // ── Atributos ───────────────────────────────────────────────────────────
    private int    id;
    private String titulo;
    private String autor;
    private String isbn;
    private int    anioPublicacion;
    private int    cantidadDisponible;

    // ── Constructores ────────────────────────────────────────────────────────

    /** Constructor vacío requerido para instanciación genérica. */
    public Libro() {}

    /**
     * Constructor para crear un libro nuevo (sin ID, lo asigna la BD).
     *
     * @param titulo              título del libro
     * @param autor               nombre del autor
     * @param isbn                código ISBN
     * @param anioPublicacion     año de publicación
     * @param cantidadDisponible  ejemplares disponibles
     */
    public Libro(String titulo, String autor, String isbn,
                 int anioPublicacion, int cantidadDisponible) {
        this.titulo             = titulo;
        this.autor              = autor;
        this.isbn               = isbn;
        this.anioPublicacion    = anioPublicacion;
        this.cantidadDisponible = cantidadDisponible;
    }

    /**
     * Constructor completo, incluyendo el ID.
     *
     * @param id                  identificador único (PK)
     * @param titulo              título del libro
     * @param autor               nombre del autor
     * @param isbn                código ISBN
     * @param anioPublicacion     año de publicación
     * @param cantidadDisponible  ejemplares disponibles
     */
    public Libro(int id, String titulo, String autor, String isbn,
                 int anioPublicacion, int cantidadDisponible) {
        this.id                 = id;
        this.titulo             = titulo;
        this.autor              = autor;
        this.isbn               = isbn;
        this.anioPublicacion    = anioPublicacion;
        this.cantidadDisponible = cantidadDisponible;
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    public int getId()                        { return id; }
    public void setId(int id)                 { this.id = id; }

    public String getTitulo()                 { return titulo; }
    public void setTitulo(String titulo)      { this.titulo = titulo; }

    public String getAutor()                  { return autor; }
    public void setAutor(String autor)        { this.autor = autor; }

    public String getIsbn()                   { return isbn; }
    public void setIsbn(String isbn)          { this.isbn = isbn; }

    public int getAnioPublicacion()                       { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion)   { this.anioPublicacion = anioPublicacion; }

    public int getCantidadDisponible()                        { return cantidadDisponible; }
    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }

    // ── toString ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format(
            "Libro{id=%d, titulo='%s', autor='%s', isbn='%s', anio=%d, disponibles=%d}",
            id, titulo, autor, isbn, anioPublicacion, cantidadDisponible
        );
    }
}
