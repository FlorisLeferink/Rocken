package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.ShiftController;
import controller.ShiftRequestController;
import model.Employee;
import model.Shift;
import model.ShiftRequest;

public class RequestShiftDialogBox extends JDialog {

	ShiftRequestController shiftRequestController;
	ShiftController shiftController;
	private final JPanel contentPanel = new JPanel();
	private JLabel dateLbl;
	private JLabel noteLbl;
	private JLabel startAndEndTimeLbl;
	private JTextField commentTxt;
	private ShiftRequest currentShiftRequest;
	private Monitor monitor;
	/**
	 * Create the dialog.
	 * @param monitor 
	 */
	public RequestShiftDialogBox(String day, Shift shift, Employee employee, Monitor monitor) {
		this.monitor = monitor;
		
		init(employee, shift);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel noteTitleLbl = new JLabel("Note til vagt:");
		noteTitleLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		noteTitleLbl.setBounds(10, 94, 105, 25);
		contentPanel.add(noteTitleLbl);

		dateLbl = new JLabel(day);
		dateLbl.setHorizontalAlignment(SwingConstants.CENTER);
		dateLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateLbl.setBounds(10, 10, 415, 25);
		contentPanel.add(dateLbl);

		noteLbl = new JLabel(shift.getNote());
		noteLbl.setVerticalAlignment(SwingConstants.TOP);
		noteLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		noteLbl.setBounds(10, 120, 416, 40);
		contentPanel.add(noteLbl);

		JLabel dateTitleLbl = new JLabel("Start og sluttidspunkt:");
		dateTitleLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dateTitleLbl.setBounds(10, 45, 147, 25);
		contentPanel.add(dateTitleLbl);

		startAndEndTimeLbl = new JLabel(shift.getStartAt() + " - " + shift.getEndAt());
		startAndEndTimeLbl.setVerticalAlignment(SwingConstants.TOP);
		startAndEndTimeLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		startAndEndTimeLbl.setBounds(10, 74, 416, 25);
		contentPanel.add(startAndEndTimeLbl);

		JLabel lblEventuelleKommentarer = new JLabel("Eventuelle kommentarer:");
		lblEventuelleKommentarer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEventuelleKommentarer.setBounds(10, 155, 159, 25);
		contentPanel.add(lblEventuelleKommentarer);

		JLabel noteLbl_1 = new JLabel((String) null);
		noteLbl_1.setVerticalAlignment(SwingConstants.TOP);
		noteLbl_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		noteLbl_1.setBounds(9, 203, 416, 40);
		contentPanel.add(noteLbl_1);

		commentTxt = new JTextField();
		if(currentShiftRequest != null) {
			commentTxt.setEnabled(false);
		}
		commentTxt.setBounds(10, 182, 269, 40);
		contentPanel.add(commentTxt);
		commentTxt.setColumns(10);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton confirmBtn = new JButton();
		if (currentShiftRequest != null)
			confirmBtn.setText("Afmeld vagt");
		else
			confirmBtn.setText("Tilmeld vagt");

		confirmBtn.addActionListener(e -> confirm(employee, shift));
		confirmBtn.setActionCommand("OK");
		buttonPane.add(confirmBtn);
		getRootPane().setDefaultButton(confirmBtn);

		JButton cancelBtn = new JButton("Afbryd");
		cancelBtn.addActionListener(e -> cancel());
		cancelBtn.setActionCommand("Cancel");
		buttonPane.add(cancelBtn);
	}
	
	private void init(Employee employee, Shift shift) {
		shiftController = new ShiftController();
		shiftRequestController = new ShiftRequestController();
		currentShiftRequest = shiftController.getEmployeesShiftRequestForShift(employee, shift);
	}

	public void confirm(Employee employee, Shift shift) {
		if (currentShiftRequest != null) {
			shiftRequestController.removeShiftReqestByID(currentShiftRequest.getId());
			// Så skal vi reloade noget GUI
		} else {
			shiftRequestController.createNewShiftRequest(commentTxt.getText(), shift, employee);
		}
		monitor.performChange();
		monitor.changeMade();
		dispose();
	}

	public void cancel() {
		dispose();
	}
}
