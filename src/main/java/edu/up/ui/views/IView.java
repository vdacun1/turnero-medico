package edu.up.ui.views;

import javax.swing.JPanel;

/**
 * Interfaz para todas las vistas de la aplicación.
 * Cada vista maneja su propio menú, vista y permisos.
 */
public interface IView {

  /**
   * Obtiene el panel principal de la vista
   * 
   * @return JPanel con el contenido de la vista
   */
  JPanel getPanel();

  /**
   * Obtiene el nombre único de la vista (usado para CardLayout)
   * 
   * @return String con el nombre de la vista
   */
  String getName();

  /**
   * Obtiene el título que se mostrará en el menú
   * 
   * @return String con el título del menú
   */
  String getMenuTitle();

  /**
   * Verifica si el usuario actual tiene permisos para acceder a esta vista
   * Los permisos se evalúan internamente en cada vista
   * 
   * @return true si tiene permisos, false si no
   */
  boolean hasPermission();

  /**
   * Obtiene las vistas que forman parte del submenú de esta vista
   * 
   * @return Array de IView que serán submenús, null si no tiene submenús
   */
  IView[] getSubViews();

  /**
   * Indica si esta vista tiene submenús
   * 
   * @return true si tiene submenús, false si no
   */
  default boolean hasSubViews() {
    return getSubViews() != null && getSubViews().length > 0;
  }

  /**
   * Agrega una vista como submenú de esta vista
   * Solo implementado en vistas que actúan como contenedores de menú
   * 
   * @param view Vista a agregar como submenú
   */
  default void addView(IView view) {
    // Implementación por defecto vacía - solo contenedores la sobrescriben
  }
}
