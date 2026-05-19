import com.bibliosena.connection.DatabaseConnection;
import com.bibliosena.ui.Menu;

/**
 * Punto de entrada principal del sistema BiblioSENA.
 *
 * <p><b>Ejecución:</b>
 * <pre>
 *   javac -cp lib/mysql-connector-java-8.x.x.jar -d bin src/**&#47;*.java Main.java
 *   java  -cp bin:lib/mysql-connector-java-8.x.x.jar Main
 * </pre>
 *
 * <p>Asegúrese de haber ejecutado el script {@code sql/bibliosena_db.sql}
 * en MySQL antes de iniciar la aplicación.
 *
 * @author Henry Duvier Gutiérrez Álvarez
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   Iniciando BiblioSENA — Sistema de Gestión  ║");
        System.out.println("║   Aprendiz : Henry Duvier Gutiérrez Álvarez  ║");
        System.out.println("║   Ficha    : 3186681 — SENA                  ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        try {
            // Verificar la conexión antes de mostrar el menú
            DatabaseConnection.obtenerConexion();

            Menu menu = new Menu();
            menu.mostrarMenuPrincipal();

        } catch (Exception ex) {
            System.err.println("\n[ERROR] No se pudo iniciar el sistema:");
            System.err.println(ex.getMessage());
            System.err.println("\nVerifique que:");
            System.err.println("  1. MySQL está corriendo en localhost:3306");
            System.err.println("  2. La base de datos 'bibliosena' existe (ejecute sql/bibliosena_db.sql)");
            System.err.println("  3. El archivo mysql-connector-java está en /lib");
        } finally {
            DatabaseConnection.cerrarConexion();
        }
    }
}
