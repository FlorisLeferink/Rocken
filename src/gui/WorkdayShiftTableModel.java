package gui;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import controller.RoleController;
import controller.EmployeeController;
import model.Employee;
import model.Shift;

/**
 * 
 * @author knol
 * @version 2018-08-30
 */
public class WorkdayShiftTableModel extends DefaultTableModel {
	private RoleController roleController;
	private EmployeeController employeeController;
	private List<Shift> data = new ArrayList<>();
	private static final String[] COL_NAMES = { "Start", "Slut", "Note", "Rolle", "Medarbejder" };

	public WorkdayShiftTableModel() {
		setData(null);
		roleController = new RoleController();
		employeeController = new EmployeeController();
	}

	public WorkdayShiftTableModel(List<Shift> data) {
		setData(data);
		roleController = new RoleController();
		employeeController = new EmployeeController();
	}

	@Override
	public int getColumnCount() {
		return COL_NAMES.length;
	}

	@Override
	public String getColumnName(int column) {
		return COL_NAMES[column];
	}

	public void setData(List<Shift> data) {
		if (data != null) {
			this.data = data;
		} else {
			this.data = new ArrayList<>(0);
		}
		fireTableDataChanged();
	}
	public List<Shift> getData(){
		return data;
	}

	public Shift getShiftsOfRow(int index) {
		if (index < 0 || index >= data.size()) {
			return null;
		}
		return this.data.get(index);
	}

	@Override
	public Object getValueAt(int row, int column) {
		Shift e = data.get(row);

		switch (column) {
		case 0:
			return e.getStartAt();
		case 1:
			return e.getEndAt();
		case 2:
			return e.getNote();
		case 3:
			return e.getRole().getName();
		case 4:
			String employeeNo = "";
			Employee currEmployee = e.getEmployee();
			
			if (currEmployee != null) {
				employeeNo = currEmployee.getFname() + " " + currEmployee.getLname();
			}
			return employeeNo;
		default:
			return "UNKNOLWN COL NAME";
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int colIndex) {
		Shift e = data.get(rowIndex);
		switch (colIndex) {
		case 0:
			e.setStartAt(LocalTime.parse(value.toString()));
			break;
		case 1:
			e.setEndAt(LocalTime.parse(value.toString()));
			break;
		case 2:
			e.setNote(value.toString());
			break;
		case 3:
			e.setRole(roleController.findRoleByName(value.toString()));
			break;
		case 4:
			e.setEmployee(employeeController.findEmployeeByFirstAndLastName(value.toString()));
			break;
		}
		fireTableRowsUpdated(rowIndex, colIndex);
	}

	@Override
	public int getRowCount() {
		return (data == null ? 0 : data.size());
	}

}
