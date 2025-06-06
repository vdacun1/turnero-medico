package edu.up.ui.sections;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Barra de estado al pie: versión y usuario.
 */
public class StatusSection {
  private final JPanel panel;

  public StatusSection(String version, String username) {
    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    panel.add(Box.createRigidArea(new Dimension(10, 0)));
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
