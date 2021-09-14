package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import controller.DataAccessException;
import controller.EmployeeController;
import controller.RoleController;
import model.Employee;
import model.Level;
import model.Role;

public class AdminGui extends JPanel implements Runnable{

	private JTextField textId;
	private JTextField textFname;
	private JTextField textLname;
	private JTextField textEmail;
	private JTextField textPhoneNo;
	private JComboBox roleComboBox;
	private JComboBox levelComboBox;
	private EmployeeController employeeController;
	private RoleController roleController;
	private RoleComboBoxModel roleComboBoxModel;
	private LevelComboBoxModel levelComboBoxModel;
	private EmployeeComboBoxModel empComboBoxModel;
	private Employee currEmployee;
	private JCheckBox isAdminCheckBox;
	private JLabel employeeNoErrorLbl;
	private JLabel emailErrorLbl;
	private JLabel telephoneNoErrorLbl;
	private JLabel roleErrorLbl;
	private JLabel niveauErrorLbl;
	private JComboBox selectEmpComboBox;
	private JTextField textFieldEditEmp;
	private JTextField textFieldEditEmpName;
	private JTextField textFieldEditEmpLastName;
	private JTextField textFieldEditEmail;
	private JTextField textFieldEditPhone;
	private JCheckBox isAdminCheckBoxEdit;
	private JComboBox roleComboBoxEdit;
	private JComboBox levelComboBoxEdit;
	private JLabel updateEmployeeNoErrorLbl;
	private JLabel updateEmailErrorLbl;
	private JLabel updateTelephoneNoErrorLbl;
	private int tempid;
	private JTextField tftRole;
	private JTextField tftLevel;
	private Monitor monitor;

	/**
	 * Create the panel.
	 * @param monitor 
	 * 
	 * @throws DataAccessException
	 * 
	 */
	public AdminGui(Employee employee, Monitor monitor) {
		currEmployee = employee;
		setSize(1250, 750);
		getPreferredSize().setSize(100, 25);

		init();
		
		this.monitor = monitor;
		Thread thread = new Thread(this);
		thread.start();

		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 5, 5);
		setLayout(flowLayout);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		leftPanel.setPreferredSize(new Dimension(500, 350));
		leftPanel.setVisible(true);
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		add(leftPanel);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_22 = (FlowLayout) panel_2.getLayout();
		flowLayout_22.setVgap(0);
		flowLayout_22.setHgap(10);
		leftPanel.add(panel_2);

		JLabel createEmpLbl = new JLabel("Opret Medarbejder:");
		panel_2.add(createEmpLbl);
		createEmpLbl.setPreferredSize(new Dimension(400, 30));
		createEmpLbl.setFont(new Font("Tahoma", Font.BOLD, 14));

		JPanel idPanel = new JPanel();
		leftPanel.add(idPanel);

		JPanel panelLapelId = new JPanel();
		FlowLayout flowlayout_id = (FlowLayout) panelLapelId.getLayout();
		flowlayout_id.setVgap(3);
		flowlayout_id.setAlignment(FlowLayout.LEFT);
		panelLapelId.setPreferredSize(new Dimension(100, 25));
		idPanel.add(panelLapelId);

		JLabel lblId = new JLabel("Medarbejder nr:");
		panelLapelId.add(lblId);

		textId = new JTextField();
		idPanel.add(textId);
		textId.setColumns(20);

		employeeNoErrorLbl = new JLabel("Medarbejder nummer findes allerede");
		employeeNoErrorLbl.setForeground(Color.RED);
		employeeNoErrorLbl.setVisible(false);
		leftPanel.add(employeeNoErrorLbl);

		JPanel fnamePanel = new JPanel();
		leftPanel.add(fnamePanel);

