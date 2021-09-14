package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import controller.DataAccessException;
import model.Employee;

public class Main extends JFrame {
	private JPanel contentPane;

	/**
	 * Create the frame.
	 * 
	 * @throws DataAccessException
	 * 
	 */
	public Main(Employee employee) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 970, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane panes = new JTabbedPane();

		Monitor monitor = new Monitor();

		ScheduleGui schedule = new ScheduleGui(employee, monitor);
		panes.addTab("Skema", schedule);
		if (employee.isAdmin()) {
			AdminGui adminGui = new AdminGui(employee, monitor);
			panes.addTab("Medarbejder", adminGui);

		}
		contentPane.add(panes);
	}

}
