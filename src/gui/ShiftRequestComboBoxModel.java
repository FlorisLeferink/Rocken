package gui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import model.ShiftRequest;

public class ShiftRequestComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private Object selectedItem;

	private List<ShiftRequest> anArrayList;

	
	public ShiftRequestComboBoxModel() {
	  }
	
	public ShiftRequestComboBoxModel(List<ShiftRequest> shiftRequestList) {
	    anArrayList = shiftRequestList;
	  }

	public Object getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Object newValue) {
		selectedItem = newValue;
	}

	public int getSize() {
		return (anArrayList == null) ? 0 : anArrayList.size();
	}

	public Object getElementAt(int i) {
		String fname = anArrayList.get(i).getEmployee().getFname();
		String lname = anArrayList.get(i).getEmployee().getLname();
		return fname + " " + lname;
	}
}
