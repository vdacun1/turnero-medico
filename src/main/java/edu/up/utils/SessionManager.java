package edu.up.utils;

import edu.up.models.entities.PersonaEntity;

/**
 * Gestor de sesión para mantener información del usuario autenticado
 */
public class SessionManager {
    private static SessionManager instance;
    private PersonaEntity usuarioActual;
    private boolean esAdmin;
    
    private SessionManager() {
        this.usuarioActual = null;
        this.esAdmin = false;
    }
    
    /**
     * Obtiene la instancia única del SessionManager (Singleton)
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    /**
     * Inicia sesión con un usuario (persona)
     * @param persona Usuario que inicia sesión
     */
    public void iniciarSesion(PersonaEntity persona) {
        this.usuarioActual = persona;
        this.esAdmin = false;
        Logger.info("SessionManager", "Sesión iniciada para: " + persona.getNombreCompleto() + 
                   " (" + persona.getTipoPersona() + ")");
    }
    
    /**
     * Inicia sesión como administrador
     */
    public void iniciarSesionAdmin() {
        this.usuarioActual = null;
        this.esAdmin = true;
        Logger.info("SessionManager", "Sesión de administrador iniciada");
    }
    
    /**
     * Cierra la sesión actual
     */
    public void cerrarSesion() {
        if (esAdmin) {
            Logger.info("SessionManager", "Sesión de administrador cerrada");
        } else if (usuarioActual != null) {
            Logger.info("SessionManager", "Sesión cerrada para: " + usuarioActual.getNombreCompleto());
        }
        this.usuarioActual = null;
        this.esAdmin = false;
    }
    
    /**
     * Obtiene el usuario actual (persona)
     * @return Usuario actual o null si no hay persona autenticada o es admin
     */
    public PersonaEntity getUsuarioActual() {
        return usuarioActual;
    }
    
    /**
     * Obtiene el nombre del usuario/admin actual
     * @return Nombre del usuario o admin
     */
    public String getNombreUsuario() {
        if (esAdmin) {
            return "Administrador del Sistema";
        }
        return usuarioActual.getNombreCompleto();
    }
    
    /**
     * Obtiene el tipo de usuario actual
     * @return Tipo de usuario
     */
    public String getTipoUsuario() {
        if (esAdmin) {
            return "Administrador";
        }
        return usuarioActual.getTipoPersona();
    }
    
    /**
     * Verifica si el usuario actual es médico
     * @return true si es médico, false en caso contrario
     */
    public boolean esMedico() {
        return !esAdmin && usuarioActual != null && "Médico".equals(usuarioActual.getTipoPersona());
    }
    
    /**
     * Verifica si el usuario actual es paciente
     * @return true si es paciente, false en caso contrario
     */
    public boolean esPaciente() {
        return !esAdmin && usuarioActual != null && "Paciente".equals(usuarioActual.getTipoPersona());
    }
    
    /**
     * Verifica si el usuario actual es administrador
     * @return true si es administrador, false en caso contrario
     */
    public boolean esAdministrador() {
        return esAdmin;
    }
} 