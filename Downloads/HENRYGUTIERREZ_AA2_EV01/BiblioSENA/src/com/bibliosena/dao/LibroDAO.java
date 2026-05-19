package com.bibliosena.dao;

import com.bibliosena.connection.DatabaseConnection;
import com.bibliosena.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Libro}.
 * Implementa las operaciones CRUD usando JDBC contra la tabla {@code libros}.
 *
 * <p>Convenciones aplicadas:
 * <ul>
 *   <li>Uso de {@code PreparedStatement} para prevenir inyección SQL.</li>
 *   <li>Cierre de recursos en bloques {@code finally} o try-with-resources.</li>
 *   <li>Métodos nombrados en camelCase con verbos descriptivos.</li>
 * </ul>
 *
 * @author Henry Duvier Gutiérrez Álvarez
 * @version 1.0
 */
public class LibroDAO {

    // ── Sentencias SQL ───────────────────────────────────────────────────────
    private static final String SQL_INSERTAR =
        "INSERT INTO libros (titulo, autor, isbn, anio_publicacion, cantidad_disponible) "
        + "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_CONSULTAR_TODOS =
        "SELECT id, titulo, autor, isbn, anio_publicacion, cantidad_disponible "
        + "FROM libros ORDER BY titulo ASC";

    private static final String SQL_CONSULTAR_POR_ID =
        "SELECT id, titulo, autor, isbn, anio_publicacion, cantidad_disponible "
        + "FROM libros WHERE id = ?";

    private static final String SQL_CONSULTAR_POR_TITULO =
        "SELECT id, titulo, autor, isbn, anio_publicacion, cantidad_disponible "
        + "FROM libros WHERE titulo LIKE ? ORDER BY titulo ASC";

    private static final String SQL_ACTUALIZAR =
        "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, "
        + "anio_publicacion = ?, cantidad_disponible = ? WHERE id = ?";

    private static final String SQL_ELIMINAR =
        "DELETE FROM libros WHERE id = ?";

    // ════════════════════════════════════════════════════════════════════════
    //  INSERCIÓN
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Inserta un nuevo libro en la base de datos.
     *
     * @param libro objeto {@link Libro} con los datos a insertar (sin ID)
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario
     */
    public boolean insertar(Libro libro) {
        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(
                 SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getIsbn());
            pstmt.setInt(4, libro.getAnioPublicacion());
            pstmt.setInt(5, libro.getCantidadDisponible());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet llaves = pstmt.getGeneratedKeys();
                if (llaves.next()) {
                    libro.setId(llaves.getInt(1));
                    System.out.println("[LibroDAO] Libro insertado con ID: " + libro.getId());
                }
                return true;
            }

        } catch (SQLException ex) {
            System.err.println("[LibroDAO] Error al insertar libro: " + ex.getMessage());
        }
        return false;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  CONSULTAS
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Retorna la lista completa de libros registrados, ordenada alfabéticamente.
     *
     * @return {@link List} de objetos {@link Libro}; lista vacía si no hay registros
     */
    public List<Libro> consultarTodos() {
        List<Libro> libros = new ArrayList<>();

        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(SQL_CONSULTAR_TODOS);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                libros.add(mapearResultSet(rs));
            }

        } catch (SQLException ex) {
            System.err.println("[LibroDAO] Error al consultar libros: " + ex.getMessage());
        }
        return libros;
    }

    /**
     * Busca un libro por su identificador único.
     *
     * @param id identificador del libro
     * @return objeto {@link Libro} si se encontró, {@code null} en caso contrario
     */
    public Libro consultarPorId(int id) {
        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(SQL_CONSULTAR_POR_ID)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }

        } catch (SQLException ex) {
            System.err.println("[LibroDAO] Error al consultar libro por ID: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Busca libros cuyo título contenga el texto indicado (búsqueda parcial).
     *
     * @param fragmentoTitulo texto a buscar dentro del título
     * @return lista de libros que coinciden con la búsqueda
     */
    public List<Libro> consultarPorTitulo(String fragmentoTitulo) {
        List<Libro> libros = new ArrayList<>();

        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(SQL_CONSULTAR_POR_TITULO)) {

            pstmt.setString(1, "%" + fragmentoTitulo + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapearResultSet(rs));
                }
            }

        } catch (SQLException ex) {
            System.err.println("[LibroDAO] Error al buscar libros por título: " + ex.getMessage());
        }
        return libros;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  ACTUALIZACIÓN
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Actualiza todos los campos de un libro existente en la base de datos.
     *
     * @param libro objeto {@link Libro} con los nuevos datos y el ID correcto
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario
     */
    public boolean actualizar(Libro libro) {
        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(SQL_ACTUALIZAR)) {

            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getIsbn());
            pstmt.setInt(4, libro.getAnioPublicacion());
            pstmt.setInt(5, libro.getCantidadDisponible());
            pstmt.setInt(6, libro.getId());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("[LibroDAO] Libro ID " + libro.getId() + " actualizado.");
                return true;
            } else {
                System.out.println("[LibroDAO] No se encontró libro con ID: " + libro.getId());
            }

        } catch (SQLException ex) {
            System.err.println("[LibroDAO] Error al actualizar libro: " + ex.getMessage());
        }
        return false;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  ELIMINACIÓN
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Elimina un libro de la base de datos según su ID.
     *
     * @param id identificador del libro a eliminar
     * @return {@code true} si se eliminó correctamente, {@code false} en caso contrario
     */
    public boolean eliminar(int id) {
        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(SQL_ELIMINAR)) {

            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("[LibroDAO] Libro ID " + id + " eliminado.");
                return true;
            } else {
                System.out.println("[LibroDAO] No se encontró libro con ID: " + id);
            }

        } catch (SQLException ex) {
            System.err.println("[LibroDAO] Error al eliminar libro: " + ex.getMessage());
        }
        return false;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MÉTODO AUXILIAR
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link Libro}.
     *
     * @param rs ResultSet posicionado en una fila válida
     * @return objeto {@link Libro} con los datos de la fila
     * @throws SQLException si alguna columna no existe en el ResultSet
     */
    private Libro mapearResultSet(ResultSet rs) throws SQLException {
        return new Libro(
            rs.getInt("id"),
            rs.getString("titulo"),
            rs.getString("autor"),
            rs.getString("isbn"),
            rs.getInt("anio_publicacion"),
            rs.getInt("cantidad_disponible")
        );
    }
}
