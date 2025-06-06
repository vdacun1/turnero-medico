package edu.up.ui.sections;

import static edu.up.ui.Constants.CHECK_ICON;
import static edu.up.ui.Constants.DB_ACTIVE_ICON;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Barra de estado al pie: actividad BD, conexión, versión y usuario.
 */
public class StatusSection {
  private final JPanel panel;

  public StatusSection(boolean dbActive,
      boolean dbConnected,
      String version,
      String username) {
    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // iconos de ejemplo como texto; reemplaza por ImageIcon si tienes recursos
    JLabel dbIcon = new JLabel(DB_ACTIVE_ICON);
    JLabel connIcon = new JLabel(CHECK_ICON);

    panel.add(Box.createRigidArea(new Dimension(10, 0)));
    panel.add(dbIcon);
    panel.add(new JLabel(" BD Active: " + (dbActive ? "Sí" : "No")));
    panel.add(Box.createHorizontalGlue());

    panel.add(connIcon);
    panel.add(new JLabel(" Conectado: " + (dbConnected ? "Sí" : "No")));
    panel.add(Box.createHorizontalGlue());

    panel.add(new JLabel("Versión: " + version));
    panel.add(Box.createRigidArea(new Dimension(20, 0)));

    panel.add(new JLabel("Usuario: " + username));
    panel.add(Box.createRigidArea(new Dimension(10, 0)));
  }

  public JPanel getPanel() {
    return panel;
  }
}
