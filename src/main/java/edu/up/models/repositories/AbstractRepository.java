package edu.up.models.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

import edu.up.controllers.exceptions.DataAccessException;
import edu.up.controllers.infrastructure.IDBConnection;
import edu.up.controllers.infrastructure.MySQLConnectionManager;

/**
 * Maneja apertura/cierre de conexión en cada operación.
 * Utiliza MySQLConnectionManager internamente para obtener conexiones.
 */
public abstract class AbstractRepository {
  protected final IDBConnection dbConnection;

  /**
   * Constructor por defecto que utiliza MySQLConnectionManager.
   */
  protected AbstractRepository() {
    this.dbConnection = MySQLConnectionManager.getInstance();
  }

  /**
   * Constructor para inyección de dependencias (útil para testing).
   */
  protected AbstractRepository(IDBConnection dbConnection) {
    this.dbConnection = dbConnection;
  }

  /**
   * Ejecuta la lógica que recibe con una conexión abierta,
   * y se encarga de cerrarla y traducir SQLException.
   */
  protected <R> R execute(Function<Connection, R> block) {
    try (Connection conn = dbConnection.getConnection()) {
      return block.apply(conn);
    } catch (SQLException e) {
      throw new DataAccessException("Error acceso a datos", e);
    }
  }
}
