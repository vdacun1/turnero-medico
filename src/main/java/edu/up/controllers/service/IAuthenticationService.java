package edu.up.controllers.service;

/**
 * Servicio de autenticación que maneja el login sin exponer entidades
 */
public interface IAuthenticationService {
    
    /**
     * Autentica un usuario en el sistema
     * @param usuario Nombre de usuario
     * @param contrasena Contraseña
     * @return true si la autenticación fue exitosa, false en caso contrario
     */
    boolean autenticar(String usuario, String contrasena);
} 