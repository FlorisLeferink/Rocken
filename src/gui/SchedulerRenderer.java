package gui;

import java.awt.Component;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import model.Employee;
import model.Shift;

class SchedulerRenderer implements TableCellRenderer, TableCellEditor {
	private Vector<Vector> listOfDays;

	private Shift shift;
	private JButton button;
	private String lbl;
	private int row;
	private boolean toggleAdmin;
	private String day;
	private Employee employee;

	SchedulerRenderer(JTable table, Vector<Vector> days, boolean toggleAdmin, String currDate, Employee employee,
			Monitor monitor) {
		button = new JButton();
		button.addActionListener(e -> selectDay(day, monitor));

		this.listOfDays = days;
		this.toggleAdmin = toggleAdmin;

		this.day = currDate;
		this.employee = employee;
	}

	private void selectDay(String date, Monitor monitor) {
		if (toggleAdmin) {
			WorkdayDialogbox dialog = new WorkdayDialogbox(date, monitor);
			dialog.setModal(true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} else {
			// Tilføj monitor hvis vi vil opdatere farver på vagter efter valgt
			RequestShiftDialogBox dialog = new RequestShiftDialogBox(date, shift, employee, monitor);
			dialog.setModal(true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
	}

	private Shift getShifts(Object value) {
		Shift currShift = null;
		for (Vector vector : listOfDays) {
			for (int i = 0; i < vector.size(); i++) {
				if (vector.get(i) == value) {
					currShift = (Shift) vector.get(i);
				}
			}
		}
		return currShift;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		lbl = (value == null) ? "" : value.toString();
		if (toggleAdmin) {
			if (value != null) {
				shift = getShifts(value);
				if (shift != null) {
					if (shift.getEmployee() != null) {
						button.setText("<html>" + shift.getStartAt() + "-" + shift.getEndAt() + ("\n" + "<br>")
								+ shift.getRole().getName() + ("\n" + "<br>") + shift.getEmployee().getFname()
								+ "</html>");
					} else {
						button.setText("<html>" + shift.getStartAt() + "-" + shift.getEndAt() + ("\n" + "<br>")
								+ shift.getRole().getName() + ("\n" + "<br>") + "Ledig vagt" + "</html>");
					}
				}
			} else if (row == 0) {
				button.setText("<html>" + "Opret" + ("\n" + "<br>") + "arbejdsdag" + "</html>");
			}
			return (value == null && row != 0 ? null : button);
		} else {
			if (value != null) {
				shift = getShifts(value);
				if (shift.getEmployee() != null) {
					button.setText("<html>" + shift.getStartAt() + "-" + shift.getEndAt() + ("\n" + "<br>")
							+ shift.getRole().getName() + ("\n" + "<br>") + shift.getEmployee().getFname() + "</html>");
				} else {
					button.setText("<html>" + shift.getStartAt() + "-" + shift.getEndAt() + ("\n" + "<br>")
							+ shift.getRole().getName() + ("\n" + "<br>") + "Ledig vagt"+ "</html>");
				}
			}
			return (value == null ? null : button);
		}
	}

	@Override
	public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
			int column) {
		lbl = (value == null) ? "" : value.toString();
		if (toggleAdmin) {
			if (value != null) {
				shift = getShifts(value);
				if (shift.getEmployee() != null) {
					button.setText("<html>" + shift.getStartAt() + "-" + shift.getEndAt() + ("\n" + "<br>")
							+ shift.getNote() + ("\n" + "<br>") + shift.getRole().getName() + ("\n" + "<br>")
							+ shift.getEmployee().getFname() + "</html>");
				} else {
					button.setText(
							"<html>" + shift.getStartAt() + "-" + shift.getEndAt() + ("\n" + "<br>") + shift.getNote()
									 + shift.getRole().getName() + ("\n" + "<br>") + "Ledig vagt"+ "</html>");
				}
			} else if (row == 0) {
				button.setText("<html>" + "Opret" + ("\n" + "<br>") + "arbejdsdag" + "</html>");
			}

			this.row = row;
			return (value == null && row != 0 ? null : button);
		} else {
			this.row = row;
			if (value != null) {
				shift = getShifts(value);
				if (shift.getEmployee() != null) {
					button.setText("<html>" + shift.getStartAt() + "-" + shift.getEndAt() + ("\n" + "<br>")
							+ shift.getNote() + ("\n" + "<br>") + shift.getRole().getName() + ("\n" + "<br>")
							+ shift.getEmployee().getFname() + "</html>");
				} else {
					button.setText(
							"<html>" + shift.getStartAt() + "-" + shift.getEndAt() + ("\n" + "<br>") + shift.getNote()
									 + shift.getRole().getName() + ("\n" + "<br>") + "Ledig vagt"+ "</html>");
				}
			}
			return (value == null ? null : button);
		}
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return false;
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
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
	}
}