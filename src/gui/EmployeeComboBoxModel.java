package gui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import model.Employee;

class EmployeeComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private Object selectedItem;

	private List<Employee> anArrayList;

	public EmployeeComboBoxModel(List<Employee> EmployeeList) {
	    anArrayList = EmployeeList;
	  }

	public Object getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Object newValue) {
		selectedItem = newValue;
	}

	public int getSize() {
		return anArrayList.size();
	}

	public Object getElementAt(int i) {
		String fname = anArrayList.get(i).getFname();
		String lname = anArrayList.get(i).getLname();
		return fname + " " + lname;
	}
}