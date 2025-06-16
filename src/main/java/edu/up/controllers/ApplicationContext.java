package edu.up.controllers;

import edu.up.controllers.dao.IMedicDAO;
import edu.up.controllers.dao.MedicDAOImpl;
import edu.up.controllers.service.AuthenticationServiceImpl;
import edu.up.controllers.service.IAuthenticationService;
import edu.up.controllers.service.IMedicService;
import edu.up.controllers.service.MedicServiceImpl;

/**
 * Contexto de aplicación que maneja la inyección de dependencias
 * Centraliza la creación y configuración de componentes
 */
public class ApplicationContext {
    
    // Instancias únicas (singleton pattern)
    private static ApplicationContext instance;
    
    // DAOs
    private IMedicDAO medicDAO;
    
    // Servicios
    private IAuthenticationService authenticationService;
    private IMedicService medicService;
    
    // Controladores
    private LoginController loginController;
    private MedicController medicController;
    
    private ApplicationContext() {
        initializeComponents();
    }
    
    /**
     * Obtiene la instancia única del contexto
     */
    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }
    
    /**
     * Inicializa todos los componentes y sus dependencias
     */
    private void initializeComponents() {
        // Inicializar DAOs
        medicDAO = new MedicDAOImpl();
        
        // Inicializar servicios
        authenticationService = new AuthenticationServiceImpl();
        medicService = new MedicServiceImpl(medicDAO);
        
        // Inicializar controladores
        loginController = new LoginController(authenticationService);
        medicController = new MedicController(medicService);
    }
    
    // Getters para acceder a los controladores configurados
    
    public LoginController getLoginController() {
        return loginController;
    }
    
    public MedicController getMedicController() {
        return medicController;
    }
    
    // Getters para servicios (si se necesita acceso directo)
    
    public IAuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    
    public IMedicService getMedicService() {
        return medicService;
    }
} 