package edu.up.ui.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.up.controllers.MedicController;
import edu.up.controllers.dto.MedicoDTO;
import edu.up.ui.forms.medic.MedicForm;
import edu.up.ui.forms.medic.MedicListForm;

/**
 * Vista para el CRUD de médicos del turnero.
 * Actúa como coordinador entre los formularios y la lógica de negocio.
 * Usa DTOs para separar la vista del modelo.
 */
public class MedicView implements IView {
  private static final String NAME = "MEDIC";
  private static final String TITLE = "Médicos";
  private final JPanel panel;
  private final MedicController medicController;
  private final MedicForm medicForm;
  private final MedicListForm medicListForm;
  private MedicoDTO selectedMedico = null;

  public MedicView(MedicController medicController) {
    this.medicController = medicController;
    this.panel = new JPanel(new BorderLayout());
    this.medicForm = new MedicForm();
    this.medicListForm = new MedicListForm();

    initializeUI();
    setupEventListeners();
    loadMedicos();
  }

  private void initializeUI() {
    // Panel contenedor con márgenes
    Integer margin = 40;
    JPanel containerPanel = new JPanel(new BorderLayout());
    containerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(margin, margin, margin, margin));

    // Título
    JLabel titleLabel = new JLabel("Gestión de Médicos", SwingConstants.CENTER);
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 20, 0));
    containerPanel.add(titleLabel, BorderLayout.NORTH);

    // Panel principal dividido horizontalmente con espaciado
    JPanel mainPanel = new JPanel(new BorderLayout(15, 0));

    // Formulario de entrada a la izquierda
    mainPanel.add(medicForm.getPanel(), BorderLayout.WEST);

    // Tabla de médicos a la derecha
    mainPanel.add(medicListForm.getPanel(), BorderLayout.CENTER);

    containerPanel.add(mainPanel, BorderLayout.CENTER);
    panel.add(containerPanel, BorderLayout.CENTER);
  }

  private void setupEventListeners() {
    // Configurar listeners del formulario
    medicForm.setGuardarListener(e -> guardarMedico());
    medicForm.setEliminarListener(e -> eliminarMedico());
    medicForm.setLimpiarListener(e -> limpiarFormulario());

    // Configurar listeners de la lista
    medicListForm.setSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        cargarMedicoEnFormulario();
      }
    });
    medicListForm.setRefrescarListener(e -> loadMedicos());
  }

  private void guardarMedico() {
    // Validar campos en la vista
    if (!medicForm.validarCampos()) {
      JOptionPane.showMessageDialog(panel,
          "Por favor complete todos los campos",
          "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    // Crear DTO con los datos del formulario
    MedicoDTO medico = new MedicoDTO(
        medicForm.getNombre(),
        medicForm.getApellido(),
        medicForm.getDni());

    // Si estamos editando, agregar el ID
    if (selectedMedico != null) {
      medico.setId(selectedMedico.getId());
    }

    // Delegar la operación al controlador
    MedicController.OperationResult resultado = medicController.guardarMedico(medico);

    if (resultado.isExitoso()) {
      JOptionPane.showMessageDialog(panel,
          resultado.getMensaje(),
          "Éxito", JOptionPane.INFORMATION_MESSAGE);
      limpiarFormulario();
      loadMedicos();
    } else {
      JOptionPane.showMessageDialog(panel,
          resultado.getMensaje(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void eliminarMedico() {
    int selectedRow = medicListForm.getSelectedRow();
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
      Long id = (Long) medicListForm.getValueAt(selectedRow, 0);
      MedicController.OperationResult resultado = medicController.eliminarMedico(id);

      if (resultado.isExitoso()) {
        JOptionPane.showMessageDialog(panel,
            resultado.getMensaje(),
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiarFormulario();
        loadMedicos();
      } else {
        JOptionPane.showMessageDialog(panel,
            resultado.getMensaje(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void cargarMedicoEnFormulario() {
    selectedMedico = medicListForm.getMedicoDTOFromSelectedRow();
    medicForm.cargarMedicoDTO(selectedMedico);
  }

  private void limpiarFormulario() {
    medicForm.limpiarCampos();
    selectedMedico = null;
    medicListForm.clearSelection();
  }

  private void loadMedicos() {
    try {
      // Cargar médicos usando el controlador
      List<MedicoDTO> medicos = medicController.obtenerTodosMedicos();
      medicListForm.cargarMedicosDTO(medicos);
    } catch (Exception e) {
      medicListForm.limpiarTabla();
      JOptionPane.showMessageDialog(panel,
          "Error al cargar médicos: " + e.getMessage(),
          "Error", JOptionPane.ERROR_MESSAGE);
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
    return TITLE;
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
