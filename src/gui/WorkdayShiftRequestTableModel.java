package gui;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import controller.RoleController;
import controller.EmployeeController;
import model.Employee;
import model.Shift;
import model.ShiftRequest;

public class WorkdayShiftRequestTableModel extends DefaultTableModel {


	private List<ShiftRequest> data = new ArrayList<>();
	private static final String[] COL_NAMES = { "Fornavn", "Efternavn", "Role", "Level", "Note" };

	public WorkdayShiftRequestTableModel() {
		setData(null);
	
	}

	public WorkdayShiftRequestTableModel(List<ShiftRequest> data) {
		setData(data);

	}

	@Override
	public int getColumnCount() {
		return COL_NAMES.length;
	}

	@Override
	public String getColumnName(int column) {
		return COL_NAMES[column];
	}
	public void setData(List<ShiftRequest> data) {
		if (data != null) {
			this.data = data;
		} else {
			this.data = new ArrayList<>(0);
		}
		super.fireTableDataChanged();
	}
	public List<ShiftRequest> getData(){
		return data;
	}
	public ShiftRequest getShiftsOfRow(int index) {
		if (index < 0 || index >= data.size()) {
			return null;
		}
		return this.data.get(index);
	}

	@Override
	public Object getValueAt(int row, int column) {
		ShiftRequest e = data.get(row);

		switch (column) {
		case 0:
			return e.getEmployee().getFname();
		case 1:
			return e.getEmployee().getLname();
		case 2:
			return e.getEmployee().getRole().getName();
		case 3:
			return  e.getEmployee().getLevel().getName();
		case 4:
			return e.getNote();
		
	
		default:
			return "UNKNOLWN COL NAME";
		}
	}


	@Override
	public int getRowCount() {
		return (data == null ? 0 : data.size());
	}
}
