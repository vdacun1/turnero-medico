package edu.up.controllers.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.up.utils.Logger;

/**
 * Implementación concreta para MySQL vía JDBC DriverManager.
 * Maneja la configuración de conexión internamente usando DatabaseConfig.
 */
public class MySQLConnectionManager implements IDBConnection {
  private static MySQLConnectionManager instance;

  /**
   * Constructor privado que usa la configuración de DatabaseConfig.
   */
  private MySQLConnectionManager() {
  }

  /**
   * Obtiene la instancia singleton del connection manager.
   */
  public static synchronized MySQLConnectionManager getInstance() {
    if (instance == null) {
      instance = new MySQLConnectionManager();
    }
    return instance;
  }

  @Override
  public Connection getConnection() throws SQLException {
    try {
      Logger.info("MySQLConnectionManager", "Estableciendo conexión a la base de datos...");
      Connection connection = DriverManager.getConnection(
          DatabaseConfig.getUrl(),
          DatabaseConfig.getUser(),
          DatabaseConfig.getPassword());
      Logger.info("MySQLConnectionManager", "Conexión establecida exitosamente");
      return connection;
    } catch (SQLException e) {
      Logger.error("MySQLConnectionManager", "Error al conectar a la base de datos", e);
      throw e;
    }
  }
}
