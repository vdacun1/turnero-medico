package edu.up.controllers.infrastructure;

import edu.up.utils.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuración singleton para la carga centralizada de propiedades.
 * Maneja la lectura del archivo application.properties de forma estática.
 */
public class Configuration {

  private static final Properties properties = new Properties();
  private static boolean initialized = false;

  // Constructor privado para evitar instanciación
  private Configuration() {
    throw new UnsupportedOperationException("Esta es una clase de utilidad singleton y no debe ser instanciada");
  }

  /**
   * Inicializa la configuración.
   * Solo se ejecuta una vez.
   */
  private static void initialize() {
    if (!initialized) {
      try (InputStream input = Configuration.class.getClassLoader().getResourceAsStream("application.properties")) {
        if (input == null) {
          Logger.error(Configuration.class.getSimpleName(), "No se pudo encontrar application.properties");
          throw new RuntimeException("No se pudo encontrar application.properties");
        }
        properties.load(input);
        initialized = true;
        Logger.info(Configuration.class.getSimpleName(), "Configuración cargada exitosamente desde application.properties");
      } catch (IOException e) {
        Logger.error(Configuration.class.getSimpleName(), "Error al cargar application.properties", e);
        throw new RuntimeException("Error al cargar application.properties", e);
      }
    }
  }

  /**
   * Obtiene una propiedad por su clave.
   * 
   * @param key la clave de la propiedad
   * @return el valor de la propiedad o null si no existe
   */
  public static String getProperty(String key) {
    initialize();
    String value = properties.getProperty(key);
    Logger.info(Configuration.class.getSimpleName(), "Obteniendo propiedad: " + key + " = " + value);
    return value;
  }
} 