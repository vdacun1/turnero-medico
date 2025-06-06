package edu.up.ui.sections;

import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import edu.up.ui.views.IView;

/**
 * Sección de menú que genera automáticamente botones basándose en las vistas
 * disponibles.
 * Soporta menús simples y desplegables con subvistas.
 */
public class MenuSection {

  private static final String DROPDOWN_ARROW = " ▼";
  private static final int MENU_SPACING = 20;
  private static final int MENU_PADDING = 10;

  private final JPanel panel;
  private final Map<String, Runnable> menuActions;

  /**
   * Constructor que inicializa la sección de menú con las vistas disponibles.
   * 
   * @param allViews Lista de todas las vistas disponibles en la aplicación
   */
  public MenuSection(List<IView> allViews) {
    this.menuActions = new HashMap<>();
    this.panel = createMenuPanel();

    buildMenuButtons(allViews);
  }

  /**
   * Crea y configura el panel principal del menú.
   * 
   * @return Panel configurado para contener los botones de menú
   */
  private JPanel createMenuPanel() {
    JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, MENU_SPACING, MENU_PADDING));
    menuPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, MENU_SPACING));
    return menuPanel;
  }

  /**
   * Construye todos los botones de menú basándose en las vistas principales.
   * 
   * @param allViews Lista de todas las vistas disponibles
   */
  private void buildMenuButtons(List<IView> allViews) {
    allViews.stream()
        .filter(view -> !isSubView(view, allViews))
        .forEach(this::createMenuButton);
  }

  /**
   * Crea un botón de menú apropiado según el tipo de vista.
   * 
   * @param view Vista para la cual crear el botón
   */
  private void createMenuButton(IView view) {
    if (view.hasSubViews()) {
      createDropdownButton(view);
    } else {
      createSimpleButton(view);
    }
  }

  /**
   * Verifica si una vista es subvista de otra vista principal.
   * 
   * @param view     Vista a verificar
   * @param allViews Lista de todas las vistas disponibles
   * @return true si la vista es una subvista, false en caso contrario
   */
  private boolean isSubView(IView view, List<IView> allViews) {
    return allViews.stream()
        .filter(IView::hasSubViews)
        .flatMap(mainView -> java.util.Arrays.stream(mainView.getSubViews()))
        .anyMatch(subView -> subView.getName().equals(view.getName()));
  }

  /**
   * Crea un botón desplegable para vistas que contienen subvistas.
   * 
   * @param view Vista principal que contiene subvistas
   */
  private void createDropdownButton(IView view) {
    JButton button = new JButton(view.getMenuTitle() + DROPDOWN_ARROW);
    button.setEnabled(view.hasPermission());

    JPopupMenu popupMenu = buildPopupMenu(view);
    button.addActionListener(e -> showPopupIfEnabled(button, popupMenu));

    panel.add(button);
  }

  /**
   * Construye el menú desplegable para una vista con subvistas.
   * 
   * @param view Vista principal
   * @return Menú desplegable configurado
   */
  private JPopupMenu buildPopupMenu(IView view) {
    JPopupMenu popupMenu = new JPopupMenu();

    java.util.Arrays.stream(view.getSubViews())
        .filter(IView::hasPermission)
        .forEach(subView -> addMenuItem(popupMenu, subView));

    return popupMenu;
  }

  /**
   * Agrega un elemento al menú desplegable.
   * 
   * @param popupMenu Menú al cual agregar el elemento
   * @param subView   Subvista a agregar como elemento del menú
   */
  private void addMenuItem(JPopupMenu popupMenu, IView subView) {
    JMenuItem menuItem = new JMenuItem(subView.getMenuTitle());
    menuItem.addActionListener(e -> executeAction(subView.getName()));
    popupMenu.add(menuItem);
  }

  /**
   * Muestra el menú desplegable si el botón está habilitado.
   * 
   * @param button    Botón que activa el menú
   * @param popupMenu Menú a mostrar
   */
  private void showPopupIfEnabled(JButton button, JPopupMenu popupMenu) {
    if (button.isEnabled()) {
      popupMenu.show(button, 0, button.getHeight());
    }
  }

  /**
   * Crea un botón simple para vistas sin subvistas.
   * 
   * @param view Vista para la cual crear el botón
   */
  private void createSimpleButton(IView view) {
    JButton button = new JButton(view.getMenuTitle());
    button.setEnabled(view.hasPermission());
    button.addActionListener(e -> executeActionIfEnabled(button, view.getName()));

    panel.add(button);
  }

  /**
   * Ejecuta una acción solo si el botón está habilitado.
   * 
   * @param button   Botón que activa la acción
   * @param viewName Nombre de la vista asociada
   */
  private void executeActionIfEnabled(JButton button, String viewName) {
    if (button.isEnabled()) {
      executeAction(viewName);
    }
  }

  /**
   * Ejecuta la acción asociada a un nombre de vista.
   * 
   * @param viewName Nombre de la vista cuya acción ejecutar
   */
  private void executeAction(String viewName) {
    Runnable action = menuActions.get(viewName);
    if (action != null) {
      action.run();
    }
  }

  /**
   * Registra una acción para un elemento del menú.
   * 
   * @param viewName Nombre de la vista
   * @param action   Acción a ejecutar cuando se seleccione el menú
   */
  public void setMenuAction(String viewName, Runnable action) {
    menuActions.put(viewName, action);
  }

  /**
   * Obtiene el panel principal que contiene todos los elementos del menú.
   * 
   * @return Panel del menú
   */
  public JPanel getPanel() {
    return panel;
  }
}
