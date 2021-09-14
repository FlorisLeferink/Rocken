package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.Shift;

/**
 * 
 * @author knol
 * @version 2018-08-30
 */
public class ShiftListTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private Vector<Vector> data = new Vector<Vector>();

	private static String[] COL_NAMES;

	public ShiftListTableModel() {
		setData(null);
	}

	public ShiftListTableModel(Vector<Vector> data) {
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

	public void setCol(List<String> day) {
		List<String> list = new ArrayList<String>();
		list.add("Role");
		for (String string : day) {
			list.add(string);

		}
		COL_NAMES = list.toArray(new String[0]);

	}

	public void setData(Vector<Vector> days) {
		if (days != null) {
			this.data = days;
		} else {
			this.data = new Vector<Vector>(0);
		}
		// super.fireTableDataChanged();
	}

	public Vector<Shift> getShiftListOfColumn(int index) {
		if (index < 0 || index >= data.size()) {
			return null;
		}
		return this.data.get(index);
	}

	public boolean isCellEditable(int row, int col) {
		boolean isData = false;
		if (getValueAt(row, col) != null || row == 0) {
			isData = true;
		}
		return isData;
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		switch (col) {
		case 0:
			return null;
		case 1:
			return data.get(row).get(col - 1);
		case 2:
			return data.get(row).get(col - 1);
		case 3:
			return data.get(row).get(col - 1);
		case 4:
			return data.get(row).get(col - 1);
		case 5:
			return data.get(row).get(col - 1);
		case 6:
			return data.get(row).get(col - 1);
		case 7:
			return data.get(row).get(col - 1);

		default:
			return "UNKNOLWN COL NAME";
		}
	}

	@Override
	public int getRowCount() {

		return (data == null ? 0 : data.size());
	}

}
