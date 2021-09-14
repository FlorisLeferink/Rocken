package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import database.ShiftDB;
import database.ShiftDBIF;
import model.Employee;
import model.Role;
import model.Shift;
import model.ShiftRequest;
import model.Workday;

/**
 * @author Brian Bundgaard Engelbrektsen
 * @author Thomas Henriksen
 * @author Simon Ørts Niese
 * @author Floris Ruben Leferink
 * @author Kasper Johan Knudsen
 */
/**
 *		This is the controller for Shift, responsible for create new shift, find shifts by id or date,
 *		update exiting Shift, return list of shifts, save shift in datebase, makes a check in the database
 *      for exiting shift.		 
 */
public class ShiftController {
	private ShiftDBIF shiftDB;
	
	/**
	 * This constructor makes a instance of a ShiftDB 
	 */
	public ShiftController() {
		try {
			shiftDB = new ShiftDB();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to return the last id form the database
	 * @return id
	 */
	public int getLastId() {
		int id = 0;
		try {
			id = shiftDB.getLastId();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * This method is used to find a shift by id. It will search the database for
	 * the shift and return the shift.
	 * @param id
	 * @return currShift
	 */
	public Shift findShiftById(int id) {
		Shift currShift = null;
		try {
			currShift = shiftDB.findById(id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return currShift;
	}
	
	/**
	 * This method is used for find a list of shifts by date. It will search the database for
	 * the shifts and return a list of shift.
	 * @param date
	 * @return shiftList
	 */ 
	public List<Shift> findShiftsByDate(LocalDate date) {
		WorkdayController workdayController = new WorkdayController();
		Workday worday =  workdayController.findWorkdayByDate(date);
		List<Shift> shiftList = null;
		if (worday != null) {
			try {
				shiftList = shiftDB.findByDate(worday);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		}
		return shiftList;
	}
	
	/**
	 * This method is used for find a list of all shifts. It will search the database for
	 * the shifts and return a list of shift.
	 * Not currently used.
	 * @return List<Shift> shiftList
	 */
	public List<Shift> findAllShifts() {
		List<Shift> shiftList = null;
		try {
			shiftList = shiftDB.findAll();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return shiftList;
	}
	
	/**
	 * This method is used to insert shifts into the database from a arrayList of shift 
	 * and with a work day.
	 * @param workday
	 * @param shiftList
	 */
	public void saveShiftToDatabasen(Workday workday, List<Shift> shiftList) {
		List<Shift> newShiftList = new ArrayList<Shift>();
		for (Shift shift : shiftList) {
			if (!checkExitingShift(workday, shift)) {
				newShiftList.add(shift);
			}
		}
		for (Shift shift : newShiftList) {
			try {
				shiftDB.insert(shift, workday);
			} catch (DataAccessException | SQLException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method is used to update a shift in the database.  
	 * @param shift
	 */
	public void updateShift(Shift shift) {
		try {
			shiftDB.update(shift);
		} catch (DataAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to create a new shift.
	 * @param startAt
	 * @param endAt
	 * @param note
	 * @param roleType
	 * @return newShift
	 */
	public Shift createNewShift(LocalTime startAt, LocalTime endAt, String note, Role roleType) {
		Shift newShift = null;
		try {
			newShift = new Shift(shiftDB.getLastId() + 1, startAt, endAt, note, roleType);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newShift;
	}
	/**
	 * This method is used to check if a shift already exiting in the database for 
	 * specific work day.
	 * @param workday
	 * @param shift
	 * @return exiting boolean
	 */
	private boolean checkExitingShift(Workday workday, Shift shift) {
		boolean exiting = false;
		LocalDate date = workday.getDate();
		List<Shift> listOfShifts = findShiftsByDate(date);
		int i = 0;
		int size = listOfShifts.size();
		// If the size of list of shifts for a work day is bigger than 0 and do not exits,
		// it compare the shift id with exiting shift id.
		while (i < size && !exiting) {
			Shift temp = listOfShifts.get(i);
			if (temp.getId() == shift.getId()) {
				exiting = true;
			}
			i++;
		}
		return exiting;
	}
	
	/**
	 * This method is used to return a list of shift request for a specific shift
	 * @param shift
	 * @return List<ShiftRequest> list of shift request
	 */
	public List<ShiftRequest> getListOfShiftRequest(Shift shift) {
		return shift.getListOfShiftRequest();
	}

	/** 
	 * This method is used to return a list of shift for a specific work day
	 * @param workday
	 * @return List<Shift> currWorkday List of shift
	 */
	public List<Shift> getWorkdayListOfShift(Workday workday) {
		List<Shift> currWorkday = null;
		// If the work day exists, it gets a list of shift
		if (workday != null) {
			currWorkday = workday.getShiftList();
		}
		return currWorkday;
	}

	/**
	 * This method is used to return a shift request for a specific employee to the 
	 * specific shift. 
	 * @param employee
	 * @param shift
	 * @return shiftRequest
	 */
	public ShiftRequest getEmployeesShiftRequestForShift(Employee employee, Shift shift) {
		ShiftRequest shiftRequest = null;
		// This for-each loop goes through a list of specific shift request.  
		for (ShiftRequest req : shift.getListOfShiftRequest()) {
		// This if statement compare the employee number from a shift request and a employee
			if (req.getEmployee().getEmployeeNo() == employee.getEmployeeNo()) {
				shiftRequest = req;
			}
		}
		return shiftRequest;
	}
}
