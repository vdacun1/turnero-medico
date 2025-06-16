package edu.up.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gestor de configuración para cargar propiedades desde application.properties
 */
public class ConfigurationManager {
    private static final String CONFIG_FILE = "application.properties";
    private static Properties properties;
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream inputStream = ConfigurationManager.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            
            if (inputStream != null) {
                properties.load(inputStream);
                Logger.info("ConfigurationManager", "Propiedades cargadas exitosamente");
            } else {
                Logger.error("ConfigurationManager", "No se pudo encontrar el archivo " + CONFIG_FILE, null);
            }
        } catch (IOException e) {
            Logger.error("ConfigurationManager", "Error al cargar propiedades", e);
        }
    }
    
    /**
     * Obtiene el valor de una propiedad
     * @param key Clave de la propiedad
     * @return Valor de la propiedad o null si no existe
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Obtiene el valor de una propiedad con un valor por defecto
     * @param key Clave de la propiedad
     * @param defaultValue Valor por defecto si la propiedad no existe
     * @return Valor de la propiedad o valor por defecto
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    // Propiedades específicas para fácil acceso
    public static String getDbUser() {
        return getProperty("db.user", "root");
    }
    
    public static String getDbPassword() {
        return getProperty("db.password", "");
    }
    
    public static String getDbUrl() {
        return getProperty("db.url", "jdbc:mysql://localhost:3306/turnero_medico");
    }
    
    public static String getAdminUser() {
        return getProperty("admin.user", "admin");
    }
    
    public static String getAdminPassword() {
        return getProperty("admin.password", "admin");
    }
} 