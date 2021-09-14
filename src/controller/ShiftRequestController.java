package controller;

import java.sql.SQLException;
import java.util.List;

import database.ShiftRequestDB;
import database.ShiftRequestDBIF;
import model.Employee;
import model.Shift;
import model.ShiftRequest;

/**
 * @author Brian Bundgaard Engelbrektsen
 * @author Thomas Henriksen
 * @author Simon Ørts Niese
 * @author Floris Ruben Leferink
 * @author Kasper Johan Knudsen
*/

/**
 *      This Controller for shiftRequests is responsible for creating new shiftRequests, finding shiftRequests, updating shiftRequests and Deleting shiftRequests. 
 */
public class ShiftRequestController {
	private ShiftRequestDBIF shiftRequestDB;
	private ShiftController shiftController;

	/**
	 * This constructor makes a instance of a ShiftRequestDB and shiftController 
	 */
	public ShiftRequestController() {
		try {
			shiftRequestDB = new ShiftRequestDB();
			shiftController = new ShiftController();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to retun a shift request by for a specific employee by id
	 * @param id
	 * @return	shiftRquest
	 */
	public ShiftRequest findShiftRequestByRequestId(int id) {
		ShiftRequest currShiftRequest = null;
		try {
			currShiftRequest = shiftRequestDB.findById(id);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currShiftRequest;
	}
	
	/**
	 * This method is used to return a list of all shift requests
	 * @return list<ShiftRequest> currShiftRequest returns a list of all shift requests
	 */
	public List<ShiftRequest> findAllShiftRequests() {
		List<ShiftRequest> currShiftRequestList = null;
		try {
			currShiftRequestList = shiftRequestDB.findAll();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currShiftRequestList;
	}
	
	/**
	 * This method is used to return shift requests made for a specific shift
	 * @param shift
	 * @return shift
	 */
	public List<ShiftRequest> findShiftRequestsByShiftId(Shift shift) {
		return shiftController.getListOfShiftRequest(shift);
	}
	
	/**
	 * This method is used to create a new shift request from an employee
	 * @param note
	 * @param shift
	 * @param employee
	 */
	public void createNewShiftRequest(String note, Shift shift, Employee employee) {
		try {
			ShiftRequest newShiftRequest = new ShiftRequest(shiftRequestDB.getLastId() + 1, note, employee);
			shiftRequestDB.insert(newShiftRequest, shift);
		} catch (DataAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to remove shift requests by its id
	 * @param id
	 */
	public void removeShiftReqestByID(int id) {
		try {
			shiftRequestDB.removeById(id);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *  This method is used to get the employees shift request for the shift
	 * @param employee
	 * @param shift
	 * @return shiftRequest
	 */
	public ShiftRequest getEmployeesShiftRequestForShift(Employee employee, Shift shift) {
		ShiftRequest shiftRequest = null;
		//goes through the shiftRequests
		for (ShiftRequest req : shift.getListOfShiftRequest()) {
			// checks if the employeeNo in the shiftRequest matches the employeeNo 
			if (req.getEmployee().getEmployeeNo() == employee.getEmployeeNo()) {
				shiftRequest = req;
			}
		}
		return shiftRequest;
	}
}
