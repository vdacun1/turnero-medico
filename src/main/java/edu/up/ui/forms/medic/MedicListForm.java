package edu.up.ui.forms.medic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import edu.up.controllers.dto.MedicoDTO;
import edu.up.models.entities.MedicoEntity;

/**
 * Formulario para mostrar la lista de médicos en una tabla.
 * Se encarga únicamente de la parte gráfica de la lista.
 */
public class MedicListForm {
  private final JPanel panel;
  private final DefaultTableModel tableModel;
  private final JTable medicosTable;
  private final JButton btnRefrescar;

  public MedicListForm() {
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
    this.btnRefrescar = new JButton("Refrescar Lista");

    initializeUI();
  }

  private void initializeUI() {
    // Borde simple con título
    panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createTitledBorder("Lista de Médicos"),
        javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));

    // Scroll pane para la tabla
    JScrollPane scrollPane = new JScrollPane(medicosTable);
    scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    panel.add(scrollPane, BorderLayout.CENTER);

    // Panel para el botón de refrescar
    JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    refreshPanel.add(btnRefrescar);
    panel.add(refreshPanel, BorderLayout.SOUTH);
  }

  // Getters para acceder al panel y componentes
  public JPanel getPanel() {
    return panel;
  }

  public JTable getTable() {
    return medicosTable;
  }

  // Métodos para manejar datos de la tabla
  public void cargarMedicos(List<MedicoEntity> medicos) {
    // Limpiar tabla
    tableModel.setRowCount(0);

    // Cargar médicos
    for (MedicoEntity medico : medicos) {
      Object[] row = {
          medico.getId(),
          medico.getNombre(),
          medico.getApellido(),
          medico.getDni()
      };
      tableModel.addRow(row);
    }
  }

  public void cargarMedicosDTO(List<MedicoDTO> medicos) {
    // Limpiar tabla
    tableModel.setRowCount(0);

    // Cargar médicos
    for (MedicoDTO medico : medicos) {
      Object[] row = {
          medico.getId(),
          medico.getNombre(),
          medico.getApellido(),
          medico.getDni()
      };
      tableModel.addRow(row);
    }
  }

  public void limpiarTabla() {
    tableModel.setRowCount(0);
  }

  public int getSelectedRow() {
    return medicosTable.getSelectedRow();
  }

  public void clearSelection() {
    medicosTable.clearSelection();
  }

  public Object getValueAt(int row, int column) {
    return tableModel.getValueAt(row, column);
  }

  public MedicoEntity getMedicoFromSelectedRow() {
    int selectedRow = getSelectedRow();
    if (selectedRow >= 0) {
      Long id = (Long) getValueAt(selectedRow, 0);
      String nombre = (String) getValueAt(selectedRow, 1);
      String apellido = (String) getValueAt(selectedRow, 2);
      String dni = (String) getValueAt(selectedRow, 3);

      MedicoEntity medico = new MedicoEntity(nombre, apellido, dni);
      medico.setId(id);
      return medico;
    }
    return null;
  }

  public MedicoDTO getMedicoDTOFromSelectedRow() {
    int selectedRow = getSelectedRow();
    if (selectedRow >= 0) {
      Long id = (Long) getValueAt(selectedRow, 0);
      String nombre = (String) getValueAt(selectedRow, 1);
      String apellido = (String) getValueAt(selectedRow, 2);
      String dni = (String) getValueAt(selectedRow, 3);

      return new MedicoDTO(id, nombre, apellido, dni);
    }
    return null;
  }

  // Métodos para configurar listeners
  public void setSelectionListener(ListSelectionListener listener) {
    medicosTable.getSelectionModel().addListSelectionListener(listener);
  }

  public void setRefrescarListener(ActionListener listener) {
    btnRefrescar.addActionListener(listener);
  }
}
