package com.bibliosena.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con la base de datos MySQL.
 * Implementa el patrón Singleton para garantizar una única instancia de conexión.
 *
 * @author Henry Duvier Gutiérrez Álvarez
 * @version 1.0
 */
public class DatabaseConnection {

    // ── Parámetros de conexión ──────────────────────────────────────────────
    private static final String URL      = "jdbc:mysql://localhost:3306/bibliosena"
                                         + "?useSSL=false&serverTimezone=America/Bogota"
                                         + "&allowPublicKeyRetrieval=true";
    private static final String USUARIO  = "root";
    private static final String CLAVE    = "1035@";          // Cambiar según el entorno

    // ── Instancia única (Singleton) ─────────────────────────────────────────
    private static Connection instancia = null;

    /** Constructor privado para evitar instanciación directa. */
    private DatabaseConnection() {}

    /**
     * Retorna la conexión activa con la base de datos.
     * Si no existe o está cerrada, crea una nueva.
     *
     * @return objeto {@link Connection} listo para usar
     * @throws SQLException si el driver no se encuentra o la conexión falla
     */
    public static Connection obtenerConexion() throws SQLException {
        if (instancia == null || instancia.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                instancia = DriverManager.getConnection(URL, USUARIO, CLAVE);
                System.out.println("[BiblioSENA] Conexión establecida con MySQL.");
            } catch (ClassNotFoundException ex) {
                throw new SQLException(
                    "Driver MySQL no encontrado. Verifique que mysql-connector-java esté en /lib\n"
                    + ex.getMessage()
                );
            }
        }
        return instancia;
    }

    /**
     * Cierra la conexión activa con la base de datos, si existe.
     */
    public static void cerrarConexion() {
        if (instancia != null) {
            try {
                instancia.close();
                instancia = null;
                System.out.println("[BiblioSENA] Conexión cerrada correctamente.");
            } catch (SQLException ex) {
                System.err.println("[BiblioSENA] Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }
}
