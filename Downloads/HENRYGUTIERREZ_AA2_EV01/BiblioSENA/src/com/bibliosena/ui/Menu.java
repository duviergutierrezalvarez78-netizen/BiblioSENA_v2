package com.bibliosena.ui;

import com.bibliosena.dao.LibroDAO;
import com.bibliosena.dao.UsuarioDAO;
import com.bibliosena.model.Libro;
import com.bibliosena.model.Usuario;

import java.util.List;
import java.util.Scanner;

/**
 * Clase responsable de la interfaz de usuario por consola del sistema BiblioSENA.
 * Presenta menús interactivos para gestionar libros y usuarios.
 *
 * @author Henry Duvier Gutiérrez Álvarez
 * @version 1.0
 */
public class Menu {

    // ── Dependencias ─────────────────────────────────────────────────────────
    private final LibroDAO   libroDAO;
    private final UsuarioDAO usuarioDAO;
    private final Scanner    scanner;

    // ── Separador visual ─────────────────────────────────────────────────────
    private static final String LINEA = "─────────────────────────────────────────────";

    // ── Constructor ──────────────────────────────────────────────────────────

    public Menu() {
        this.libroDAO   = new LibroDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.scanner    = new Scanner(System.in);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MENÚ PRINCIPAL
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Muestra el menú principal y redirige a los submenús correspondientes.
     * El bucle se mantiene hasta que el usuario elige salir.
     */
    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════════════════════╗");
            System.out.println("║          SISTEMA BiblioSENA  v1.0            ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║  1. Gestión de Libros                        ║");
            System.out.println("║  2. Gestión de Usuarios                      ║");
            System.out.println("║  0. Salir                                    ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("   Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1  -> mostrarMenuLibros();
                case 2  -> mostrarMenuUsuarios();
                case 0  -> System.out.println("\n[BiblioSENA] Sesión finalizada. ¡Hasta pronto!");
                default -> System.out.println("[!] Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 0);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MENÚ LIBROS
    // ════════════════════════════════════════════════════════════════════════

    /** Submenú de operaciones sobre libros. */
    private void mostrarMenuLibros() {
        int opcion;
        do {
            System.out.println("\n┌─ GESTIÓN DE LIBROS ──────────────────────────");
            System.out.println("│  1. Listar todos los libros");
            System.out.println("│  2. Buscar libro por ID");
            System.out.println("│  3. Buscar libro por título");
            System.out.println("│  4. Agregar nuevo libro");
            System.out.println("│  5. Actualizar libro");
            System.out.println("│  6. Eliminar libro");
            System.out.println("│  0. Volver al menú principal");
            System.out.println("└─────────────────────────────────────────────");
            System.out.print("   Opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1  -> listarTodosLosLibros();
                case 2  -> buscarLibroPorId();
                case 3  -> buscarLibroPorTitulo();
                case 4  -> agregarLibro();
                case 5  -> actualizarLibro();
                case 6  -> eliminarLibro();
                case 0  -> System.out.println("[<] Volviendo al menú principal...");
                default -> System.out.println("[!] Opción no válida.");
            }

        } while (opcion != 0);
    }

    // ── Operaciones CRUD de libros ────────────────────────────────────────

    private void listarTodosLosLibros() {
        System.out.println("\n" + LINEA);
        System.out.println(" LISTADO DE LIBROS");
        System.out.println(LINEA);

        List<Libro> libros = libroDAO.consultarTodos();
        if (libros.isEmpty()) {
            System.out.println(" No hay libros registrados.");
        } else {
            libros.forEach(l -> System.out.printf(
                " [%3d] %-40s | %-25s | Disp: %d%n",
                l.getId(), l.getTitulo(), l.getAutor(), l.getCantidadDisponible()
            ));
        }
        System.out.println(LINEA);
    }

    private void buscarLibroPorId() {
        System.out.print("\n  Ingrese el ID del libro: ");
        int id = leerEntero();

        Libro libro = libroDAO.consultarPorId(id);
        if (libro != null) {
            mostrarDetallesLibro(libro);
        } else {
            System.out.println("[!] No se encontró libro con ID: " + id);
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("\n  Ingrese el título o fragmento a buscar: ");
        String titulo = scanner.nextLine().trim();

        List<Libro> resultados = libroDAO.consultarPorTitulo(titulo);
        if (resultados.isEmpty()) {
            System.out.println("[!] No se encontraron libros con ese título.");
        } else {
            System.out.println("\n  Resultados encontrados: " + resultados.size());
            resultados.forEach(l -> System.out.printf(
                "  [%d] %s — %s (ISBN: %s)%n",
                l.getId(), l.getTitulo(), l.getAutor(), l.getIsbn()
            ));
        }
    }

    private void agregarLibro() {
        System.out.println("\n  ── AGREGAR LIBRO ──────────────────────────");
        System.out.print("  Título             : "); String titulo = scanner.nextLine().trim();
        System.out.print("  Autor              : "); String autor  = scanner.nextLine().trim();
        System.out.print("  ISBN               : "); String isbn   = scanner.nextLine().trim();
        System.out.print("  Año de publicación : "); int    anio   = leerEntero();
        System.out.print("  Cantidad disponible: "); int    cant   = leerEntero();

        Libro nuevoLibro = new Libro(titulo, autor, isbn, anio, cant);
        boolean exito = libroDAO.insertar(nuevoLibro);
        System.out.println(exito
            ? "  [✓] Libro agregado exitosamente."
            : "  [✗] No se pudo agregar el libro.");
    }

    private void actualizarLibro() {
        System.out.print("\n  ID del libro a actualizar: ");
        int id = leerEntero();

        Libro libro = libroDAO.consultarPorId(id);
        if (libro == null) {
            System.out.println("[!] Libro no encontrado con ID: " + id);
            return;
        }
        mostrarDetallesLibro(libro);

        System.out.println("  Ingrese los nuevos datos (Enter para mantener el actual):");
        System.out.print("  Nuevo título [" + libro.getTitulo() + "]: ");
        String titulo = scanner.nextLine().trim();
        if (!titulo.isEmpty()) libro.setTitulo(titulo);

        System.out.print("  Nuevo autor [" + libro.getAutor() + "]: ");
        String autor = scanner.nextLine().trim();
        if (!autor.isEmpty()) libro.setAutor(autor);

        System.out.print("  Nuevo ISBN [" + libro.getIsbn() + "]: ");
        String isbn = scanner.nextLine().trim();
        if (!isbn.isEmpty()) libro.setIsbn(isbn);

        System.out.print("  Nuevo año [" + libro.getAnioPublicacion() + "]: ");
        String anioStr = scanner.nextLine().trim();
        if (!anioStr.isEmpty()) libro.setAnioPublicacion(Integer.parseInt(anioStr));

        System.out.print("  Nueva cantidad [" + libro.getCantidadDisponible() + "]: ");
        String cantStr = scanner.nextLine().trim();
        if (!cantStr.isEmpty()) libro.setCantidadDisponible(Integer.parseInt(cantStr));

        boolean exito = libroDAO.actualizar(libro);
        System.out.println(exito
            ? "  [✓] Libro actualizado exitosamente."
            : "  [✗] No se pudo actualizar el libro.");
    }

    private void eliminarLibro() {
        System.out.print("\n  ID del libro a eliminar: ");
        int id = leerEntero();

        Libro libro = libroDAO.consultarPorId(id);
        if (libro == null) {
            System.out.println("[!] Libro no encontrado con ID: " + id);
            return;
        }

        System.out.println("  ¿Confirma la eliminación de '" + libro.getTitulo() + "'? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        if (confirmacion.equals("s")) {
            boolean exito = libroDAO.eliminar(id);
            System.out.println(exito
                ? "  [✓] Libro eliminado exitosamente."
                : "  [✗] No se pudo eliminar el libro.");
        } else {
            System.out.println("  Eliminación cancelada.");
        }
    }

    private void mostrarDetallesLibro(Libro libro) {
        System.out.println("\n  " + LINEA);
        System.out.println("  ID       : " + libro.getId());
        System.out.println("  Título   : " + libro.getTitulo());
        System.out.println("  Autor    : " + libro.getAutor());
        System.out.println("  ISBN     : " + libro.getIsbn());
        System.out.println("  Año      : " + libro.getAnioPublicacion());
        System.out.println("  Disp.    : " + libro.getCantidadDisponible());
        System.out.println("  " + LINEA);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MENÚ USUARIOS
    // ════════════════════════════════════════════════════════════════════════

    /** Submenú de operaciones sobre usuarios. */
    private void mostrarMenuUsuarios() {
        int opcion;
        do {
            System.out.println("\n┌─ GESTIÓN DE USUARIOS ────────────────────────");
            System.out.println("│  1. Listar todos los usuarios");
            System.out.println("│  2. Buscar usuario por ID");
            System.out.println("│  3. Buscar usuario por email");
            System.out.println("│  4. Registrar nuevo usuario");
            System.out.println("│  5. Actualizar usuario");
            System.out.println("│  6. Eliminar usuario");
            System.out.println("│  0. Volver al menú principal");
            System.out.println("└─────────────────────────────────────────────");
            System.out.print("   Opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1  -> listarTodosLosUsuarios();
                case 2  -> buscarUsuarioPorId();
                case 3  -> buscarUsuarioPorEmail();
                case 4  -> registrarUsuario();
                case 5  -> actualizarUsuario();
                case 6  -> eliminarUsuario();
                case 0  -> System.out.println("[<] Volviendo al menú principal...");
                default -> System.out.println("[!] Opción no válida.");
            }

        } while (opcion != 0);
    }

    // ── Operaciones CRUD de usuarios ──────────────────────────────────────

    private void listarTodosLosUsuarios() {
        System.out.println("\n" + LINEA);
        System.out.println(" LISTADO DE USUARIOS");
        System.out.println(LINEA);

        List<Usuario> usuarios = usuarioDAO.consultarTodos();
        if (usuarios.isEmpty()) {
            System.out.println(" No hay usuarios registrados.");
        } else {
            usuarios.forEach(u -> System.out.printf(
                " [%3d] %-30s | %-30s | %s%n",
                u.getId(), u.getNombreCompleto(), u.getEmail(), u.getTipoUsuario()
            ));
        }
        System.out.println(LINEA);
    }

    private void buscarUsuarioPorId() {
        System.out.print("\n  Ingrese el ID del usuario: ");
        int id = leerEntero();

        Usuario usuario = usuarioDAO.consultarPorId(id);
        if (usuario != null) {
            mostrarDetallesUsuario(usuario);
        } else {
            System.out.println("[!] No se encontró usuario con ID: " + id);
        }
    }

    private void buscarUsuarioPorEmail() {
        System.out.print("\n  Ingrese el email del usuario: ");
        String email = scanner.nextLine().trim();

        Usuario usuario = usuarioDAO.consultarPorEmail(email);
        if (usuario != null) {
            mostrarDetallesUsuario(usuario);
        } else {
            System.out.println("[!] No se encontró usuario con email: " + email);
        }
    }

    private void registrarUsuario() {
        System.out.println("\n  ── REGISTRAR USUARIO ──────────────────────");
        System.out.print("  Nombre     : "); String nombre   = scanner.nextLine().trim();
        System.out.print("  Apellido   : "); String apellido = scanner.nextLine().trim();
        System.out.print("  Email      : "); String email    = scanner.nextLine().trim();
        System.out.print("  Teléfono   : "); String telefono = scanner.nextLine().trim();
        System.out.print("  Tipo (LECTOR / ADMINISTRADOR): ");
        String tipo = scanner.nextLine().trim().toUpperCase();
        if (!tipo.equals(Usuario.TIPO_LECTOR) && !tipo.equals(Usuario.TIPO_ADMINISTRADOR)) {
            tipo = Usuario.TIPO_LECTOR;
            System.out.println("  [!] Tipo no reconocido. Se asignará LECTOR por defecto.");
        }

        Usuario nuevoUsuario = new Usuario(nombre, apellido, email, telefono, tipo);
        boolean exito = usuarioDAO.insertar(nuevoUsuario);
        System.out.println(exito
            ? "  [✓] Usuario registrado exitosamente."
            : "  [✗] No se pudo registrar el usuario.");
    }

    private void actualizarUsuario() {
        System.out.print("\n  ID del usuario a actualizar: ");
        int id = leerEntero();

        Usuario usuario = usuarioDAO.consultarPorId(id);
        if (usuario == null) {
            System.out.println("[!] Usuario no encontrado con ID: " + id);
            return;
        }
        mostrarDetallesUsuario(usuario);

        System.out.println("  Ingrese los nuevos datos (Enter para mantener el actual):");

        System.out.print("  Nuevo nombre [" + usuario.getNombre() + "]: ");
        String nombre = scanner.nextLine().trim();
        if (!nombre.isEmpty()) usuario.setNombre(nombre);

        System.out.print("  Nuevo apellido [" + usuario.getApellido() + "]: ");
        String apellido = scanner.nextLine().trim();
        if (!apellido.isEmpty()) usuario.setApellido(apellido);

        System.out.print("  Nuevo email [" + usuario.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) usuario.setEmail(email);

        System.out.print("  Nuevo teléfono [" + usuario.getTelefono() + "]: ");
        String telefono = scanner.nextLine().trim();
        if (!telefono.isEmpty()) usuario.setTelefono(telefono);

        boolean exito = usuarioDAO.actualizar(usuario);
        System.out.println(exito
            ? "  [✓] Usuario actualizado exitosamente."
            : "  [✗] No se pudo actualizar el usuario.");
    }

    private void eliminarUsuario() {
        System.out.print("\n  ID del usuario a eliminar: ");
        int id = leerEntero();

        Usuario usuario = usuarioDAO.consultarPorId(id);
        if (usuario == null) {
            System.out.println("[!] Usuario no encontrado con ID: " + id);
            return;
        }

        System.out.println("  ¿Confirma la eliminación de '" + usuario.getNombreCompleto() + "'? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        if (confirmacion.equals("s")) {
            boolean exito = usuarioDAO.eliminar(id);
            System.out.println(exito
                ? "  [✓] Usuario eliminado exitosamente."
                : "  [✗] No se pudo eliminar el usuario.");
        } else {
            System.out.println("  Eliminación cancelada.");
        }
    }

    private void mostrarDetallesUsuario(Usuario usuario) {
        System.out.println("\n  " + LINEA);
        System.out.println("  ID       : " + usuario.getId());
        System.out.println("  Nombre   : " + usuario.getNombreCompleto());
        System.out.println("  Email    : " + usuario.getEmail());
        System.out.println("  Teléfono : " + usuario.getTelefono());
        System.out.println("  Tipo     : " + usuario.getTipoUsuario());
        System.out.println("  " + LINEA);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  UTILIDADES
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Lee un número entero desde la consola con manejo de entrada inválida.
     *
     * @return entero ingresado por el usuario; -1 si la entrada no es válida
     */
    private int leerEntero() {
        try {
            String linea = scanner.nextLine().trim();
            return Integer.parseInt(linea);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
