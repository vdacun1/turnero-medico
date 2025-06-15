package edu.up.controllers.infrastructure;

import edu.up.utils.Logger;

/**
 * Configuración centralizada de la base de datos.
 * Contiene todos los parámetros necesarios para la conexión a MySQL.
 * Utiliza la clase Configuration para obtener las propiedades.
 */
public class DatabaseConfig {

  // Constructor privado para evitar instanciación
  private DatabaseConfig() {
    throw new UnsupportedOperationException("Esta es una clase de utilidad y no debe ser instanciada");
  }

  /**
   * Obtiene la URL completa de conexión a la base de datos.
   * 
   * @return URL de conexión JDBC
   */
  public static String getUrl() {
    String url = Configuration.getProperty("db.url");
    Logger.info(DatabaseConfig.class.getSimpleName(), "Obteniendo URL de conexión: " + url);
    return url;
  }

  /**
   * Obtiene el nombre de usuario para la conexión.
   * 
   * @return nombre de usuario
   */
  public static String getUser() {
    String user = Configuration.getProperty("db.user");
    Logger.info(DatabaseConfig.class.getSimpleName(), "Obteniendo usuario: " + user);
    return user;
  }

  /**
   * Obtiene la contraseña para la conexión.
   * 
   * @return contraseña
   */
  public static String getPassword() {
    Logger.info(DatabaseConfig.class.getSimpleName(), "Obteniendo contraseña");
    return Configuration.getProperty("db.password");
  }
}
