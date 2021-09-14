package gui;

import java.awt.Component;
import java.util.EventObject;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import model.Role;
import model.Shift;
import model.ShiftRequest;

class WorkdayRendererTableModel implements TableCellRenderer, TableCellEditor {
	private JComboBox roleComboBox;
	private JComboBox shiftRequestComboBox;
	private String lbl;
	private int row;
	private RoleComboBoxModel roleComboBoxModel;
	private ShiftRequestComboBoxModel shiftRequestComboBoxModel;
	private List<Shift> shiftList;
	private TableCellEditor comboBoxEditor;
	private TableCellEditor comboBoxEditorEmployee;
	private TableCellEditor textFieldEditor;
	private TableCellEditor current;

	WorkdayRendererTableModel(JTable table, List<Role> list, List<Shift> shiftList) {
		roleComboBoxModel = new RoleComboBoxModel(list);
		roleComboBox = new JComboBox(roleComboBoxModel);
		comboBoxEditor = new DefaultCellEditor(roleComboBox);
		shiftRequestComboBoxModel = new ShiftRequestComboBoxModel();
		shiftRequestComboBox = new JComboBox(shiftRequestComboBoxModel);
		comboBoxEditorEmployee = new DefaultCellEditor(shiftRequestComboBox);
		textFieldEditor = new DefaultCellEditor(new JTextField());
		this.shiftList = shiftList;
		
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		lbl = (value == null) ? "" : value.toString();
		this.row = row;
		if (value != null) {
			if (column == 0 || column == 1 || column == 2) {
				current = textFieldEditor;
				JTextField field = (JTextField) current.getTableCellEditorComponent(table, value, isSelected, row,
						column);
				return field;
			} else if (column == 3) {
				current = comboBoxEditor;
				JComboBox combo = (JComboBox) current.getTableCellEditorComponent(table, value, isSelected, row,
						column);
				return combo;
			} else if (column == 4) {
				List<ShiftRequest> reqList = shiftList.get(row).getListOfShiftRequest();

				shiftRequestComboBoxModel = new ShiftRequestComboBoxModel(reqList);
				shiftRequestComboBox = new JComboBox(shiftRequestComboBoxModel);
				comboBoxEditorEmployee = new DefaultCellEditor(shiftRequestComboBox);

				current = comboBoxEditorEmployee;
				JComboBox combo = (JComboBox) current.getTableCellEditorComponent(table, value, isSelected, row,
						column);
				return combo;
			}
		}
		return table;
	}

	@Override
	public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
			int column) {
		lbl = (value == null) ? "" : value.toString();
		this.row = row;
		if (value != null) {
			if (column == 0 || column == 1 || column == 2) {
				current = textFieldEditor;
				JTextField field = (JTextField) current.getTableCellEditorComponent(table, value, isSelected, row,
						column);
				return field;
			} else if (column == 3) {
				current = comboBoxEditor;
				JComboBox combo = (JComboBox) current.getTableCellEditorComponent(table, value, isSelected, row,
						column);
				return combo;
			} else if (column == 4) {
				List<ShiftRequest> reqList = shiftList.get(row).getListOfShiftRequest();

				shiftRequestComboBoxModel = new ShiftRequestComboBoxModel(reqList);
				shiftRequestComboBox = new JComboBox(shiftRequestComboBoxModel);
				comboBoxEditorEmployee = new DefaultCellEditor(shiftRequestComboBox);

				current = comboBoxEditorEmployee;
				JComboBox combo = (JComboBox) current.getTableCellEditorComponent(table, value, isSelected, row,
						column);
				return combo;
			}
		}
		return table;
	}

	@Override
	public Object getCellEditorValue() {
		return (current == null ? null : current.getCellEditorValue());
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		return true;
	}

	@Override
	public void cancelCellEditing() {
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		comboBoxEditorEmployee.addCellEditorListener(l);
		comboBoxEditor.addCellEditorListener(l);
		textFieldEditor.addCellEditorListener(l);
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
	}
}