package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.EmployeeController;
import model.Employee;

public class Login {

	private JFrame frame;
	private JTextField usernameField;
	private JLabel LogoLbl;
	private EmployeeController employeeController;
	JLabel validationLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login win = new Login();
					win.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		employeeController = new EmployeeController();

		frame = new JFrame();
		frame.setBounds(100, 100, 721, 438);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);

		LogoLbl = new JLabel();
		LogoLbl.setLocation(263, 11);
		LogoLbl.setSize(200, 200);
		Image img = null;
		try {
			img = ImageIO.read(new File("img/logo.jpg"));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 LogoLbl.setIcon(new ImageIcon(img));
		centerPanel.add(LogoLbl);

		validationLbl = new JLabel();
		validationLbl.setVerticalAlignment(SwingConstants.TOP);
		validationLbl.setHorizontalAlignment(SwingConstants.LEFT);
		validationLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		validationLbl.setForeground(Color.RED);
		validationLbl.setBounds(263, 257, 350, 36);
		centerPanel.add(validationLbl);

		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(e -> login());
		loginBtn.setBounds(263, 304, 200, 30);
		loginBtn.setForeground(Color.BLACK);
		loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		loginBtn.setBackground(Color.GREEN);
		centerPanel.add(loginBtn);

		usernameField = new JTextField();
		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					int i = Integer.parseInt(usernameField.getText());
					validationLbl.setText("");
				} catch (NumberFormatException e1) {
					validationLbl.setText("Der må kun benyttes tal.");
				}
			}
		});

		usernameField.setBounds(263, 222, 200, 30);
		usernameField.setColumns(10);
		centerPanel.add(usernameField);

		JLabel LoginNameLbl = new JLabel("Medarbejder ID:");
		LoginNameLbl.setBounds(125, 223, 128, 22);
		LoginNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		centerPanel.add(LoginNameLbl);

		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.WEST);

		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.EAST);

		JPanel panel_4 = new JPanel();
		frame.getContentPane().add(panel_4, BorderLayout.SOUTH);

	}

	private void login() {
		if (usernameField.getText().isEmpty()) {
			validationLbl.setText("Feltet skal udfyldes");
		} else {
			int id = Integer.parseInt(usernameField.getText());
			Employee employee = employeeController.findEmployeeById(id);

			if (employee != null) {
				Main fra = new Main(employee);
				fra.setVisible(true);
				frame.dispose();
			} else {
				validationLbl.setText("Der findes ikke en bruger med dette medarbejder ID.");
			}
		}
	}
}
