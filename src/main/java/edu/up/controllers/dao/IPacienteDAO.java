package edu.up.controllers.dao;

import edu.up.models.entities.PacienteEntity;

/**
 * Interfaz para el acceso a datos de pacientes
 */
public interface IPacienteDAO extends IBaseDAO<PacienteEntity> {
    
    // SQL Queries
    String SQL_SELECT_ALL = "SELECT id, nombre, apellido, dni, usuario, contrasena FROM pacientes ORDER BY apellido, nombre";
    String SQL_SELECT_BY_ID = "SELECT id, nombre, apellido, dni, usuario, contrasena FROM pacientes WHERE id = ?";
    String SQL_SELECT_BY_DNI = "SELECT id, nombre, apellido, dni, usuario, contrasena FROM pacientes WHERE dni = ?";
    String SQL_SELECT_BY_USUARIO = "SELECT id, nombre, apellido, dni, usuario, contrasena FROM pacientes WHERE usuario = ?";
    String SQL_INSERT = "INSERT INTO pacientes (nombre, apellido, dni, usuario, contrasena) VALUES (?, ?, ?, ?, ?)";
    String SQL_UPDATE = "UPDATE pacientes SET nombre = ?, apellido = ?, dni = ?, usuario = ?, contrasena = ? WHERE id = ?";
    String SQL_DELETE_BY_ID = "DELETE FROM pacientes WHERE id = ?";
    
    /**
     * Busca un paciente por DNI
     * @param dni DNI del paciente
     * @return PacienteEntity si existe, null en caso contrario
     */
    PacienteEntity buscarPorDni(String dni);
    
    /**
     * Busca un paciente por nombre de usuario
     * @param usuario Nombre de usuario
     * @return PacienteEntity si existe, null en caso contrario
     */
    PacienteEntity buscarPorUsuario(String usuario);
} 