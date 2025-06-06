package edu.up;

import javax.swing.SwingUtilities;

import edu.up.ui.MainFrame;
import edu.up.utils.Logger;

/**
 * Clase de inicio de la aplicación.
 *
 */
public class App {
  public static void main(String[] args) {
    Logger.info("App", "Iniciando aplicación Turnero Médico");

    SwingUtilities.invokeLater(() -> {
      try {
        Logger.info("App", "Creando ventana principal");
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
        Logger.info("App", "Aplicación iniciada exitosamente");
      } catch (Exception e) {
        Logger.error("App", "Error al iniciar la aplicación", e);
      }
    });
  }
}
