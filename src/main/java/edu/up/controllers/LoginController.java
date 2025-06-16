package edu.up.controllers;

import edu.up.controllers.service.IAuthenticationService;
import edu.up.utils.Logger;

/**
 * Controlador para manejar la lógica de login
 * Actúa como intermediario entre la vista y el servicio de autenticación
 */
public class LoginController {
    
    private final IAuthenticationService authService;
    
    public LoginController(IAuthenticationService authService) {
        this.authService = authService;
    }
    
    /**
     * Resultado de un intento de login
     */
    public static class LoginResult {
        private final boolean exitoso;
        private final String mensaje;
        
        public LoginResult(boolean exitoso, String mensaje) {
            this.exitoso = exitoso;
            this.mensaje = mensaje;
        }
        
        public boolean isExitoso() {
            return exitoso;
        }
        
        public String getMensaje() {
            return mensaje;
        }
    }
    
    /**
     * Valida las credenciales del usuario
     * @param usuario nombre de usuario
     * @param contrasena contraseña
     * @return resultado del login con mensaje descriptivo
     */
    public LoginResult validarCredenciales(String usuario, String contrasena) {
        Logger.info("LoginController", "Iniciando proceso de login para usuario: " + usuario);
        
        // Validar campos vacíos
        if (usuario == null || usuario.trim().isEmpty()) {
            Logger.warn("LoginController", "Intento de login con usuario vacío");
            return new LoginResult(false, "Por favor ingrese el nombre de usuario");
        }
        
        if (contrasena == null || contrasena.isEmpty()) {
            Logger.warn("LoginController", "Intento de login con contraseña vacía");
            return new LoginResult(false, "Por favor ingrese la contraseña");
        }
        
        try {
            // Autenticar usando el servicio
            boolean autenticado = authService.autenticar(usuario.trim(), contrasena);
            
            if (autenticado) {
                Logger.info("LoginController", "Login exitoso para usuario: " + usuario);
                return new LoginResult(true, "Login exitoso");
            } else {
                Logger.warn("LoginController", "Credenciales inválidas para usuario: " + usuario);
                return new LoginResult(false, "Usuario o contraseña incorrectos");
            }
            
        } catch (Exception e) {
            Logger.error("LoginController", "Error durante el proceso de autenticación", e);
            return new LoginResult(false, "Error al conectar con el sistema. Intente nuevamente.");
        }
    }
} 