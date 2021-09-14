package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.EmployeeDB;
import database.EmployeeDBIF;
import database.LevelDB;
import database.LevelDBIF;
import model.Employee;
import model.Level;
import model.Role;

/**
 * @author Brian Bundgaard Engelbrektsen
 * @author Thomas Henriksen
 * @author Simon Ørts Niese
 * @author Floris Ruben Leferink
 * @author Kasper Johan Knudsen
 */

 /**         
  * This is the controller for employee. In is controller is methods to
  * create new employee, find them again, getting list of employees and
  * it can find shiftRequests
  */
public class EmployeeController {
	private EmployeeDBIF employeeDB;
	private LevelDBIF levelDB;

	/**
	 * This constructor makes a instance of a EmployeeDB and LevelDB 
	 */
	public EmployeeController() {
		try {
			employeeDB = new EmployeeDB();
			levelDB = new LevelDB();

		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to find a employee by id. it will seach the database for
	 * the employee and return the employee
	 * 
	 * @param int ID
	 * @return Employee
	 */
	public Employee findEmployeeById(int id) {
		Employee emp = null;
		try {
			emp = employeeDB.findByEmployeeNo(id);
		} catch (DataAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}

	/**
	 * This method is used to find a employee by fullname. it will seach the
	 * database for the employee and return the employee
	 * 
	 * @param String fullname
	 * @return Employee
	 */
	public Employee findEmployeeByFirstAndLastName(String fullname) {
		Employee emp = null;
		try {
			emp = employeeDB.findEmployeeByFirstAndLastName(fullname);
		} catch (DataAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}

	/**
	 * This method is used to create a new employee by taking employeeNo, firstname,
	 * lastname, email, phone, role, isAdmin, level. it will create and save it to
	 * the database
	 * 
	 * @param int     employeeNo
	 * @param String  firstname
	 * @param String  lastname
	 * @param String  email
	 * @param String  phoneNo
	 * @param Role    role
	 * @param boolean isAdmin
	 * @param Level   level
	 * 
	 * @return boolean
	 */
	public boolean CreateNewEmployee(int employeeNo, String fname, String lname, String email, String phoneNo,
			Role role, boolean isAdmin, Level level) {
		boolean check = false;

		check = checkListOfEmployees(fname, lname, phoneNo);

		if (!check) {
			Employee employee = new Employee(employeeNo, fname, lname, email, phoneNo, role, isAdmin, level);
			try {
				employeeDB.insert(employee);
			} catch (DataAccessException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return check;
	}

	/**
	 * This method is used to updates a employee by taking employeeNo, firstname,
	 * lastname, email, phone, role, isAdmin, level, tempId. it will update and save
	 * it to the database
	 * 
	 * @param int     employeeNo
	 * @param String  firstname
	 * @param String  lastname
	 * @param String  email
	 * @param String  phoneNo
	 * @param Role    role
	 * @param boolean isAdmin
	 * @param Level   level
	 * @param int     tempId
	 * 
	 * @return boolean
	 */
	public boolean UpdateEmployee(int employeeNo, String fname, String lname, String email, String phoneNo,
			Role roleType, boolean isAdmin, Level level, int tempId) {
		boolean check = false;
		check = checkListOfEmployees(fname, lname, phoneNo);
		if (!check) {

			Employee employee = new Employee(employeeNo, fname, lname, email, phoneNo, roleType, isAdmin, level);
			try {
				employeeDB.update(employee, tempId);
			} catch (DataAccessException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return check;
	}

	/**
	 * This method is used to delete a employee by taking employeeNo it will delete
	 * a employee in the database
	 * 
	 * @param int employeeNo
	 * 
	 * @return boolean
	 */
	public void DeleteEmployee(int employeeNo) {
		try {
			employeeDB.deleteEmployeeById(employeeNo);
		} catch (DataAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to find all employees in the database
	 * 
	 * @return List<Employee> employeeList
	 */
	public List<Employee> findAllEmployees() {
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			employeeList = employeeDB.findAll();
		} catch (DataAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeeList;
	}

	/**
	 * This method is used to create a level by taking name it to the database
	 * 
	 * @param String name
	 * 
	 * @return boolean
	 */
	public boolean CreateNewLevel(String name) {
		boolean check = false;
		try {
			check = checkListOfLevels(name);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!check) {
			Level level = new Level(findAllLevels().size() + 1, name);
			try {
				levelDB.insert(level);
			} catch (DataAccessException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return check;
	}

	/**
	 * This method is used to find a level by its id in the database
	 * 
	 * @param int id
	 * @return level
	 */
	public Level findLevelById(int id) {
		Level currLevel = null;
		try {
			currLevel = levelDB.findById(id);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currLevel;
	}

	/**
	 * This method is used to find a level by its name in the database
	 * 
	 * @param String name
	 * @return level
	 */
	public Level findLevelByName(String name) {
		Level currLevel = null;
		try {
			currLevel = levelDB.findByName(name);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currLevel;
	}

	/**
	 * This method is used to find all levels in the database
	 * 
	 * @return List<Level> currLevelList
	 */
	public List<Level> findAllLevels() {
		List<Level> currLevelList = null;
		try {
			return currLevelList = levelDB.findAll();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currLevelList;
	}

	/**
	 * This method is used to check in the database if there are a level with the
	 * same name and if there are a level by same and return true if there are a
	 * level by same name else will it return false
	 * 
	 * @param String name
	 * @return boolean
	 */
	private boolean checkListOfLevels(String name) throws DataAccessException {
		boolean exiting = false;
		List<Level> listOfLevels = findAllLevels();
		int i = 0;
		int size = listOfLevels.size();
		while (i < size && !exiting) {
			Level temp = listOfLevels.get(i);
			if (temp.getName().equals(name)) {
				exiting = true;
			}
			i++;
		}
		return exiting;
	}
	/**
	 * This method is used to check in the database if there are a Employee with the
	 * same firstname, lastname and phoneNo and if there are a employee by same name and phone number and return true if there are a
	 * employee by same name and phone number else will it return false
	 * 
	 * @param String fname
	 * @param String lname
	 * @param String phoneNo
	 * @return boolean
	 */
	private boolean checkListOfEmployees(String fname, String lname, String phoneNo) {
		boolean exiting = false;
		List<Employee> listOfEmployees = findAllEmployees();
		int i = 0;
		int size = listOfEmployees.size();
		while (i < size && !exiting) {
			Employee temp = listOfEmployees.get(i);
			if (temp.getFname().equals(fname) && temp.getFname().equals(lname) && temp.getPhoneNo().equals(phoneNo)) {
				exiting = true;
			}
			i++;
		}
		return exiting;
	}
}
