package edu.up.ui.views;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ConfigurationView implements IView {
  public static final String NAME = "CONFIG";
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
    return "Configuración";
  }

  @Override
  public boolean hasPermission() {
    // Mock: siempre true por ahora - aquí iría la lógica real de permisos
    return false;
  }

  @Override
  public IView[] getSubViews() {
    // No tiene submenús
    return null;
  }
}
