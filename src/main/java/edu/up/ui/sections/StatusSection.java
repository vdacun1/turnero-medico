package edu.up.ui.sections;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.up.utils.SessionManager;

/**
 * Barra de estado al pie: versión y usuario.
 */
public class StatusSection {
  private final JPanel panel;
  private final JLabel versionLabel;
  private final JLabel usuarioLabel;
  private final JLabel tipoUsuarioLabel;

  public StatusSection(String version, String username) {
    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    panel.add(Box.createRigidArea(new Dimension(10, 0)));
    panel.add(Box.createHorizontalGlue());

    // Etiqueta de versión (fija)
    versionLabel = new JLabel("Versión: " + version);
    panel.add(versionLabel);
    panel.add(Box.createRigidArea(new Dimension(20, 0)));

    // Etiqueta de usuario (dinámica)
    usuarioLabel = new JLabel("Usuario: " + username);
    panel.add(usuarioLabel);
    panel.add(Box.createRigidArea(new Dimension(20, 0)));

    // Etiqueta de tipo de usuario (nueva)
    tipoUsuarioLabel = new JLabel("Tipo: Sin definir");
    panel.add(tipoUsuarioLabel);
    panel.add(Box.createRigidArea(new Dimension(10, 0)));
    
    // Actualizar con información de sesión
    actualizarInformacionUsuario();
  }

  public JPanel getPanel() {
    return panel;
  }
  
  /**
   * Actualiza la información del usuario en la barra de estado
   */
  public void actualizarInformacionUsuario() {
    SessionManager session = SessionManager.getInstance();
    
    usuarioLabel.setText("Usuario: " + session.getNombreUsuario());
    tipoUsuarioLabel.setText("Tipo: " + session.getTipoUsuario());
    
    // Cambiar color según el tipo de usuario
    if (session.esAdministrador()) {
      tipoUsuarioLabel.setForeground(Color.RED); // Rojo para administrador
    } else if (session.esMedico() || session.esPaciente()) {
      tipoUsuarioLabel.setForeground(Color.BLUE); // Azul para médicos y pacientes
    } else {
      tipoUsuarioLabel.setForeground(Color.BLACK); // Negro por defecto
    }
    
    // Repintar el componente
    panel.revalidate();
    panel.repaint();
  }
}
