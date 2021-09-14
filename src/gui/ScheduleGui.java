package gui;

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;

import controller.ScheduleController;
import controller.WorkdayController;
import model.Employee;

public class ScheduleGui extends JPanel implements Runnable {
	private int weekChange = 0;
	private JTable scheduleTable;
	private ShiftListTableModel shiftListTableModel;
	private ScheduleController scheduleController;
	private WorkdayController workdayController;
	private List<String> listOfDates;
	private Vector<Vector> days;
	private boolean toggleAdmin = false;
	private JToggleButton tglbtnAdminToggleButton;
	private Employee employee;
	private Monitor monitor;

	public ScheduleGui(Employee employee, Monitor monitor) {
		this.employee = employee;
		this.monitor = monitor;

		init();

		Thread thread = new Thread(this);
		thread.start();

		getPreferredSize().setSize(1000, 600);
		JButton btnCurrWeekButton = new JButton("Nuværende uge");
		btnCurrWeekButton.setBounds(180, 5, 148, 23);
		btnCurrWeekButton.addActionListener(e -> changeWeek(0));
		setLayout(null);
		add(btnCurrWeekButton);

		JScrollPane scrollPane_schedule = new JScrollPane();
		scrollPane_schedule.setBounds(-1, 33, 945, 470);
		scrollPane_schedule.getPreferredSize().setSize(1000, 600);
		add(scrollPane_schedule);
		scheduleTable.getPreferredSize().setSize(1000, 600);
		scrollPane_schedule.setViewportView(scheduleTable);
		scrollPane_schedule.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel panel = new JPanel();
		panel.setBounds(220, 440, 10, 10);
		add(panel);

		JButton btnNextWeekButton = new JButton("En uge frem");
		btnNextWeekButton.addActionListener(e -> changeWeek(weekChange + 1));
		btnNextWeekButton.setBounds(338, 5, 157, 23);
		add(btnNextWeekButton);

		JButton btnPrevWeekButton = new JButton("En uge tilbage");
		btnPrevWeekButton.addActionListener(e -> changeWeek(weekChange - 1));
		btnPrevWeekButton.setBounds(49, 5, 121, 23);
		add(btnPrevWeekButton);

		tglbtnAdminToggleButton = new JToggleButton("Admin mode: Tænd", false);
		tglbtnAdminToggleButton.setBounds(759, 5, 185, 23);
		tglbtnAdminToggleButton.addActionListener(e -> itemStateChanged(tglbtnAdminToggleButton.isValid()));

		if (employee.isAdmin())
			add(tglbtnAdminToggleButton);
	}

	private void itemStateChanged(boolean stat) {

		if (tglbtnAdminToggleButton.isSelected()) {
			toggleAdmin = true;
			tglbtnAdminToggleButton.setText("Admin mode: Sluk");
		} else {

			toggleAdmin = false;
			tglbtnAdminToggleButton.setText("Admin Mode: Tænd");
		}
		fillTable();
	}

	private void init() {
		scheduleTable = new JTable();
		workdayController = new WorkdayController();
		days = new Vector<Vector>();
		scheduleController = new ScheduleController();
		fillTable();
	}

	private void fillTable() {
		listOfDates = workdayController.getDaysOfWeek(weekChange);
		shiftListTableModel = new ShiftListTableModel();
		shiftListTableModel.setCol(listOfDates);
		scheduleTable.setModel(shiftListTableModel);
		
		days.clear();

		days = scheduleController.rowData(listOfDates);

		for (int i = 0; i < listOfDates.size(); i++) {
			String dates = listOfDates.get(i);

			scheduleTable.getColumn(dates)
					.setCellRenderer(new SchedulerRenderer(scheduleTable, days, toggleAdmin, dates, employee, monitor));
			scheduleTable.getColumn(dates)
					.setCellEditor(new SchedulerRenderer(scheduleTable, days, toggleAdmin, dates, employee, monitor));
		}
		shiftListTableModel.setData(days);
		scheduleTable.repaint();
		scheduleTable.revalidate();

		scheduleTable.setRowSelectionAllowed(false);
		scheduleTable.setRowHeight(75);
	}

	private void changeWeek(int i) {
		weekChange = i;
		fillTable();
	}

	@Override
	public void run() {
		while (true) {
			monitor.awaitChange();
			fillTable();
		}

	}
}
