package edu.up.ui.views;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ConfigurationView implements IView {
  private static final String NAME = "CONFIG";
  private static final String TITLE = "Configuración";
  private final JPanel panel;

  public ConfigurationView() {
    panel = new JPanel(new BorderLayout());
    JLabel lbl = new JLabel("Configuration View", SwingConstants.CENTER);
    lbl.setFont(new Font("SansSerif", Font.BOLD, 32));
    panel.add(lbl, BorderLayout.CENTER);
  }

  @Override
  public JPanel getPanel() {
    return panel;
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
    // Dejo en false para probar que no se muestre en el menú
    return false;
  }

  @Override
  public IView[] getSubViews() {
    // No tiene submenús
    return null;
  }
}
