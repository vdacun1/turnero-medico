-- Archivo de creación de base de datos para el sistema Turnero Médico
-- Ejecutar este script en MySQL para crear la estructura necesaria

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS turnero_medico 
DEFAULT CHARACTER SET utf8mb4 
DEFAULT COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE turnero_medico;

-- Tabla de médicos
-- Hereda conceptualmente de una clase Persona con nombre, apellido y dni
CREATE TABLE IF NOT EXISTS medicos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  apellido VARCHAR(100) NOT NULL,
  dni VARCHAR(20) NOT NULL UNIQUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_medicos_dni (dni),
  INDEX idx_medicos_nombre (nombre, apellido)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Datos de ejemplo para pruebas
INSERT INTO medicos (nombre, apellido, dni) VALUES 
('Juan Carlos', 'Pérez', '12345678'),
('María Elena', 'González', '87654321'),
('Roberto', 'Martínez', '11223344'),
('Ana Sofía', 'López', '44332211'),
('Carlos Eduardo', 'Rodríguez', '55667788')
ON DUPLICATE KEY UPDATE 
nombre = VALUES(nombre),
apellido = VALUES(apellido);

-- Consulta de verificación
SELECT 
  id,
  CONCAT(nombre, ' ', apellido) AS nombre_completo,
  dni AS codigo,
  fecha_creacion
FROM medicos 
ORDER BY apellido, nombre;