		JPanel panelLabelFname = new JPanel();
		FlowLayout flowLayout_fname = (FlowLayout) panelLabelFname.getLayout();
		flowLayout_fname.setVgap(3);
		flowLayout_fname.setAlignment(FlowLayout.LEFT);
		panelLabelFname.setPreferredSize(new Dimension(100, 25));
		fnamePanel.add(panelLabelFname);

		JLabel lblfName = new JLabel("Fornavn:");
		panelLabelFname.add(lblfName);

		textFname = new JTextField();
		fnamePanel.add(textFname);
		textFname.setColumns(20);

		JPanel lnamePanel = new JPanel();
		leftPanel.add(lnamePanel);

		JPanel panelLabelLname = new JPanel();
		FlowLayout flowLayout_lname = (FlowLayout) panelLabelLname.getLayout();
		flowLayout_lname.setVgap(3);
		flowLayout_lname.setAlignment(FlowLayout.LEFT);
		panelLabelLname.setPreferredSize(new Dimension(100, 25));
		lnamePanel.add(panelLabelLname);

		JLabel lblLname = new JLabel("Efternavn:");
		panelLabelLname.add(lblLname);

		textLname = new JTextField();
		lnamePanel.add(textLname);
		textLname.setColumns(20);

		JPanel emailPanel = new JPanel();
		leftPanel.add(emailPanel);

		JPanel panelLabelEmail = new JPanel();
		FlowLayout flowLayout_email = (FlowLayout) panelLabelEmail.getLayout();
		flowLayout_email.setVgap(3);
		flowLayout_email.setAlignment(FlowLayout.LEFT);
		panelLabelEmail.setPreferredSize(new Dimension(100, 25));
		emailPanel.add(panelLabelEmail);

		JLabel lblEmail = new JLabel("Email:");
		panelLabelEmail.add(lblEmail);

		textEmail = new JTextField();
		emailPanel.add(textEmail);
		textEmail.setColumns(20);

		emailErrorLbl = new JLabel("Indtast gyldig email");
		emailErrorLbl.setForeground(Color.RED);
		emailErrorLbl.setVisible(false);
		leftPanel.add(emailErrorLbl);

		JPanel phoneNoPanel = new JPanel();
		leftPanel.add(phoneNoPanel);

		JPanel panelLabelPhoneNo = new JPanel();
		FlowLayout flowLayout_phoneNo = (FlowLayout) panelLabelPhoneNo.getLayout();
		flowLayout_phoneNo.setVgap(3);
		flowLayout_phoneNo.setAlignment(FlowLayout.LEFT);
		panelLabelPhoneNo.setPreferredSize(new Dimension(100, 25));
		phoneNoPanel.add(panelLabelPhoneNo);

		JLabel lblPhoneNo = new JLabel("Mobil Nr:");
		panelLabelPhoneNo.add(lblPhoneNo);

		textPhoneNo = new JTextField();
		phoneNoPanel.add(textPhoneNo);
		textPhoneNo.setColumns(20);

		telephoneNoErrorLbl = new JLabel("Indtast gyldigt mobilnummer");
		telephoneNoErrorLbl.setForeground(Color.RED);
		telephoneNoErrorLbl.setVisible(false);
		leftPanel.add(telephoneNoErrorLbl);

