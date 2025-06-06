package edu.up.controllers.infrastructure;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstracción del origen de conexiones.
 */
public interface IDBConnection {
  Connection getConnection() throws SQLException;
}
