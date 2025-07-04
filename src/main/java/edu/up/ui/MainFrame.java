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

import edu.up.controllers.ApplicationContext;
import edu.up.ui.forms.LoginForm;
import edu.up.ui.sections.HeaderSection;
import edu.up.ui.sections.StatusSection;
import edu.up.ui.views.AdministrationView;
import edu.up.ui.views.ConfigurationView;
import edu.up.ui.views.HomeView;
import edu.up.ui.views.IView;
import edu.up.ui.views.MedicView;
import edu.up.utils.SessionManager;

/**
 * Ventana principal de la aplicación con gestión automática de vistas.
 * Implementa el patrón IView para manejo dinámico de menús y navegación.
 */
public class MainFrame extends JFrame {
  private static final String WINDOW_TITLE = "Turnero médico - UP";
  private static final Dimension MIN_WINDOW_SIZE = new Dimension(640, 480);
  
  private StatusSection statusSection;

  /**
   * Constructor que inicializa la ventana principal y todos sus componentes.
   */
  public MainFrame() {
    super(WINDOW_TITLE);

    // Mostrar login antes de inicializar la ventana
    if (!mostrarLogin()) {
      // Si el login falla o se cancela, cerrar la aplicación
      System.exit(0);
      return;
    }

    // Variables locales para todo el proceso
    Map<String, IView> viewMap = new HashMap<>();
    CardLayout cardLayout = new CardLayout();
    JPanel contentPanel = new JPanel(cardLayout);
    List<IView> allViews = new ArrayList<>();
    IView initialView; // Variable local para la vista inicial

    // Inicializar vistas y configurar UI
    initialView = initializeViewsAndCards(allViews, viewMap, contentPanel);
    initializeWindow();

    // Obtener vistas principales para el header
    List<IView> mainViews = getMainViews(allViews);

    // Inicializar componentes
    HeaderSection headerSection = new HeaderSection(mainViews);
    statusSection = new StatusSection(Constants.APP_VERSION, Constants.DEMO_USER);

    setupComponents(headerSection, statusSection, contentPanel);
    buildMenu(allViews, headerSection, cardLayout, contentPanel);
    showInitialView(cardLayout, contentPanel, initialView);
    
    // Actualizar la barra de estado con la información del usuario autenticado
    statusSection.actualizarInformacionUsuario();
  }
  
  /**
   * Muestra el formulario de login
   * @return true si el login fue exitoso, false en caso contrario
   */
  private boolean mostrarLogin() {
    // Crear una ventana temporal invisible para el login
    JFrame tempFrame = new JFrame();
    tempFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // Obtener el controlador de login del contexto de aplicación
    ApplicationContext context = ApplicationContext.getInstance();
    
    boolean loginExitoso = LoginForm.mostrarLogin(tempFrame, context.getLoginController());
    
    tempFrame.dispose();
    
    return loginExitoso;
  }

  /**
   * Inicializa las vistas y las agrega al CardLayout sin configurar acciones.
   * 
   * @return La vista inicial de la aplicación
   */
  private IView initializeViewsAndCards(List<IView> allViews, Map<String, IView> viewMap, JPanel contentPanel) {
    // Crear instancias de las vistas principales
    HomeView homeView = new HomeView();
    ConfigurationView configView = new ConfigurationView();
    AdministrationView adminView = new AdministrationView();

    // Obtener controladores del contexto de aplicación
    ApplicationContext context = ApplicationContext.getInstance();
    MedicView medicView = new MedicView(context.getMedicController());

    // Agregar la vista de médicos a la vista de administración
    adminView.addView(medicView);

    // Agregar todas las vistas a las colecciones
    addViewToCollections(homeView, allViews, viewMap, contentPanel);
    addViewToCollections(configView, allViews, viewMap, contentPanel);
    addViewToCollections(adminView, allViews, viewMap, contentPanel);

    return homeView;
  }

  /**
   * Configura las acciones de menú
   */
  private void buildMenu(List<IView> allViews, HeaderSection headerSection, CardLayout cardLayout,
      JPanel contentPanel) {
    // Configurar acciones para vistas principales
    List<IView> mainViews = getMainViews(allViews);
    mainViews.forEach(view -> {
      if (view.hasSubViews()) {
        // Configurar acciones para subvistas con permisos
        for (IView subView : view.getSubViews()) {
          if (subView.hasPermission()) {
            configureViewAction(subView, headerSection, cardLayout, contentPanel);
          }
        }
      } else {
        // Configurar acción para vista simple
        configureViewAction(view, headerSection, cardLayout, contentPanel);
      }
    });
  }

  /**
   * Agrega una vista a todas las colecciones necesarias.
   */
  private void addViewToCollections(IView view, List<IView> allViews, Map<String, IView> viewMap, JPanel contentPanel) {
    allViews.add(view);
    viewMap.put(view.getName(), view);
    contentPanel.add(view.getPanel(), view.getName());
    // Agregar recursivamente sub-vistas
    if (view.hasSubViews()) {
        for (IView subView : view.getSubViews()) {
            addViewToCollections(subView, allViews, viewMap, contentPanel);
        }
    }
  }

  /**
   * Configura la acción de menú para una vista específica.
   */
  private void configureViewAction(IView view, HeaderSection headerSection, CardLayout cardLayout,
      JPanel contentPanel) {
    headerSection.setMenuAction(view.getName(), () -> {
      if (view.hasPermission()) {
        cardLayout.show(contentPanel, view.getName());
      }
    });
  }

  /**
   * Obtiene solo las vistas principales (no subvistas) para el menú.
   */
  private List<IView> getMainViews(List<IView> allViews) {
    List<IView> mainViews = new ArrayList<>();
    for (IView view : allViews) {
      if (!isSubView(view, allViews)) {
        mainViews.add(view);
      }
    }
    return mainViews;
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
  private void setupComponents(HeaderSection headerSection, StatusSection statusSection, JPanel contentPanel) {
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
   * @param view     Vista a verificar
   * @param allViews Lista de todas las vistas
   * @return true si es subvista, false en caso contrario
   */
  private boolean isSubView(IView view, List<IView> allViews) {
    for (IView mainView : allViews) {
      if (mainView.hasSubViews()) {
        for (IView subView : mainView.getSubViews()) {
          if (subView == view) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Muestra la vista inicial de la aplicación.
   */
  private void showInitialView(CardLayout cardLayout, JPanel contentPanel, IView initialView) {
    cardLayout.show(contentPanel, initialView.getName());
  }
  
  /**
   * Actualiza la información de la barra de estado
   */
  public void actualizarBarraEstado() {
    if (statusSection != null) {
      statusSection.actualizarInformacionUsuario();
    }
  }
  
  /**
   * Cierra la sesión y muestra el login nuevamente
   */
  public void cerrarSesion() {
    SessionManager.getInstance().cerrarSesion();
    dispose();
    
    // Crear nueva instancia de MainFrame que mostrará el login
    new MainFrame().setVisible(true);
  }
}
