package edu.up.ui.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.up.models.entities.MedicoEntity;
import edu.up.models.repositories.MedicoRepository;
import edu.up.models.repositories.MedicoRepositoryImpl;
import edu.up.ui.forms.medic.MedicForm;
import edu.up.ui.forms.medic.MedicListForm;
import edu.up.utils.Logger;

/**
 * Vista para el CRUD de médicos del turnero.
 * Actúa como coordinador entre los formularios y la lógica de negocio.
 */
public class MedicView implements IView {
  private static final String NAME = "MEDIC";
  private static final String TITLE = "Médicos";
  private final JPanel panel;
  private final MedicoRepository medicoRepository;
  private final MedicForm medicForm;
  private final MedicListForm medicListForm;
  private MedicoEntity selectedMedico = null;

  public MedicView() {
    this.medicoRepository = new MedicoRepositoryImpl();
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
    try {
      Logger.info("MedicView", "Iniciando guardado de médico");

      if (!medicForm.validarCampos()) {
        Logger.warn("MedicView", "Intento de guardar médico con campos vacíos");
        JOptionPane.showMessageDialog(panel,
            "Por favor complete todos los campos",
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      MedicoEntity medico = new MedicoEntity(
          medicForm.getNombre(),
          medicForm.getApellido(),
          medicForm.getDni());

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
      try {
        Long id = (Long) medicListForm.getValueAt(selectedRow, 0);
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

  private void cargarMedicoEnFormulario() {
    selectedMedico = medicListForm.getMedicoFromSelectedRow();
    medicForm.cargarMedico(selectedMedico);
  }

  private void limpiarFormulario() {
    medicForm.limpiarCampos();
    selectedMedico = null;
    medicListForm.clearSelection();
  }

  private void loadMedicos() {
    try {
      Logger.info("MedicView", "Cargando lista de médicos");

      // Cargar médicos
      List<MedicoEntity> medicos = medicoRepository.findAll();
      medicListForm.cargarMedicos(medicos);

      Logger.info("MedicView", "Lista de médicos cargada exitosamente");

    } catch (Exception e) {
      Logger.error("MedicView", "Error al cargar médicos", e);

      // Si no hay datos, mostrar tabla vacía sin error
      if (e.getMessage().contains("No medicos found")) {
        Logger.warn("MedicView", "No se encontraron médicos en la base de datos");
        medicListForm.limpiarTabla();
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
