package gui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import model.Level;


public class LevelComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private Object selectedItem;

	private List<Level> anArrayList;

	public LevelComboBoxModel(List<Level> listOfRoles) {
	    anArrayList = listOfRoles;
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
