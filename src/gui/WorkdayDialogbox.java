package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import controller.RoleController;
import controller.ShiftController;
import controller.WorkdayController;
import model.Shift;

public class WorkdayDialogbox extends JDialog {
	private ShiftController shiftController;
	private WorkdayController workdayController;
	private RoleController roleController;
	private RoleComboBoxModel roleComboBoxModel;
	private WorkdayShiftTableModel workdayShiftTableModel;
	private WorkdayRendererTableModel workdayRendererTableModel;
	private WorkdayShiftsWithShiftsRequestTableModel workdayShiftsWithShiftsRequestTableModel;
	private WorkdayShiftsWithShiftRequestRendererTableModel workdayShiftsWithShiftRequestRendererTableModel;
	private JTable shiftTableList;
	private JTable shiftsRequestTable;
	private final JPanel contentPanel = new JPanel();
	private LocalDate date;
	private JButton btnCreateShiftButton;
	private JTextField tftEndAt;
	private JTextField tftStartAt;
	private JTextField tftNote;
	private JComboBox cbRole;
	private JLabel lblErrorStartAt;
	private JLabel lblErrorEndAt;

	/**
	 * Create the dialog.
	 * @param monitor 
	 * 
	 * @return
	 */

	public WorkdayDialogbox(String day, Monitor monitor) {
		setBounds(100, 100, 1250, 700);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.workdayController = new WorkdayController();
		date = LocalDate.parse(day);
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
		JLabel lblDateLabel = new JLabel(date.format(formatters));
		JScrollPane sPShiftsRequest = new JScrollPane();
		shiftsRequestTable = new JTable();
		sPShiftsRequest.setViewportView(shiftsRequestTable);
		shiftTableList = new JTable();
		init();
		fillTable();
		JScrollPane scrollPane = new JScrollPane(shiftTableList);

		tftStartAt = new JTextField();
		tftStartAt.setColumns(10);

		tftEndAt = new JTextField();
		tftEndAt.setColumns(10);

		tftNote = new JTextField();

		tftNote.setColumns(10);

		JLabel lblStartAtLabel = new JLabel("Vagt start:");

		JLabel lblEndAtLabel = new JLabel("Vagt slut:");

		JLabel lblNoteLabel = new JLabel("Note:");

		JLabel lblRoleLabel = new JLabel("Rolle:");

		cbRole = new JComboBox(roleComboBoxModel);
		if (roleController.ListOfRole().size() != 0) {
			cbRole.setSelectedIndex(0);
		}

		btnCreateShiftButton = new JButton("Ny vagt");

		btnCreateShiftButton.addActionListener(e -> createNewShift());

		lblErrorStartAt = new JLabel("Sæt tid som 22:00 eller 2200");
		lblErrorStartAt.setForeground(Color.RED);
		lblErrorStartAt.setVisible(false);
		lblErrorEndAt = new JLabel("Sæt tid som 22:00 eller 2200");
		lblErrorEndAt.setForeground(Color.RED);
		lblErrorEndAt.setVisible(false);

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(lblStartAtLabel)
								.addComponent(lblEndAtLabel).addComponent(lblNoteLabel).addComponent(lblRoleLabel))
						.addGap(18)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnCreateShiftButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(tftStartAt, Alignment.LEADING).addComponent(tftEndAt, Alignment.LEADING)
								.addComponent(tftNote)
								.addComponent(cbRole, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(lblErrorEndAt)
								.addComponent(lblErrorStartAt))
						.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 858, GroupLayout.PREFERRED_SIZE)
						.addGap(18))
				.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap()
						.addComponent(sPShiftsRequest, GroupLayout.DEFAULT_SIZE, 1206, Short.MAX_VALUE)
						.addContainerGap())
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup().addGap(490)
						.addComponent(lblDateLabel).addContainerGap(681, Short.MAX_VALUE)));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addComponent(lblDateLabel)
						.addGap(31)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel
								.createSequentialGroup()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblErrorStartAt).addComponent(lblStartAtLabel)
										.addGroup(gl_contentPanel.createSequentialGroup()
												.addComponent(tftStartAt, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(12)
												.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(tftEndAt, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblErrorEndAt).addComponent(lblEndAtLabel))
												.addGap(14)
												.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(tftNote, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblNoteLabel))
												.addGap(21)
												.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(cbRole, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblRoleLabel))))
								.addGap(17).addComponent(btnCreateShiftButton))
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
						.addGap(18)
						.addComponent(sPShiftsRequest, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(e -> exit(true, monitor));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(e -> exit(false, monitor));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}

		}
	}

	private void init() {
		workdayController = new WorkdayController();
		roleController = new RoleController();
		shiftController = new ShiftController();
		roleComboBoxModel = new RoleComboBoxModel(roleController.ListOfRole());
		workdayController.setCurrWorkday(date);
		workdayShiftTableModel = new WorkdayShiftTableModel();
		workdayShiftsWithShiftsRequestTableModel = new WorkdayShiftsWithShiftsRequestTableModel();

	}
	/**
	 * Validated the time String only can contains 00:00 to 23:59
	 * @param time
	 * @return
	 */
	public boolean validTime24(String time) {

		return time.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
	}
	
	/**
	 * Convert the time String to type LocalTime. If time String is "20:00" validTime24 == true, 
	 * If time String is "2000" convert to "20" and "00" and combine into "20:00" 
	 * @param time
	 * @return
	 */
	private LocalTime convertStringToLocalTime(String time) {
		String convertTime = time;
		if (validTime24(time) == false) {
			String convertHours = time.substring(0, 2);
			String convertMins = time.substring(2, time.length());
			convertTime = convertHours + ":" + convertMins;

		}

		return LocalTime.parse(convertTime);
	}

	private void createNewShift() {
		String startAt = tftStartAt.getText();
		String endAt = tftEndAt.getText();
		String note = tftNote.getText();
		int roleId = cbRole.getSelectedIndex()+1;
		if (validTime24(convertStringToLocalTime(startAt).toString())
				&& validTime24(convertStringToLocalTime(endAt).toString())) {

			workdayController.createNewShift(convertStringToLocalTime(startAt), convertStringToLocalTime(endAt),
					note, roleId);

		} else if (validTime24(startAt)) {
			lblErrorEndAt.setVisible(true);
			lblErrorStartAt.setVisible(false);
		} else if (validTime24(endAt)) {
			lblErrorStartAt.setVisible(true);
			lblErrorEndAt.setVisible(false);
		} else {
			lblErrorStartAt.setVisible(true);
			lblErrorEndAt.setVisible(true);
		}
		fillTable();
	}

	private void fillTable() {
		shiftTableList.setModel(workdayShiftTableModel);
		shiftTableList.setRowHeight(20);
		shiftsRequestTable.setModel(workdayShiftsWithShiftsRequestTableModel);

		List<Shift> ShiftsList = workdayController.getShiftForWorkday();

		workdayShiftTableModel.setData(ShiftsList);
		workdayShiftsWithShiftsRequestTableModel.setData(ShiftsList);

		shiftTableList.repaint();
		shiftTableList.revalidate();
		renderAndEditTable(ShiftsList);
	}

	private void renderAndEditTable(List<Shift> ShiftsList) {
		workdayShiftsWithShiftRequestRendererTableModel = new WorkdayShiftsWithShiftRequestRendererTableModel(
				shiftsRequestTable, ShiftsList);
		workdayRendererTableModel = new WorkdayRendererTableModel(shiftTableList, roleController.ListOfRole(),
				workdayController.getShiftForWorkday());
		shiftTableList.getColumn("Start").setPreferredWidth(10);
		shiftTableList.getColumn("Slut").setPreferredWidth(10);
		shiftTableList.getColumn("Note").setPreferredWidth(300);
		shiftsRequestTable.getColumn("Start").setPreferredWidth(10);
		shiftsRequestTable.getColumn("Slut").setPreferredWidth(10);
		shiftsRequestTable.getColumn("Anmodninger").setPreferredWidth(1000);

		for (int i = 0; i < workdayShiftTableModel.getColumnCount(); i++) {
			shiftTableList.getColumn(workdayShiftTableModel.getColumnName(i))
					.setCellRenderer(workdayRendererTableModel);
			shiftTableList.getColumn(workdayShiftTableModel.getColumnName(i))
					.setCellEditor(new WorkdayRendererTableModel(shiftTableList, roleController.ListOfRole(),
							workdayController.getShiftForWorkday()));
			if (shiftsRequestTable.getColumnCount() > i) {
				shiftsRequestTable.getColumn(shiftsRequestTable.getColumnName(i))
						.setCellRenderer(workdayShiftsWithShiftRequestRendererTableModel);
			}
		}

		shiftsRequestTable.setRowHeight(getShiftsRequestTableSize(ShiftsList) * 16);
	}

	private int getShiftsRequestTableSize(List<Shift> ShiftsList) {
		int size = 1;
		for (Shift shift : ShiftsList) {
			if (shift.getListOfShiftRequest() != null) {
				int listSize = shift.getListOfShiftRequest().size();
				if (size < listSize) {
					size = listSize;
				}
			}
		}
	
		return size;
	}

	private void exit(boolean confirm, Monitor monitor) {
		if (confirm) {
			for (Shift shifts : workdayShiftTableModel.getData()) {
				shiftController.updateShift(shifts);
			}
			workdayController.saveWorkday();
			//Update the GUI
			monitor.performChange();
			monitor.changeMade();
		}
		dispose();
	}
}
