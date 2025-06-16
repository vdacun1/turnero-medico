package edu.up.controllers.service;

import edu.up.controllers.dao.AuthenticationDAOImpl;
import edu.up.models.entities.PersonaEntity;
import edu.up.utils.Logger;
import edu.up.utils.SessionManager;

/**
 * Implementación del servicio de autenticación
 */
public class AuthenticationServiceImpl implements IAuthenticationService {
    
    private final AuthenticationDAOImpl authDAO;
    
    public AuthenticationServiceImpl() {
        this.authDAO = new AuthenticationDAOImpl();
    }
    
    public AuthenticationServiceImpl(AuthenticationDAOImpl authDAO) {
        this.authDAO = authDAO;
    }

    @Override
    public boolean autenticar(String usuario, String contrasena) {
        Logger.info("AuthenticationServiceImpl", "Intentando autenticar usuario: " + usuario);
        
        // Primero verificar si es admin (tiene prioridad sobre cualquier usuario de BD)
        if (authDAO.esAdmin(usuario, contrasena)) {
            Logger.info("AuthenticationServiceImpl", "Administrador autenticado exitosamente");
            SessionManager.getInstance().iniciarSesionAdmin();
            return true;
        }
        
        // Solo si NO es admin, intentar autenticar como persona en BD
        PersonaEntity persona = authDAO.autenticar(usuario, contrasena);
        if (persona != null) {
            Logger.info("AuthenticationServiceImpl", "Usuario autenticado exitosamente: " + 
                       persona.getNombreCompleto() + " (" + persona.getTipoPersona() + ")");
            SessionManager.getInstance().iniciarSesion(persona);
            return true;
        }
        
        Logger.info("AuthenticationServiceImpl", "Credenciales inválidas para usuario: " + usuario);
        return false;
    }
} 