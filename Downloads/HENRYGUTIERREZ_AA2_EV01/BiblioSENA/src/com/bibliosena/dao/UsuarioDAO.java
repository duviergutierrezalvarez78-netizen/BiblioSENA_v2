package com.bibliosena.dao;

import com.bibliosena.connection.DatabaseConnection;
import com.bibliosena.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos (DAO) para la entidad {@link Usuario}.
 * Implementa las operaciones CRUD usando JDBC contra la tabla {@code usuarios}.
 *
 * @author Henry Duvier Gutiérrez Álvarez
 * @version 1.0
 */
public class UsuarioDAO {

    // ── Sentencias SQL ───────────────────────────────────────────────────────
    private static final String SQL_INSERTAR =
        "INSERT INTO usuarios (nombre, apellido, email, telefono, tipo_usuario) "
        + "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_CONSULTAR_TODOS =
        "SELECT id, nombre, apellido, email, telefono, tipo_usuario "
        + "FROM usuarios ORDER BY apellido, nombre ASC";

    private static final String SQL_CONSULTAR_POR_ID =
        "SELECT id, nombre, apellido, email, telefono, tipo_usuario "
        + "FROM usuarios WHERE id = ?";

    private static final String SQL_CONSULTAR_POR_EMAIL =
        "SELECT id, nombre, apellido, email, telefono, tipo_usuario "
        + "FROM usuarios WHERE email = ?";

    private static final String SQL_ACTUALIZAR =
        "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, "
        + "telefono = ?, tipo_usuario = ? WHERE id = ?";

    private static final String SQL_ELIMINAR =
        "DELETE FROM usuarios WHERE id = ?";

    // ════════════════════════════════════════════════════════════════════════
    //  INSERCIÓN
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param usuario objeto {@link Usuario} con los datos a insertar (sin ID)
     * @return {@code true} si el registro fue exitoso, {@code false} en caso contrario
     */
    public boolean insertar(Usuario usuario) {
        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(
                 SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getTelefono());
            pstmt.setString(5, usuario.getTipoUsuario());

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet llaves = pstmt.getGeneratedKeys();
                if (llaves.next()) {
                    usuario.setId(llaves.getInt(1));
                    System.out.println("[UsuarioDAO] Usuario registrado con ID: " + usuario.getId());
                }
                return true;
            }

        } catch (SQLException ex) {
            System.err.println("[UsuarioDAO] Error al insertar usuario: " + ex.getMessage());
        }
        return false;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  CONSULTAS
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Retorna la lista completa de usuarios registrados.
     *
     * @return {@link List} de objetos {@link Usuario}; lista vacía si no hay registros
     */
    public List<Usuario> consultarTodos() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(SQL_CONSULTAR_TODOS);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearResultSet(rs));
            }

        } catch (SQLException ex) {
            System.err.println("[UsuarioDAO] Error al consultar usuarios: " + ex.getMessage());
        }
        return usuarios;
    }

    /**
     * Busca un usuario por su identificador único.
     *
     * @param id identificador del usuario
     * @return objeto {@link Usuario} si se encontró, {@code null} en caso contrario
     */
    public Usuario consultarPorId(int id) {
        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(SQL_CONSULTAR_POR_ID)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }

        } catch (SQLException ex) {
            System.err.println("[UsuarioDAO] Error al consultar usuario por ID: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Busca un usuario por su correo electrónico (campo único).
     *
     * @param email correo electrónico del usuario
     * @return objeto {@link Usuario} si se encontró, {@code null} en caso contrario
     */
    public Usuario consultarPorEmail(String email) {
        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(SQL_CONSULTAR_POR_EMAIL)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }

        } catch (SQLException ex) {
            System.err.println("[UsuarioDAO] Error al consultar usuario por email: " + ex.getMessage());
        }
        return null;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  ACTUALIZACIÓN
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario objeto {@link Usuario} con los nuevos datos y el ID correcto
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario
     */
    public boolean actualizar(Usuario usuario) {
        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(SQL_ACTUALIZAR)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getTelefono());
            pstmt.setString(5, usuario.getTipoUsuario());
            pstmt.setInt(6, usuario.getId());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("[UsuarioDAO] Usuario ID " + usuario.getId() + " actualizado.");
                return true;
            } else {
                System.out.println("[UsuarioDAO] No se encontró usuario con ID: " + usuario.getId());
            }

        } catch (SQLException ex) {
            System.err.println("[UsuarioDAO] Error al actualizar usuario: " + ex.getMessage());
        }
        return false;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  ELIMINACIÓN
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Elimina un usuario de la base de datos según su ID.
     *
     * @param id identificador del usuario a eliminar
     * @return {@code true} si se eliminó correctamente, {@code false} en caso contrario
     */
    public boolean eliminar(int id) {
        try (Connection conexion = DatabaseConnection.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(SQL_ELIMINAR)) {

            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("[UsuarioDAO] Usuario ID " + id + " eliminado.");
                return true;
            } else {
                System.out.println("[UsuarioDAO] No se encontró usuario con ID: " + id);
            }

        } catch (SQLException ex) {
            System.err.println("[UsuarioDAO] Error al eliminar usuario: " + ex.getMessage());
        }
        return false;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MÉTODO AUXILIAR
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link Usuario}.
     *
     * @param rs ResultSet posicionado en una fila válida
     * @return objeto {@link Usuario} con los datos de la fila
     * @throws SQLException si alguna columna no existe en el ResultSet
     */
    private Usuario mapearResultSet(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("email"),
            rs.getString("telefono"),
            rs.getString("tipo_usuario")
        );
    }
}
