package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.Shift;
import model.ShiftRequest;

public class WorkdayShiftsWithShiftsRequestTableModel extends DefaultTableModel {


	private List<Shift> data = new ArrayList<>();
	private static final String[] COL_NAMES = { "Start", "Slut", "Anmodninger"};

	public WorkdayShiftsWithShiftsRequestTableModel() {
		setData(null);
	
	}

	public WorkdayShiftsWithShiftsRequestTableModel(List<Shift> data) {
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
	public void setData(List<Shift> data) {
		if (data != null) {
			this.data = data;
		} else {
			this.data = new ArrayList<>(0);
		}
		super.fireTableDataChanged();
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
			return e.getListOfShiftRequest();
		
		
	
		default:
			return "UNKNOLWN COL NAME";
		}
	}


	@Override
	public int getRowCount() {
		return (data == null ? 0 : data.size());
	}
}