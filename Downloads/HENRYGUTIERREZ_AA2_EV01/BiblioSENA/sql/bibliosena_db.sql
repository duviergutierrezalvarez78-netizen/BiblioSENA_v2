-- ============================================================
-- Script de creacion de base de datos: BiblioSENA
-- Sistema de Gestion de Biblioteca - SENA
-- Aprendiz: Henry Duvier Gutierrez Alvarez
-- Ficha: 3186681
-- ============================================================

CREATE DATABASE IF NOT EXISTS bibliosena
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE bibliosena;

-- Tabla: libros
CREATE TABLE IF NOT EXISTS libros (
    id                  INT          NOT NULL AUTO_INCREMENT,
    titulo              VARCHAR(200) NOT NULL,
    autor               VARCHAR(100) NOT NULL,
    isbn                VARCHAR(20)  UNIQUE,
    anio_publicacion    INT,
    cantidad_disponible INT          NOT NULL DEFAULT 0,
    fecha_registro      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id              INT          NOT NULL AUTO_INCREMENT,
    nombre          VARCHAR(80)  NOT NULL,
    apellido        VARCHAR(80)  NOT NULL,
    email           VARCHAR(120) NOT NULL UNIQUE,
    telefono        VARCHAR(20),
    tipo_usuario    ENUM('LECTOR','ADMINISTRADOR') NOT NULL DEFAULT 'LECTOR',
    fecha_registro  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Datos de prueba: libros
INSERT INTO libros (titulo, autor, isbn, anio_publicacion, cantidad_disponible) VALUES
    ('El principito',                'Antoine de Saint-Exupery', '978-958-04-9349-3', 1943, 5),
    ('Cien anios de soledad',        'Gabriel Garcia Marquez',   '978-0-06-093129-4', 1967, 3),
    ('Fundamentos de programacion',  'Luis Joyanes Aguilar',     '978-84-481-9635-3', 2008, 8),
    ('Introduccion a los algoritmos','Thomas H. Cormen',          '978-0-26-203384-8', 2009, 2),
    ('Clean Code',                   'Robert C. Martin',          '978-0-13-235088-4', 2008, 4);

-- Datos de prueba: usuarios
INSERT INTO usuarios (nombre, apellido, email, telefono, tipo_usuario) VALUES
    ('Henry Duvier', 'Gutierrez Alvarez', 'henry.gutierrez@sena.edu.co', '3001234567', 'ADMINISTRADOR'),
    ('Maria Camila', 'Perez Torres',      'mcamila.perez@example.com',   '3109876543', 'LECTOR'),
    ('Juan Pablo',   'Ramirez Lopez',     'jp.ramirez@example.com',      '3201111222', 'LECTOR');
