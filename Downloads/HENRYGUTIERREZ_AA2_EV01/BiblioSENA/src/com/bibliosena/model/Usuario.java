package com.bibliosena.model;

/**
 * Clase modelo que representa un usuario del sistema BiblioSENA.
 * Mapea directamente la tabla {@code usuarios} de la base de datos.
 *
 * @author Henry Duvier Gutiérrez Álvarez
 * @version 1.0
 */
public class Usuario {

    // ── Tipos de usuario disponibles ────────────────────────────────────────
    public static final String TIPO_LECTOR        = "LECTOR";
    public static final String TIPO_ADMINISTRADOR = "ADMINISTRADOR";

    // ── Atributos ───────────────────────────────────────────────────────────
    private int    id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String tipoUsuario;

    // ── Constructores ────────────────────────────────────────────────────────

    /** Constructor vacío. */
    public Usuario() {}

    /**
     * Constructor para registrar un usuario nuevo (sin ID).
     *
     * @param nombre       primer nombre
     * @param apellido     apellido
     * @param email        correo electrónico (único)
     * @param telefono     número de teléfono
     * @param tipoUsuario  rol: LECTOR o ADMINISTRADOR
     */
    public Usuario(String nombre, String apellido, String email,
                   String telefono, String tipoUsuario) {
        this.nombre      = nombre;
        this.apellido    = apellido;
        this.email       = email;
        this.telefono    = telefono;
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Constructor completo, incluyendo el ID.
     *
     * @param id           identificador único (PK)
     * @param nombre       primer nombre
     * @param apellido     apellido
     * @param email        correo electrónico (único)
     * @param telefono     número de teléfono
     * @param tipoUsuario  rol: LECTOR o ADMINISTRADOR
     */
    public Usuario(int id, String nombre, String apellido, String email,
                   String telefono, String tipoUsuario) {
        this.id          = id;
        this.nombre      = nombre;
        this.apellido    = apellido;
        this.email       = email;
        this.telefono    = telefono;
        this.tipoUsuario = tipoUsuario;
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }

    public String getNombre()                   { return nombre; }
    public void setNombre(String nombre)        { this.nombre = nombre; }

    public String getApellido()                 { return apellido; }
    public void setApellido(String apellido)    { this.apellido = apellido; }

    public String getEmail()                    { return email; }
    public void setEmail(String email)          { this.email = email; }

    public String getTelefono()                 { return telefono; }
    public void setTelefono(String telefono)    { this.telefono = telefono; }

    public String getTipoUsuario()                      { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario)      { this.tipoUsuario = tipoUsuario; }

    // ── Nombre completo ──────────────────────────────────────────────────────

    /** Retorna el nombre completo del usuario (nombre + apellido). */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    // ── toString ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format(
            "Usuario{id=%d, nombre='%s %s', email='%s', tipo='%s'}",
            id, nombre, apellido, email, tipoUsuario
        );
    }
}
