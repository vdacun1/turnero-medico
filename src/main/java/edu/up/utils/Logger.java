package edu.up.utils;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger simple para la aplicación que muestra mensajes en consola
 * con formato de fecha/hora y nivel de log.
 * Configurado para manejar correctamente caracteres UTF-8 en Windows.
 */
public class Logger {
  private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  // Configuración de salida que maneja encoding correctamente
  private static final PrintWriter out = initializePrintWriter();

  private static PrintWriter initializePrintWriter() {
    try {
      // En Windows, intentar usar CP850 o la codificación de la consola
      String os = System.getProperty("os.name").toLowerCase();
      if (os.contains("windows")) {
        // Usar la codificación predeterminada de la consola en Windows
        String consoleEncoding = System.getProperty("console.encoding", "CP850");
        return new PrintWriter(
            new OutputStreamWriter(System.out, Charset.forName(consoleEncoding)), true);
      } else {
        // En otros sistemas usar UTF-8
        return new PrintWriter(
            new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
      }
    } catch (Exception e) {
      // Fallback a System.out normal si hay algún error
      return new PrintWriter(System.out, true);
    }
  }

  // Prefijos con colores ANSI para diferentes niveles
  private static final String INFO_PREFIX = "[INFO]";
  private static final String WARN_PREFIX = "[WARN]";
  private static final String ERROR_PREFIX = "[ERROR]";

  // Constructor privado para evitar instanciación
  private Logger() {
    throw new UnsupportedOperationException("Esta es una clase de utilidad y no debe ser instanciada");
  }

  /**
   * Registra un mensaje de información.
   * 
   * @param message Mensaje a registrar
   */
  public static void info(String message) {
    log(INFO_PREFIX, message);
  }

  /**
   * Registra un mensaje de información con contexto adicional.
   * 
   * @param className Nombre de la clase que genera el log
   * @param message   Mensaje a registrar
   */
  public static void info(String className, String message) {
    log(INFO_PREFIX, className, message);
  }

  /**
   * Registra un mensaje de advertencia.
   * 
   * @param message Mensaje a registrar
   */
  public static void warn(String message) {
    log(WARN_PREFIX, message);
  }

  /**
   * Registra un mensaje de advertencia con contexto adicional.
   * 
   * @param className Nombre de la clase que genera el log
   * @param message   Mensaje a registrar
   */
  public static void warn(String className, String message) {
    log(WARN_PREFIX, className, message);
  }

  /**
   * Registra un mensaje de error.
   * 
   * @param message Mensaje a registrar
   */
  public static void error(String message) {
    log(ERROR_PREFIX, message);
  }

  /**
   * Registra un mensaje de error con contexto adicional.
   * 
   * @param className Nombre de la clase que genera el log
   * @param message   Mensaje a registrar
   */
  public static void error(String className, String message) {
    log(ERROR_PREFIX, className, message);
  }

  /**
   * Registra un mensaje de error con excepción.
   * 
   * @param message   Mensaje a registrar
   * @param throwable Excepción asociada
   */
  public static void error(String message, Throwable throwable) {
    log(ERROR_PREFIX, message);
    if (throwable != null) {
      out.println("  Causa: " + throwable.getMessage());
    }
  }

  /**
   * Registra un mensaje de error con contexto adicional y excepción.
   * 
   * @param className Nombre de la clase que genera el log
   * @param message   Mensaje a registrar
   * @param throwable Excepción asociada
   */
  public static void error(String className, String message, Throwable throwable) {
    log(ERROR_PREFIX, className, message);
    if (throwable != null) {
      out.println("  Causa: " + throwable.getMessage());
    }
  }

  /**
   * Método privado para generar el log con formato estándar.
   * 
   * @param level   Nivel del log (INFO, WARN, ERROR)
   * @param message Mensaje a registrar
   */
  private static void log(String level, String message) {
    String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
    out.println(String.format("%s %s %s", timestamp, level, message));
  }

  /**
   * Método privado para generar el log con formato estándar y contexto.
   * 
   * @param level     Nivel del log (INFO, WARN, ERROR)
   * @param className Nombre de la clase
   * @param message   Mensaje a registrar
   */
  private static void log(String level, String className, String message) {
    String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
    out.println(String.format("%s %s [%s] %s", timestamp, level, className, message));
  }
}
