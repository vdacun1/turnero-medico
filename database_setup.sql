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
  usuario VARCHAR(50) UNIQUE,
  contrasena VARCHAR(255),
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_medicos_dni (dni),
  INDEX idx_medicos_nombre (nombre, apellido),
  INDEX idx_medicos_usuario (usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de pacientes
-- Hereda conceptualmente de una clase Persona con nombre, apellido y dni
CREATE TABLE IF NOT EXISTS pacientes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  apellido VARCHAR(100) NOT NULL,
  dni VARCHAR(20) NOT NULL UNIQUE,
  usuario VARCHAR(50) UNIQUE,
  contrasena VARCHAR(255),
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_pacientes_dni (dni),
  INDEX idx_pacientes_nombre (nombre, apellido),
  INDEX idx_pacientes_usuario (usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Datos de ejemplo para médicos
INSERT INTO medicos (nombre, apellido, dni, usuario, contrasena) VALUES 
('Juan Carlos', 'Pérez', '12345678', 'jperez', 'medico123'),
('María Elena', 'González', '87654321', 'mgonzalez', 'medico456'),
('Roberto', 'Martínez', '11223344', 'rmartinez', 'medico789'),
('Ana Sofía', 'López', '44332211', 'alopez', 'medico101'),
('Carlos Eduardo', 'Rodríguez', '55667788', 'crodriguez', 'medico202'),
('Admin', 'Sistema', '00000000', 'admin', 'admin')
ON DUPLICATE KEY UPDATE 
nombre = VALUES(nombre),
apellido = VALUES(apellido),
usuario = VALUES(usuario),
contrasena = VALUES(contrasena);

-- Datos de ejemplo para pacientes
INSERT INTO pacientes (nombre, apellido, dni, usuario, contrasena) VALUES 
('Pedro', 'García', '98765432', 'pgarcia', 'paciente123'),
('Laura', 'Mendoza', '12398765', 'lmendoza', 'paciente456'),
('Miguel', 'Torres', '45678901', 'mtorres', 'paciente789'),
('Carmen', 'Ruiz', '78901234', 'cruiz', 'paciente101'),
('Diego', 'Morales', '34567890', 'dmorales', 'paciente202')
ON DUPLICATE KEY UPDATE 
nombre = VALUES(nombre),
apellido = VALUES(apellido),
usuario = VALUES(usuario),
contrasena = VALUES(contrasena);

-- Consulta de verificación para médicos
SELECT 
  id,
  CONCAT(nombre, ' ', apellido) AS nombre_completo,
  dni AS codigo,
  usuario,
  'Médico' AS tipo,
  fecha_creacion
FROM medicos 
ORDER BY apellido, nombre;

-- Consulta de verificación para pacientes
SELECT 
  id,
  CONCAT(nombre, ' ', apellido) AS nombre_completo,
  dni AS codigo,
  usuario,
  'Paciente' AS tipo,
  fecha_creacion
FROM pacientes 
ORDER BY apellido, nombre;
