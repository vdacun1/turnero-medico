package edu.up.controllers.dao;

import edu.up.models.entities.PersonaEntity;

/**
 * Interfaz para el acceso a datos de personas con funcionalidades de autenticación
 */
public interface IPersonaDAO extends IBaseDAO<PersonaEntity> {
    
    // SQL Queries para médicos
    String SQL_SELECT_MEDICO_BY_USUARIO = "SELECT id, nombre, apellido, dni, usuario, contrasena FROM medicos WHERE usuario = ?";
    String SQL_AUTHENTICATE_MEDICO = "SELECT id, nombre, apellido, dni, usuario, contrasena FROM medicos WHERE usuario = ? AND contrasena = ?";
    
    // SQL Queries para pacientes
    String SQL_SELECT_PACIENTE_BY_USUARIO = "SELECT id, nombre, apellido, dni, usuario, contrasena FROM pacientes WHERE usuario = ?";
    String SQL_AUTHENTICATE_PACIENTE = "SELECT id, nombre, apellido, dni, usuario, contrasena FROM pacientes WHERE usuario = ? AND contrasena = ?";
    
    /**
     * Autentica un usuario por credenciales
     * @param usuario Nombre de usuario
     * @param contrasena Contraseña
     * @return PersonaEntity si las credenciales son válidas, null en caso contrario
     */
    PersonaEntity autenticar(String usuario, String contrasena);
    
    /**
     * Busca una persona por nombre de usuario
     * @param usuario Nombre de usuario
     * @return PersonaEntity si existe, null en caso contrario
     */
    PersonaEntity buscarPorUsuario(String usuario);
} 