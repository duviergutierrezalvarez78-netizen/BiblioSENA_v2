# BiblioSENA — Sistema de Gestión de Biblioteca

**Evidencia:** GA7-220501096-AA2-EV01  
**Aprendiz:** Henry Duvier Gutiérrez Álvarez  
**Ficha:** 3186681  
**Instructor:** William Fernando Muñoz Muñoz  
**Programa:** Tecnología en Análisis y Desarrollo de Software — SENA  

---

## Repositorio en GitHub

🔗 **https://github.com/TU_USUARIO/BiblioSENA**  
*(Reemplaza TU_USUARIO con tu nombre de usuario de GitHub)*

---

## Descripción

Sistema de gestión de biblioteca desarrollado en Java con conexión a MySQL mediante JDBC.  
Permite realizar operaciones CRUD sobre libros y usuarios registrados en el sistema.

---

## Estructura del Proyecto

```
BiblioSENA/
├── Main.java                                  ← Punto de entrada
├── src/
│   └── com/
│       └── bibliosena/
│           ├── connection/
│           │   └── DatabaseConnection.java    ← Singleton JDBC
│           ├── model/
│           │   ├── Libro.java                 ← Entidad libro
│           │   └── Usuario.java               ← Entidad usuario
│           ├── dao/
│           │   ├── LibroDAO.java              ← CRUD libros
│           │   └── UsuarioDAO.java            ← CRUD usuarios
│           └── ui/
│               └── Menu.java                  ← Interfaz consola
├── sql/
│   └── bibliosena_db.sql                      ← Script BD MySQL
└── lib/
    └── mysql-connector-java-8.x.x.jar         ← Driver JDBC (descargar)
```

---

## Requisitos Previos

- Java JDK 11 o superior
- MySQL 8.x corriendo en `localhost:3306`
- Driver JDBC: `mysql-connector-java` (descargar desde [Maven Repository](https://mvnrepository.com/artifact/mysql/mysql-connector-java))

---

## Configuración

### 1. Crear la base de datos

Abrir MySQL Workbench o la consola de MySQL y ejecutar:

```sql
SOURCE ruta/a/BiblioSENA/sql/bibliosena_db.sql;
```

### 2. Colocar el Driver JDBC

Descargar `mysql-connector-java-8.x.x.jar` y colocarlo en la carpeta `/lib/`.

### 3. Configurar credenciales (si es necesario)

Editar `src/com/bibliosena/connection/DatabaseConnection.java`:

```java
private static final String USUARIO = "root";   // Tu usuario MySQL
private static final String CLAVE   = "";        // Tu contraseña MySQL
```

---

## Compilación y Ejecución

### Desde terminal (Linux/Mac)

```bash
# Compilar
javac -cp lib/mysql-connector-java-8.x.x.jar -d bin \
      Main.java src/com/bibliosena/**/*.java

# Ejecutar
java -cp "bin:lib/mysql-connector-java-8.x.x.jar" Main
```

### Desde terminal (Windows)

```bat
javac -cp lib\mysql-connector-java-8.x.x.jar -d bin ^
      Main.java src\com\bibliosena\**\*.java

java -cp "bin;lib\mysql-connector-java-8.x.x.jar" Main
```

### Desde NetBeans / IntelliJ

1. Abrir el proyecto como proyecto Java existente.
2. Agregar `mysql-connector-java-8.x.x.jar` a las librerías del proyecto.
3. Ejecutar `Main.java`.

---

## Funcionalidades

### Gestión de Libros
| Operación    | Descripción                                |
|--------------|--------------------------------------------|
| Inserción    | Agrega un nuevo libro a la biblioteca       |
| Consulta     | Lista todos / busca por ID o título         |
| Actualización| Modifica los datos de un libro existente    |
| Eliminación  | Elimina un libro por su ID                  |

### Gestión de Usuarios
| Operación    | Descripción                                |
|--------------|--------------------------------------------|
| Inserción    | Registra un nuevo usuario (lector/admin)    |
| Consulta     | Lista todos / busca por ID o email          |
| Actualización| Modifica los datos de un usuario existente  |
| Eliminación  | Elimina un usuario por su ID                |

---

## Estándares de Codificación Aplicados

- **Paquetes:** minúsculas, estructura jerárquica (`com.bibliosena.*`)
- **Clases:** PascalCase (`LibroDAO`, `DatabaseConnection`)
- **Métodos:** camelCase con verbo descriptivo (`consultarPorId`, `insertar`)
- **Variables:** camelCase (`cantidadDisponible`, `filasAfectadas`)
- **Constantes:** SNAKE_CASE mayúsculas (`SQL_INSERTAR`, `TIPO_LECTOR`)
- **Documentación:** Javadoc en todas las clases y métodos públicos

---

*Evidencia desarrollada con base en los artefactos previos del ciclo de software: diagrama de clases, casos de uso, historias de usuario y prototipos del sistema BiblioSENA.*
