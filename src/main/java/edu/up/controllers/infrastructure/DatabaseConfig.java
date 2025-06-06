package edu.up.controllers.infrastructure;

/**
 * Configuración centralizada de la base de datos.
 * Contiene todos los parámetros necesarios para la conexión a MySQL.
 */
public class DatabaseConfig {

  // Configuración de conexión a MySQL
  // Esto se levantará de variables de entorno en un futuro,
  // pero por ahora se define aquí para simplificar el desarrollo local.
  private static final String DB_HOST = "localhost";
  private static final String DB_PORT = "3306";
  private static final String DB_NAME = "turnero_medico";
  private static final String DB_USER = "root";
  private static final String DB_PASSWORD = "123456";

  // URL completa de conexión
  // Me queda pendiente abstraer todo lo que es la conexión y desacoplar del motor
  // de base de datos
  private static final String DB_URL = String.format(
      "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
      DB_HOST, DB_PORT, DB_NAME);

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
    return DB_URL;
  }

  /**
   * Obtiene el nombre de usuario para la conexión.
   * 
   * @return nombre de usuario
   */
  public static String getUser() {
    return DB_USER;
  }

  /**
   * Obtiene la contraseña para la conexión.
   * 
   * @return contraseña
   */
  public static String getPassword() {
    return DB_PASSWORD;
  }

  /**
   * Obtiene el host de la base de datos.
   * 
   * @return host
   */
  public static String getHost() {
    return DB_HOST;
  }

  /**
   * Obtiene el puerto de la base de datos.
   * 
   * @return puerto
   */
  public static String getPort() {
    return DB_PORT;
  }

  /**
   * Obtiene el nombre de la base de datos.
   * 
   * @return nombre de la base de datos
   */
  public static String getDatabaseName() {
    return DB_NAME;
  }
}