		JPanel rolePanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) rolePanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		rolePanel.setPreferredSize(new Dimension(300, 30));
		leftPanel.add(rolePanel);

		JPanel panelLabelRole = new JPanel();
		FlowLayout flowLayout_role = (FlowLayout) panelLabelRole.getLayout();
		flowLayout_role.setVgap(3);
		flowLayout_role.setAlignment(FlowLayout.LEFT);
		panelLabelRole.setPreferredSize(new Dimension(100, 25));
		rolePanel.add(panelLabelRole);

		JLabel lblRole = new JLabel("Job Stilling:");
		panelLabelRole.add(lblRole);

		roleComboBox = new JComboBox(roleComboBoxModel);
		rolePanel.add(roleComboBox);

		
		JPanel levelPanel = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) levelPanel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		FlowLayout flowLayout_level = (FlowLayout) rolePanel.getLayout();
		flowLayout_level.setAlignment(FlowLayout.LEFT);

		roleErrorLbl = new JLabel("V\u00E6lg en rolle");
		roleErrorLbl.setForeground(Color.RED);
		roleErrorLbl.setVisible(false);
		leftPanel.add(roleErrorLbl);
		levelPanel.setPreferredSize(new Dimension(300, 30));
		leftPanel.add(levelPanel);

		JPanel panelLabelLevel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panelLabelLevel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		FlowLayout flowLayoutlevel = (FlowLayout) panelLabelRole.getLayout();
		flowLayoutlevel.setVgap(3);
		flowLayoutlevel.setAlignment(FlowLayout.LEFT);
		panelLabelLevel.setPreferredSize(new Dimension(100, 25));
		levelPanel.add(panelLabelLevel);

		JLabel lblLevel = new JLabel("Niveau:");
		panelLabelLevel.add(lblLevel);

		levelComboBox = new JComboBox(levelComboBoxModel);
		levelPanel.add(levelComboBox);

		niveauErrorLbl = new JLabel("V\u00E6lg et niveau");
		niveauErrorLbl.setForeground(Color.RED);
		niveauErrorLbl.setVisible(false);
		leftPanel.add(niveauErrorLbl);

		JPanel levelPanel_1 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) levelPanel_1.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		levelPanel_1.setPreferredSize(new Dimension(300, 30));
		leftPanel.add(levelPanel_1);

		JPanel isAdminLabel = new JPanel();
		FlowLayout fl_isAdminLabel = (FlowLayout) isAdminLabel.getLayout();
		fl_isAdminLabel.setAlignment(FlowLayout.LEFT);
		isAdminLabel.setPreferredSize(new Dimension(100, 25));
		levelPanel_1.add(isAdminLabel);

		JLabel isAdminLbl = new JLabel("Admin");
		isAdminLabel.add(isAdminLbl);

		isAdminCheckBox = new JCheckBox("");
		levelPanel_1.add(isAdminCheckBox);

		JPanel bottonPanel = new JPanel();
		bottonPanel.setPreferredSize(new Dimension(1000, 100));
		bottonPanel.setVisible(true);

		JPanel rightPanel = new JPanel();
		rightPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout_5 = (FlowLayout) rightPanel.getLayout();
		flowLayout_5.setVgap(0);
		flowLayout_5.setHgap(0);
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		rightPanel.setPreferredSize(new Dimension(400, 350));
		add(rightPanel);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_21 = (FlowLayout) panel_1.getLayout();
		flowLayout_21.setVgap(0);
		flowLayout_21.setHgap(10);
		rightPanel.add(panel_1);

		JLabel editEmpLbl = new JLabel("Rediger Medarbejder:");
		panel_1.add(editEmpLbl);
		editEmpLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		editEmpLbl.setPreferredSize(new Dimension(400, 30));

		JPanel selectEmpEditPanel = new JPanel();
		FlowLayout flowLayout_19 = (FlowLayout) selectEmpEditPanel.getLayout();
		flowLayout_19.setAlignment(FlowLayout.LEFT);
		selectEmpEditPanel.setPreferredSize(new Dimension(290, 30));
		rightPanel.add(selectEmpEditPanel);

		JPanel selectEmpEdit = new JPanel();
		FlowLayout flowLayout_20 = (FlowLayout) selectEmpEdit.getLayout();
		flowLayout_20.setAlignment(FlowLayout.LEFT);
		selectEmpEdit.setPreferredSize(new Dimension(110, 25));
		selectEmpEditPanel.add(selectEmpEdit);

		JLabel selectEmpEditLbl = new JLabel("V\u00E6lg Medarbejder:");
		selectEmpEdit.add(selectEmpEditLbl);

		selectEmpComboBox = new JComboBox(empComboBoxModel); // change to emp list here
		selectEmpComboBox.setPrototypeDisplayValue("XXXXXXXXXXXXXXX"); // to change width of combobox
		selectEmpEditPanel.add(selectEmpComboBox);
		selectEmpComboBox.addActionListener(e -> populateUpdateEmp());

		JPanel idPanelEdit = new JPanel();
		FlowLayout flowLayout_10 = (FlowLayout) idPanelEdit.getLayout();
		rightPanel.add(idPanelEdit);

		JPanel panelLapelId_1 = new JPanel();
		FlowLayout flowLayout_12 = (FlowLayout) panelLapelId_1.getLayout();
		flowLayout_12.setAlignment(FlowLayout.LEFT);
		panelLapelId_1.setPreferredSize(new Dimension(110, 25));
		idPanelEdit.add(panelLapelId_1);

		JLabel lblIdEdit = new JLabel("Medarbejder nr:");
		panelLapelId_1.add(lblIdEdit);

		textFieldEditEmp = new JTextField();
		textFieldEditEmp.setColumns(20);
		idPanelEdit.add(textFieldEditEmp);

		updateEmployeeNoErrorLbl = new JLabel("");
		updateEmployeeNoErrorLbl.setForeground(Color.RED);
		rightPanel.add(updateEmployeeNoErrorLbl);

		JPanel fnamePanelEdit = new JPanel();
		FlowLayout flowLayout_13 = (FlowLayout) fnamePanelEdit.getLayout();
		flowLayout_13.setAlignment(FlowLayout.LEFT);
		rightPanel.add(fnamePanelEdit);

		JPanel panelLabelFname_1 = new JPanel();
		FlowLayout flowLayout_14 = (FlowLayout) panelLabelFname_1.getLayout();
		flowLayout_14.setAlignment(FlowLayout.LEFT);
		panelLabelFname_1.setPreferredSize(new Dimension(110, 25));
		fnamePanelEdit.add(panelLabelFname_1);

		JLabel lblfNameEdit = new JLabel("Fornavn:");
		panelLabelFname_1.add(lblfNameEdit);

		textFieldEditEmpName = new JTextField();
		textFieldEditEmpName.setColumns(20);
		fnamePanelEdit.add(textFieldEditEmpName);

		JPanel lnamePanelEdit = new JPanel();
		rightPanel.add(lnamePanelEdit);

		JPanel panelLabelLname_1 = new JPanel();
		FlowLayout flowLayout_15 = (FlowLayout) panelLabelLname_1.getLayout();
		flowLayout_15.setAlignment(FlowLayout.LEFT);
		panelLabelLname_1.setPreferredSize(new Dimension(110, 25));
		lnamePanelEdit.add(panelLabelLname_1);

		JLabel lblLnameEdit = new JLabel("Efternavn:");
		panelLabelLname_1.add(lblLnameEdit);

		textFieldEditEmpLastName = new JTextField();
		textFieldEditEmpLastName.setColumns(20);
		lnamePanelEdit.add(textFieldEditEmpLastName);

		JPanel emailPanelEdit = new JPanel();
		rightPanel.add(emailPanelEdit);

		JPanel panelLabelEmail_1 = new JPanel();
		FlowLayout flowLayout_16 = (FlowLayout) panelLabelEmail_1.getLayout();
		flowLayout_16.setAlignment(FlowLayout.LEFT);
		panelLabelEmail_1.setPreferredSize(new Dimension(110, 25));
		emailPanelEdit.add(panelLabelEmail_1);

		JLabel lblEmailEdit = new JLabel("Email:");
		panelLabelEmail_1.add(lblEmailEdit);

		textFieldEditEmail = new JTextField();
		textFieldEditEmail.setColumns(20);
		emailPanelEdit.add(textFieldEditEmail);

		updateEmailErrorLbl = new JLabel("");
		updateEmailErrorLbl.setForeground(Color.RED);
		rightPanel.add(updateEmailErrorLbl);

		JPanel phoneNoPanelEdit = new JPanel();
		rightPanel.add(phoneNoPanelEdit);

		JPanel panelLabelPhoneNo_1 = new JPanel();
		FlowLayout flowLayout_11 = (FlowLayout) panelLabelPhoneNo_1.getLayout();
		flowLayout_11.setAlignment(FlowLayout.LEFT);
		panelLabelPhoneNo_1.setPreferredSize(new Dimension(110, 25));
		phoneNoPanelEdit.add(panelLabelPhoneNo_1);

		JLabel lblPhoneNoEdit = new JLabel("Mobil Nr:");
		panelLabelPhoneNo_1.add(lblPhoneNoEdit);

		textFieldEditPhone = new JTextField();
		textFieldEditPhone.setColumns(20);
		phoneNoPanelEdit.add(textFieldEditPhone);

		updateTelephoneNoErrorLbl = new JLabel("");
		updateTelephoneNoErrorLbl.setForeground(Color.RED);
		rightPanel.add(updateTelephoneNoErrorLbl);

		JPanel rolePanelEdit = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) rolePanelEdit.getLayout();
		flowLayout_7.setAlignment(FlowLayout.LEFT);
		rightPanel.add(rolePanelEdit);
		rolePanelEdit.setPreferredSize(new Dimension(290, 30));

		JPanel panelLabelRole_1 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panelLabelRole_1.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEFT);
		panelLabelRole_1.setPreferredSize(new Dimension(110, 25));
		rolePanelEdit.add(panelLabelRole_1);

		JLabel lblRoleEdit = new JLabel("Job Stilling:");
		panelLabelRole_1.add(lblRoleEdit);

		roleComboBoxEdit = new JComboBox(roleComboBoxModel);
		rolePanelEdit.add(roleComboBoxEdit);

		JPanel levelPanelEdit = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) levelPanelEdit.getLayout();
		flowLayout_8.setAlignment(FlowLayout.LEFT);
		rightPanel.add(levelPanelEdit);
		levelPanelEdit.setPreferredSize(new Dimension(290, 30));

		JPanel panelLabelLevel_1 = new JPanel();
		FlowLayout flowLayout_17 = (FlowLayout) panelLabelLevel_1.getLayout();
		flowLayout_17.setAlignment(FlowLayout.LEFT);
		panelLabelLevel_1.setPreferredSize(new Dimension(110, 25));
		levelPanelEdit.add(panelLabelLevel_1);

		JLabel lblLevelEdit = new JLabel("Niveau:");
		panelLabelLevel_1.add(lblLevelEdit);

		levelComboBoxEdit = new JComboBox(levelComboBoxModel);
		levelPanelEdit.add(levelComboBoxEdit);

		JPanel isAdminPanelEdit = new JPanel();
		FlowLayout flowLayout_9 = (FlowLayout) isAdminPanelEdit.getLayout();
		flowLayout_9.setAlignment(FlowLayout.LEFT);
		rightPanel.add(isAdminPanelEdit);
		isAdminPanelEdit.setPreferredSize(new Dimension(290, 30));

		JPanel isAdminLabel_1 = new JPanel();
		FlowLayout flowLayout_18 = (FlowLayout) isAdminLabel_1.getLayout();
		flowLayout_18.setAlignment(FlowLayout.LEFT);
		isAdminLabel_1.setPreferredSize(new Dimension(110, 25));
		isAdminPanelEdit.add(isAdminLabel_1);

		JLabel isAdminLblEdit = new JLabel("Admin");
		isAdminLabel_1.add(isAdminLblEdit);

		isAdminCheckBoxEdit = new JCheckBox("");
		isAdminPanelEdit.add(isAdminCheckBoxEdit);
		add(bottonPanel);
		bottonPanel.setLayout(new BorderLayout(0, 0));

		FlowLayout fl_bottonPanel = new FlowLayout(FlowLayout.LEFT, 0, 0);
		bottonPanel.setLayout(fl_bottonPanel);
		add(bottonPanel);

		JPanel bottomLeftButtonPanel = new JPanel();
		bottomLeftButtonPanel.setPreferredSize(new Dimension(550, 35));
		bottonPanel.add(bottomLeftButtonPanel, BorderLayout.SOUTH);
		FlowLayout fl_bottomLeftButtonPanel = (FlowLayout) bottomLeftButtonPanel.getLayout();
		fl_bottomLeftButtonPanel.setAlignment(FlowLayout.LEFT);

		JButton btnCreate = new JButton("Opret Medarbejder");
		bottomLeftButtonPanel.add(btnCreate);
		btnCreate.addActionListener(e -> createEmployee());

		btnCreate.setHorizontalAlignment(SwingConstants.LEFT);

		JPanel panel = new JPanel();
		bottonPanel.add(panel, BorderLayout.CENTER);

		JButton btnUpdateEmp = new JButton("Opdater medarbejder");
		panel.add(btnUpdateEmp);

		JPanel deleteBtnPanel = new JPanel();
		bottonPanel.add(deleteBtnPanel, BorderLayout.NORTH);

		JButton btnDeleteEmp = new JButton("Slet medarbejder");
		btnDeleteEmp.setBackground(Color.RED);
		btnDeleteEmp.setForeground(Color.BLACK);
		deleteBtnPanel.add(btnDeleteEmp);

		JPanel roleNewPanel = new JPanel();
		bottonPanel.add(roleNewPanel, BorderLayout.WEST);

		tftRole = new JTextField();
		roleNewPanel.add(tftRole);
		tftRole.setColumns(10);

		JButton btnNewRoleButton = new JButton("Ny rolle");
		btnNewRoleButton.addActionListener(e -> createNewRole());
		roleNewPanel.add(btnNewRoleButton);

		JPanel levelNewPanel = new JPanel();
		bottonPanel.add(levelNewPanel, BorderLayout.WEST);

		tftLevel = new JTextField();
		tftLevel.setColumns(10);
		levelNewPanel.add(tftLevel);

		JButton btnNewLevelButton = new JButton("Nyt niveau");
		btnNewLevelButton.addActionListener(e -> createNewLevel());

		levelNewPanel.add(btnNewLevelButton);

		btnUpdateEmp.addActionListener(e -> updateEmpoyee());
		btnDeleteEmp.addActionListener(e -> deleteEmployee());

	}

	private void populateUpdateEmp() {
		String fullname = (String) selectEmpComboBox.getSelectedItem();
		Employee employee = employeeController.findEmployeeByFirstAndLastName(fullname);

		tempid = employee.getEmployeeNo();
		textFieldEditEmp.setText(String.valueOf(employee.getEmployeeNo()));
		textFieldEditEmpName.setText(employee.getFname());
		textFieldEditEmpLastName.setText(employee.getLname());
		textFieldEditEmail.setText(employee.getEmail());
		textFieldEditPhone.setText(employee.getPhoneNo());
		roleComboBoxEdit.setSelectedItem(employee.getRole().getName());
		levelComboBoxEdit.setSelectedItem(employee.getLevel().getName());
		if (employee.isAdmin() == true) {
			isAdminCheckBoxEdit.setSelected(true);
		} else {
			isAdminCheckBoxEdit.setSelected(false);
		}
	}

	private void createNewRole() {
		roleController.CreateNewRole(tftRole.getText());
		monitor.performChange();
		monitor.changeMade();
		tftRole.setText("");
	}

	private void createNewLevel() {
		employeeController.CreateNewLevel(tftLevel.getText());
		monitor.performChange();
		monitor.changeMade();
		tftLevel.setText("");
	}

	private void deleteEmployee() {
		String empId = (String) textFieldEditEmp.getText();
		if (selectEmpComboBox.getSelectedItem() != null) {
			if (checkNumeric(empId)) {
				if (employeeController.findEmployeeById(Integer.parseInt(empId)) != null) {
					UIManager.put("OptionPane.minimumSize", new Dimension(300, 200));
					int res = JOptionPane.showConfirmDialog(null,
							"Er du sikker på du vil slette denne medarbejder?\n" + "MedarbejderID: "
									+ textFieldEditEmpName.getText() + "\n" + "Fornavn: "
									+ textFieldEditEmpName.getText() + "\n" + "Efternavn: "
									+ textFieldEditEmpName.getText() + "\n",
							"SLET MEDARBEJDER?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE); // 
					if (res == 0) {
						employeeController.DeleteEmployee(Integer.parseInt(empId));
						monitor.performChange();
						monitor.changeMade();
						JOptionPane.showMessageDialog(null, "Medarbejderen er blevet",
								"InfoBox: " + "Medarbejder slettet", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					updateEmployeeNoErrorLbl.setText("Ingen medarbejdere med dette ID");
				}
			} else {
				updateEmployeeNoErrorLbl.setText("Skal være et medarbejdernr");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Du skal vælge en medarbejder som du vil slette",
					"InfoBox: " + "Du skal vælge en medarbejder", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void updateEmpoyee() {
		updateEmployeeNoErrorLbl.setVisible(false);
		updateEmailErrorLbl.setVisible(false);
		updateTelephoneNoErrorLbl.setVisible(false);

		Role uRole = null;
		Level uLevel = null;
		boolean uerrorFound = false;

		String eUIdNo = textFieldEditEmp.getText();
		String eUFname = textFieldEditEmpName.getText();
		String eULname = textFieldEditEmpLastName.getText();
		String eUEmail = textFieldEditEmail.getText();
		String eUPhone = textFieldEditPhone.getText();

		boolean UisAdmin = isAdminCheckBoxEdit.isSelected();
		if (selectEmpComboBox.getSelectedItem() != null) {
			String fullname = (String) selectEmpComboBox.getSelectedItem(); // get name from combobox to search for
			Employee employee = employeeController.findEmployeeByFirstAndLastName(fullname);

			String uRoleStr = roleComboBoxEdit.getSelectedItem().toString();
			uRole = roleController.findRoleByName(uRoleStr); // finds role

			String uLevelStr = levelComboBoxEdit.getSelectedItem().toString();
			uLevel = employeeController.findLevelByName(uLevelStr); // finds level

			if (!checkValidPhoneNo(textPhoneNo.getText())) {
				updateTelephoneNoErrorLbl.setText("Indtast gyldigt mobilnummer");
				uerrorFound = true;
			}

			if (checkNumeric(eUIdNo)) {
				if (employeeController.findEmployeeById(Integer.parseInt(eUIdNo)) != null && employeeController
						.findEmployeeById(Integer.parseInt(eUIdNo)).getEmployeeNo() != employee.getEmployeeNo()) { // checks
																													// if
																													// ID
																													// isnt
																													// 0
																													// and
																													// if
																													// its
																													// not
																													// equal
																													// to
																													// current
																													// employee
																													// ID
																													// so
																													// you
																													// can
																													// update
																													// without
																													// getting
																													// error
																													// on
																													// ID
																													// already
																													// existing
					updateEmployeeNoErrorLbl.setText("Medarbejder nummer findes allerede");
					uerrorFound = true;
				}
			} else {
				updateEmployeeNoErrorLbl.setText("Brug et gyldigt medarbejdernummer");
				uerrorFound = true;
			}
			if (!uerrorFound) {
				employeeController.UpdateEmployee(Integer.parseInt(eUIdNo), eUFname, eULname, eUEmail, eUPhone, uRole,
						UisAdmin, uLevel, tempid);
				monitor.performChange();
				monitor.changeMade();
				JOptionPane.showMessageDialog(null, "Medarbejder opdateret", "InfoBox: " + "Medarbejder opdateret",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Du skal vælge en medarbejder som du vil opdatere",
					"InfoBox: " + "Du skal vælge en medarbejder", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void createEmployee() {
		employeeNoErrorLbl.setVisible(false);
		emailErrorLbl.setVisible(false);
		telephoneNoErrorLbl.setVisible(false);
		roleErrorLbl.setVisible(false);
		niveauErrorLbl.setVisible(false);

		Role eRole = null;
		Level eLevel = null;
		boolean errorFound = false;

		String eIdNo = textId.getText();
		String eFname = textFname.getText();
		String eLname = textLname.getText();
		String eEmail = textEmail.getText();
		String ePhone = textPhoneNo.getText();
		boolean isAdmin = isAdminCheckBox.isSelected();

		if (!checkValidPhoneNo(textPhoneNo.getText())) {
			telephoneNoErrorLbl.setVisible(true);
			errorFound = true;
		}

		if (roleComboBox.getSelectedItem() == null) {
			roleErrorLbl.setVisible(true);
			errorFound = true;
		} else {
			String roleStr = roleComboBox.getSelectedItem().toString();
			eRole = roleController.findRoleByName(roleStr);
		}

		if (levelComboBox.getSelectedItem() == null) {
			niveauErrorLbl.setVisible(true);
			errorFound = true;
		} else {
			String levelStr = levelComboBox.getSelectedItem().toString();
			eLevel = employeeController.findLevelByName(levelStr);
		}

		if (checkNumeric(eIdNo)) {
			if (employeeController.findEmployeeById(Integer.parseInt(eIdNo)) != null) {
				employeeNoErrorLbl.setText("Medarbejder nummer findes allerede");
				employeeNoErrorLbl.setVisible(true);
				errorFound = true;
			}
		} else {
			employeeNoErrorLbl.setText("Brug et gyldigt medarbejdernummer");
			employeeNoErrorLbl.setVisible(true);
			errorFound = true;
		}

		if (!errorFound) {
			employeeController.CreateNewEmployee(Integer.parseInt(eIdNo), eFname, eLname, eEmail, ePhone, eRole,
					isAdmin, eLevel);
			monitor.performChange();
			monitor.changeMade();
			JOptionPane.showMessageDialog(null, "Medarbejder oprettet succesfuld", "InfoBox: " + "Medarbejder oprettet",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void init() {
		employeeController = new EmployeeController();
		roleController = new RoleController();
		roleComboBoxModel = new RoleComboBoxModel(roleController.ListOfRole());
		levelComboBoxModel = new LevelComboBoxModel(employeeController.findAllLevels());
		empComboBoxModel = new EmployeeComboBoxModel(employeeController.findAllEmployees());
	}

	private boolean checkNumeric(String str) {
		if (str == null) {
			return false;
		}
		try {
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private boolean checkValidPhoneNo(String str) {
		if (str == null || str.trim().length() != 8) {
			return false;
		}
		try {
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private boolean checkValidEmail(String str) {
		return false;
	}
	
	private void updateComboBoxes() {
		roleComboBoxModel = new RoleComboBoxModel(roleController.ListOfRole());
		levelComboBoxModel = new LevelComboBoxModel(employeeController.findAllLevels());
		empComboBoxModel = new EmployeeComboBoxModel(employeeController.findAllEmployees());
		
		roleComboBox.setModel(roleComboBoxModel);
		roleComboBoxEdit.setModel(roleComboBoxModel);
		
		levelComboBox.setModel(levelComboBoxModel);
		levelComboBoxEdit.setModel(levelComboBoxModel); 
		selectEmpComboBox.setModel(empComboBoxModel);			
	}

	@Override
	public void run() {
		while(true) {
			monitor.awaitChange();
			updateComboBoxes();
		}
		
	}
}
