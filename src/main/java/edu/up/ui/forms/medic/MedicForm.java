package edu.up.ui.forms.medic;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.up.models.entities.MedicoEntity;

/**
 * Formulario para la edición de datos de médicos.
 * Se encarga únicamente de la parte gráfica del formulario.
 */
public class MedicForm {
  private final JPanel panel;
  private final JTextField txtNombre;
  private final JTextField txtApellido;
  private final JTextField txtDni;
  private final JButton btnGuardar;
  private final JButton btnEliminar;
  private final JButton btnLimpiar;

  public MedicForm() {
    this.panel = new JPanel(new GridBagLayout());
    this.txtNombre = new JTextField(20);
    this.txtApellido = new JTextField(20);
    this.txtDni = new JTextField(15);
    this.btnGuardar = new JButton("Guardar");
    this.btnEliminar = new JButton("Eliminar");
    this.btnLimpiar = new JButton("Limpiar");

    initializeUI();
  }

  private void initializeUI() {
    // Borde simple con título
    panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createTitledBorder("Datos del Médico"),
        javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));

    // Establecer ancho preferido para el formulario
    panel.setPreferredSize(new java.awt.Dimension(350, 0));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);

    // Nombre
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST;
    panel.add(new JLabel("Nombre:"), gbc);

    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    panel.add(txtNombre, gbc);

    // Apellido
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0.0;
    panel.add(new JLabel("Apellido:"), gbc);

    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    panel.add(txtApellido, gbc);

    // DNI
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0.0;
    panel.add(new JLabel("DNI/Código:"), gbc);

    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    panel.add(txtDni, gbc);

    // Panel de botones
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.add(btnGuardar);
    buttonPanel.add(btnEliminar);
    buttonPanel.add(btnLimpiar);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.insets = new Insets(15, 0, 0, 0);
    panel.add(buttonPanel, gbc);
  }

  // Getters para acceder al panel y componentes
  public JPanel getPanel() {
    return panel;
  }

  // Métodos para manejar datos del formulario
  public String getNombre() {
    return txtNombre.getText().trim();
  }

  public String getApellido() {
    return txtApellido.getText().trim();
  }

  public String getDni() {
    return txtDni.getText().trim();
  }

  public void setNombre(String nombre) {
    txtNombre.setText(nombre);
  }

  public void setApellido(String apellido) {
    txtApellido.setText(apellido);
  }

  public void setDni(String dni) {
    txtDni.setText(dni);
  }

  public void limpiarCampos() {
    txtNombre.setText("");
    txtApellido.setText("");
    txtDni.setText("");
  }

  public void cargarMedico(MedicoEntity medico) {
    if (medico != null) {
      setNombre(medico.getNombre());
      setApellido(medico.getApellido());
      setDni(medico.getDni());
    } else {
      limpiarCampos();
    }
  }

  public boolean validarCampos() {
    return !getNombre().isEmpty() && !getApellido().isEmpty() && !getDni().isEmpty();
  }

  // Métodos para configurar listeners de botones
  public void setGuardarListener(ActionListener listener) {
    btnGuardar.addActionListener(listener);
  }

  public void setEliminarListener(ActionListener listener) {
    btnEliminar.addActionListener(listener);
  }

  public void setLimpiarListener(ActionListener listener) {
    btnLimpiar.addActionListener(listener);
  }
}
