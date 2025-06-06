package edu.up.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.up.ui.sections.HeaderSection;
import edu.up.ui.sections.StatusSection;
import edu.up.ui.views.AdministrationView;
import edu.up.ui.views.ConfigurationView;
import edu.up.ui.views.HomeView;
import edu.up.ui.views.IView;
import edu.up.ui.views.MedicView;

/**
 * Ventana principal de la aplicación con gestión automática de vistas.
 * Implementa el patrón IView para manejo dinámico de menús y navegación.
 */
public class MainFrame extends JFrame {

  private static final String WINDOW_TITLE = "Turnero médico - UP";
  private static final Dimension MIN_WINDOW_SIZE = new Dimension(640, 480);
  private static final String APP_VERSION = "1.0-SNAPSHOT";
  private static final String DEMO_USER = "usuarioDemo";
  private final HeaderSection headerSection;
  private final StatusSection statusSection;
  private final JPanel contentPanel;
  private final CardLayout cardLayout;
  private final List<IView> allViews;
  private final Map<String, IView> viewMap;

  /**
   * Constructor que inicializa la ventana principal y todos sus componentes.
   */
  public MainFrame() {
    super(WINDOW_TITLE);
    this.allViews = new ArrayList<>();
    this.viewMap = new HashMap<>();
    this.cardLayout = new CardLayout();
    this.contentPanel = new JPanel(cardLayout);

    initializeViewsAndCards();
    initializeWindow(); // Inicializar componentes después de tener las vistas
    this.headerSection = new HeaderSection(getMainViews());
    this.statusSection = new StatusSection(APP_VERSION, DEMO_USER);

    setupComponents();
    configureMenuActionsOptimized();
    showInitialView();
  }

  /**
   * Inicializa las vistas y las agrega al CardLayout sin configurar acciones.
   */
  private void initializeViewsAndCards() {
    // Crear vistas principales
    List<IView> mainViews = createMainViews();

    // Procesar todas las vistas en un solo recorrido
    for (IView view : mainViews) {
      // Agregar vista principal
      addViewToCollections(view);

      // Procesar subvistas si existen
      if (view.hasSubViews()) {
        for (IView subView : view.getSubViews()) {
          addViewToCollections(subView);
        }
      }
    }
  }

  /**
   * Configura las acciones de menú optimizadas después de inicializar
   * headerSection.
   */
  private void configureMenuActionsOptimized() {
    // Configurar acciones para vistas principales
    getMainViews().forEach(view -> {
      if (view.hasSubViews()) {
        // Configurar acciones para subvistas con permisos
        for (IView subView : view.getSubViews()) {
          if (subView.hasPermission()) {
            configureViewAction(subView);
          }
        }
      } else {
        // Configurar acción para vista simple
        configureViewAction(view);
      }
    });
  }

  /**
   * Crea las vistas principales de la aplicación.
   */
  private List<IView> createMainViews() {
    List<IView> mainViews = new ArrayList<>();

    // Vistas principales
    mainViews.add(new HomeView());
    mainViews.add(new ConfigurationView());

    // Vista de administración con subvistas
    AdministrationView administrationView = new AdministrationView();
    administrationView.addView(new MedicView());
    mainViews.add(administrationView);

    return mainViews;
  }

  /**
   * Agrega una vista a todas las colecciones necesarias.
   */
  private void addViewToCollections(IView view) {
    allViews.add(view);
    viewMap.put(view.getName(), view);
    contentPanel.add(view.getPanel(), view.getName());
  }

  /**
   * Configura la acción de menú para una vista específica.
   */
  private void configureViewAction(IView view) {
    headerSection.setMenuAction(view.getName(), () -> {
      if (view.hasPermission()) {
        navigateToView(view.getName());
      }
    });
  }

  /**
   * Obtiene solo las vistas principales (no subvistas) para el menú.
   */
  private List<IView> getMainViews() {
    return allViews.stream()
        .filter(view -> !isSubView(view))
        .toList();
  }

  /**
   * Configura las propiedades básicas de la ventana.
   */
  private void initializeWindow() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setMinimumSize(MIN_WINDOW_SIZE);
    setLayout(new BorderLayout());
  }

  /**
   * Configura y agrega todos los componentes a la ventana.
   */
  private void setupComponents() {
    // Header con menús automáticos
    add(headerSection.getPanel(), BorderLayout.NORTH);

    // Barra de estado
    add(statusSection.getPanel(), BorderLayout.SOUTH);

    // Panel de contenido ya configurado en initializeViewsAndActions
    add(contentPanel, BorderLayout.CENTER);
  }

  /**
   * Verifica si una vista es subvista de otra vista principal.
   * 
   * @param view Vista a verificar
   * @return true si es subvista, false en caso contrario
   */
  private boolean isSubView(IView view) {
    return allViews.stream()
        .filter(IView::hasSubViews)
        .flatMap(mainView -> java.util.Arrays.stream(mainView.getSubViews()))
        .anyMatch(subView -> subView.getName().equals(view.getName()));
  }

  /**
   * Navega a una vista específica usando CardLayout.
   * 
   * @param viewName Nombre de la vista a mostrar
   */
  private void navigateToView(String viewName) {
    cardLayout.show(contentPanel, viewName);
  }

  /**
   * Muestra la vista inicial de la aplicación.
   */
  private void showInitialView() {
    navigateToView(HomeView.NAME);
  }

  /**
   * Obtiene una vista por su nombre usando el mapa optimizado.
   * 
   * @param name Nombre de la vista a buscar
   * @return Vista encontrada o null si no existe
   */
  public IView getViewByName(String name) {
    return viewMap.get(name);
  }

  /**
   * Muestra una vista específica si tiene permisos.
   * 
   * @param view Vista a mostrar
   */
  public void showView(IView view) {
    if (view != null && view.hasPermission()) {
      navigateToView(view.getName());
    }
  }
}
