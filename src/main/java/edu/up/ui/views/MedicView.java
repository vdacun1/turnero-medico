package edu.up.ui.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import edu.up.models.entities.MedicoEntity;
import edu.up.models.repositories.MedicoRepository;
import edu.up.models.repositories.MedicoRepositoryImpl;
import edu.up.utils.Logger;

/**
 * Vista para el CRUD de médicos del turnero.
 * Permite agregar, editar, eliminar y listar médicos.
 */
public class MedicView implements IView {
  public static final String NAME = "MEDIC";
  private final JPanel panel;
  private final MedicoRepository medicoRepository;
  private final DefaultTableModel tableModel;
  private final JTable medicosTable;
  private final JTextField txtNombre;
  private final JTextField txtApellido;
  private final JTextField txtDni;
  private MedicoEntity selectedMedico = null;

  public MedicView() {
    this.medicoRepository = new MedicoRepositoryImpl();
    this.panel = new JPanel(new BorderLayout());

    // Configurar modelo de tabla
    String[] columnNames = { "ID", "Nombre", "Apellido", "DNI/Código" };
    this.tableModel = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false; // Tabla no editable directamente
      }
    };

    this.medicosTable = new JTable(tableModel);
    this.txtNombre = new JTextField(20);
    this.txtApellido = new JTextField(20);
    this.txtDni = new JTextField(15);

    initializeUI();
    loadMedicos();
  }

  private void initializeUI() {
    // Panel contenedor con márgenes
    JPanel containerPanel = new JPanel(new BorderLayout());
    containerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Título
    JLabel titleLabel = new JLabel("Gestión de Médicos", SwingConstants.CENTER);
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 20, 0));
    containerPanel.add(titleLabel, BorderLayout.NORTH);

    // Panel principal dividido horizontalmente con espaciado
    JPanel mainPanel = new JPanel(new BorderLayout(15, 0));

    // Formulario de entrada a la izquierda
    JPanel formPanel = createFormPanel();
    mainPanel.add(formPanel, BorderLayout.WEST);

    // Tabla de médicos a la derecha
    JPanel tablePanel = createTablePanel();
    mainPanel.add(tablePanel, BorderLayout.CENTER);

    containerPanel.add(mainPanel, BorderLayout.CENTER);
    panel.add(containerPanel, BorderLayout.CENTER);
  }

  private JPanel createFormPanel() {
    JPanel formPanel = new JPanel(new GridBagLayout());

    // Borde simple con título
    formPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createTitledBorder("Datos del Médico"),
        javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));

    // Establecer ancho preferido para el formulario
    formPanel.setPreferredSize(new java.awt.Dimension(350, 0));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);

    // Nombre
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST;
    formPanel.add(new JLabel("Nombre:"), gbc);

    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    formPanel.add(txtNombre, gbc);

    // Apellido
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0.0;
    formPanel.add(new JLabel("Apellido:"), gbc);

    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    formPanel.add(txtApellido, gbc);

    // DNI
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0.0;
    formPanel.add(new JLabel("DNI/Código:"), gbc);

    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    formPanel.add(txtDni, gbc);

    // Panel de botones
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    // Botones estándar
    JButton btnGuardar = new JButton("Guardar");
    btnGuardar.addActionListener(e -> guardarMedico());

    JButton btnEliminar = new JButton("Eliminar");
    btnEliminar.addActionListener(e -> eliminarMedico());

    JButton btnLimpiar = new JButton("Limpiar");
    btnLimpiar.addActionListener(e -> limpiarFormulario());

    buttonPanel.add(btnGuardar);
    buttonPanel.add(btnEliminar);
    buttonPanel.add(btnLimpiar);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.insets = new Insets(15, 0, 0, 0);
    formPanel.add(buttonPanel, gbc);

    return formPanel;
  }

  private JPanel createTablePanel() {
    JPanel tablePanel = new JPanel(new BorderLayout());

    // Borde simple con título
    tablePanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createTitledBorder("Lista de Médicos"),
        javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));

    // Configurar tabla
    medicosTable.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        int selectedRow = medicosTable.getSelectedRow();
        if (selectedRow >= 0) {
          cargarMedicoEnFormulario(selectedRow);
        }
      }
    });
    JScrollPane scrollPane = new JScrollPane(medicosTable);
    scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    tablePanel.add(scrollPane, BorderLayout.CENTER);

    // Panel para el botón de refrescar
    JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    // Botón de refrescar estándar
    JButton btnRefrescar = new JButton("Refrescar Lista");
    btnRefrescar.addActionListener(e -> loadMedicos());
    refreshPanel.add(btnRefrescar);

    tablePanel.add(refreshPanel, BorderLayout.SOUTH);

    return tablePanel;
  }

  private void guardarMedico() {
    try {
      Logger.info("MedicView", "Iniciando guardado de médico");

      String nombre = txtNombre.getText().trim();
      String apellido = txtApellido.getText().trim();
      String dni = txtDni.getText().trim();

      if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
        Logger.warn("MedicView", "Intento de guardar médico con campos vacíos");
        JOptionPane.showMessageDialog(panel,
            "Por favor complete todos los campos",
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      MedicoEntity medico = new MedicoEntity(nombre, apellido, dni);
      if (selectedMedico != null) {
        medico.setId(selectedMedico.getId());
        Logger.info("MedicView", "Actualizando médico existente con ID: " + selectedMedico.getId());
      } else {
        Logger.info("MedicView", "Creando nuevo médico");
      }

      medicoRepository.save(medico);

      Logger.info("MedicView", "Médico guardado exitosamente");
      JOptionPane.showMessageDialog(panel,
          "Médico guardado exitosamente",
          "Éxito", JOptionPane.INFORMATION_MESSAGE);

      limpiarFormulario();
      loadMedicos();

    } catch (Exception e) {
      Logger.error("MedicView", "Error al guardar médico", e);
      JOptionPane.showMessageDialog(panel,
          "Error al guardar médico: " + e.getMessage(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void eliminarMedico() {
    int selectedRow = medicosTable.getSelectedRow();
    if (selectedRow < 0) {
      JOptionPane.showMessageDialog(panel,
          "Por favor seleccione un médico de la tabla",
          "Selección requerida", JOptionPane.WARNING_MESSAGE);
      return;
    }

    int confirmResult = JOptionPane.showConfirmDialog(panel,
        "¿Está seguro de que desea eliminar este médico?",
        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

    if (confirmResult == JOptionPane.YES_OPTION) {
      try {
        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        medicoRepository.delete(id);

        JOptionPane.showMessageDialog(panel,
            "Médico eliminado exitosamente",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);

        limpiarFormulario();
        loadMedicos();

      } catch (Exception e) {
        JOptionPane.showMessageDialog(panel,
            "Error al eliminar médico: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void cargarMedicoEnFormulario(int rowIndex) {
    Long id = (Long) tableModel.getValueAt(rowIndex, 0);
    String nombre = (String) tableModel.getValueAt(rowIndex, 1);
    String apellido = (String) tableModel.getValueAt(rowIndex, 2);
    String dni = (String) tableModel.getValueAt(rowIndex, 3);

    txtNombre.setText(nombre);
    txtApellido.setText(apellido);
    txtDni.setText(dni);

    selectedMedico = new MedicoEntity(nombre, apellido, dni);
    selectedMedico.setId(id);
  }

  private void limpiarFormulario() {
    txtNombre.setText("");
    txtApellido.setText("");
    txtDni.setText("");
    selectedMedico = null;
    medicosTable.clearSelection();
  }

  private void loadMedicos() {
    try {
      Logger.info("MedicView", "Cargando lista de médicos");

      // Limpiar tabla
      tableModel.setRowCount(0);

      // Cargar médicos
      List<MedicoEntity> medicos = medicoRepository.findAll();

      for (MedicoEntity medico : medicos) {
        Object[] row = {
            medico.getId(),
            medico.getNombre(),
            medico.getApellido(),
            medico.getDni()
        };
        tableModel.addRow(row);
      }

      Logger.info("MedicView", "Lista de médicos cargada exitosamente");

    } catch (Exception e) {
      Logger.error("MedicView", "Error al cargar médicos", e);

      // Si no hay datos, mostrar tabla vacía sin error
      if (e.getMessage().contains("No medicos found")) {
        Logger.warn("MedicView", "No se encontraron médicos en la base de datos");
        // Tabla ya está vacía, no mostrar error
      } else {
        JOptionPane.showMessageDialog(panel,
            "Error al cargar médicos: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
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
    return "Médicos";
  }

  @Override
  public boolean hasPermission() {
    // Mock: siempre true por ahora - aquí iría la lógica real de permisos
    return true;
  }

  @Override
  public IView[] getSubViews() {
    return null; // No tiene submenús
  }
}
