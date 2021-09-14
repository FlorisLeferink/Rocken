package gui;

import java.awt.Component;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import model.Shift;
import model.ShiftRequest;

class WorkdayShiftsWithShiftRequestRendererTableModel implements TableCellRenderer {
	private List<Shift> shifts;
	private WorkdayShiftRequestTableModel shiftRequestTableModel;
	private JLabel label;
	private String lbl;
	private int row;
	private JTable newTable;
	

	WorkdayShiftsWithShiftRequestRendererTableModel(JTable table, List<Shift> shifts) {
		this.shifts = shifts;
		label = new JLabel();
		shiftRequestTableModel = new WorkdayShiftRequestTableModel();
		newTable = new JTable();
	}



	

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		lbl = (value == null) ? "" : value.toString();
		this.row = row;
		if (column == 2) {
			List<ShiftRequest> list = shifts.get(row).getListOfShiftRequest();
			
			shiftRequestTableModel.setData(list);
			newTable.setModel(shiftRequestTableModel);

			return newTable;
		} else {
			label.setText(lbl);
			return label;
		}
	}

}