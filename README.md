# GA6-220501096-AA4-EV03 — Diseño Front-End BiblioSENA

## Información del Aprendiz

| Campo | Detalle |
|---|---|
| **Nombre** | Henry Duvier Gutiérrez Álvarez |
| **Ficha** | 3186681 |
| **Programa** | Análisis y Desarrollo de Software (ADSO) |
| **Centro** | SENA — Servicio Nacional de Aprendizaje |
| **Evidencia** | GA6-220501096-AA4-EV03 |
| **Fecha** | Abril 2026 |

---

## Descripción del Proyecto

**BiblioSENA** es el sistema de gestión de biblioteca del SENA. Este diseño front-end implementa las 6 vistas principales del sistema, aplicando principios de usabilidad, accesibilidad y diseño visual institucional.

---

## Estructura de Archivos

```
GA6-220501096-AA4-EV03-BiblioSENA/
├── index.html          ← Archivo principal (todas las vistas)
├── css/
│   └── styles.css      ← Hoja de estilos global
├── js/
│   └── app.js          ← Lógica de interactividad
├── img/                ← Carpeta para imágenes (vacía)
└── README.md           ← Este archivo
```

---

## Vistas Implementadas

| Vista | Descripción |
|---|---|
| **Login** | Autenticación con validación de formulario |
| **Dashboard** | Estadísticas, gráfica de préstamos y actividad reciente |
| **Libros** | CRUD completo del catálogo con filtros y paginación |
| **Préstamos** | Registro y gestión de préstamos con devolución |
| **Reportes** | Gráficas de barras y circular + tablas de análisis |
| **Perfil** | Edición de datos personales y cambio de contraseña |

---

## Tecnologías Utilizadas

- **HTML5** — Estructura semántica con etiquetas `<main>`, `<section>`, `<article>`, `<nav>`, `<header>`, `<footer>`, `<dialog>`, `<figure>`, `<time>`, `<details>`, `<summary>`
- **CSS3** — Variables CSS, Flexbox, Grid, animaciones, diseño responsivo, media queries
- **JavaScript (ES6+)** — Módulos, clases, arrow functions, destructuring, template literals
- **Chart.js 4** — Gráficas de barras y doughnut

---

## Principios de Usabilidad Aplicados

1. **Visibilidad del estado del sistema** — Badges de estado, toasts de confirmación
2. **Correspondencia con el mundo real** — Terminología bibliotecaria estándar
3. **Control y libertad del usuario** — Botones cancelar, modales cerrables
4. **Consistencia y estándares** — Paleta verde SENA, tipografía uniforme
5. **Prevención de errores** — Validación de formularios con mensajes claros
6. **Reconocimiento antes que recuerdo** — Iconos descriptivos, etiquetas visibles
7. **Flexibilidad y eficiencia** — Filtros, búsqueda, acciones rápidas
8. **Diseño estético y minimalista** — Interfaz limpia sin elementos innecesarios
9. **Ayuda a reconocer errores** — Mensajes de error descriptivos y accesibles
10. **Ayuda y documentación** — Tooltips y textos de ayuda en formularios

---

## Accesibilidad (WCAG 2.1)

- Atributos `aria-label`, `aria-labelledby`, `aria-required`, `aria-live`, `aria-current`
- Etiquetas `<label>` asociadas a todos los campos de formulario
- Tablas con `<caption>`, `<thead>`, `<tfoot>` y atributos `scope`
- Imágenes con atributo `alt` descriptivo
- Elemento `<dialog>` nativo del navegador con `aria-modal`
- Clase `.sr-only` para contenido solo para lectores de pantalla
- Contraste de color superior a 4.5:1 (WCAG AA)

---

## Cómo Ejecutar

1. Abrir el archivo `index.html` en cualquier navegador moderno (Chrome, Firefox, Edge, Safari)
2. No requiere servidor web ni instalación de dependencias
3. Las fuentes se cargan desde Google Fonts (requiere conexión a internet)
4. Chart.js se carga desde CDN (requiere conexión a internet)

### Credenciales de Demostración

- **Correo:** `admin@sena.edu.co`
- **Contraseña:** `sena2026`

---

## Diseño Visual

- **Filosofía:** Profesional Minimalista "Claridad Funcional"
- **Color primario:** Verde SENA `#006633`
- **Sidebar:** Oscuro `#1e2532` para reducir fatiga visual
- **Tipografía:** Outfit (display/body) + JetBrains Mono (código)
- **Layout:** Sidebar fijo + área de contenido con cards sobre fondo gris claro
