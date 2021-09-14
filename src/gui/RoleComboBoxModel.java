package gui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import model.Role;

class RoleComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private Object selectedItem;

	private List<Role> anArrayList;

	public RoleComboBoxModel(List<Role> RolesList) {
	    anArrayList = RolesList;
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
		return anArrayList.get(i).getName();
	}
}