package edu.up.ui.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Vista contenedora para el menú de Administración.
 * No renderiza contenido, solo actúa como contenedor de submenús.
 */
public class AdministrationView implements IView {
  private static final String NAME = "ADMIN";
  private static final String TITLE = "Administración";
  private final List<IView> subViews;

  public AdministrationView() {
    subViews = new ArrayList<>();
  }

  @Override
  public JPanel getPanel() {
    // Como es solo un contenedor de menú, no tiene panel propio
    return new JPanel();
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getMenuTitle() {
    return TITLE;
  }

  @Override
  public boolean hasPermission() {
    // Mock: siempre true por ahora - aquí iría la lógica real de permisos
    // Aunque estoy analizando si debería resolverse a nivel global
    return true;
  }

  @Override
  public IView[] getSubViews() {
    return subViews.toArray(new IView[0]);
  }

  @Override
  public void addView(IView view) {
    subViews.add(view);
  }
}
