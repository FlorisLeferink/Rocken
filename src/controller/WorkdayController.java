package controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import database.WorkdayDB;
import database.WorkdayDBIF;
import model.Shift;
import model.Workday;

/**
 * @author Brian Bundgaard Engelbrektsen
 * @author Thomas Henriksen
 * @author Simon Ørts Niese
 * @author Floris Ruben Leferink
 * @author Kasper Johan Knudsen
 */

/**
 *      This is the WorkDayController, it is responsible for finding workdays by date, id and all workdays. Creating workdays.
 *      Telling shiftCotroller to create new shift, checking workdays, and finding shifts on a workday. 
 */

public class WorkdayController {

	private WorkdayDBIF workdayDB;
	private Workday currWorkday;

	private ShiftController shiftController;
	private RoleController roleController;

	/**
	 * This constructor instantiates shift and roleController and makes a new instance of WorkdayDB
	 */
	public WorkdayController() {
		shiftController = new ShiftController();
		roleController = new RoleController();
		try {
			workdayDB = new WorkdayDB();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to return workday by id from database
	 * @param id
	 * @return currWorkday
	 */
	public Workday findWorkdayById(int id) {
		Workday currWorkday = null;
		try {
			currWorkday = workdayDB.findById(id);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currWorkday;
	}

	/**
	 * This method is used to return a workday by date from database
	 * @param date
	 * @return currWorkday
	 */
	public Workday findWorkdayByDate(LocalDate date) {
		Workday currWorkday = null;
		try {
			currWorkday = workdayDB.findByDate(date);
			if (currWorkday == null) {
				currWorkday = createNewWorkday(date, "", false);
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currWorkday;
	}

	/**
	 * This method is used to find all workdays from database
	 * @return list<Workday> returns a list of all workdays
	 */
	public List<Workday> findAllWorkdays() {
		List<Workday> currWorkdayList = null;
		try {
			currWorkdayList = workdayDB.findAll();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currWorkdayList;
	}

	/**
	 * This method is used to insert a workday to the database if it doesnt exist, if it does exist it uses 
	 * the one currently existing
	 * @return work boolean
	 */
	public boolean saveWorkday() {
		boolean work = false;
		Workday CreateNewWorkday = null;
		//checks if there exists a workday on the date
		if (!CheckWorkday(currWorkday.getDate())) {
			try {
				CreateNewWorkday = workdayDB.insert(currWorkday);
				
				work = true;
			} catch (SQLException | DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			CreateNewWorkday = currWorkday;
			work = true;
		}
		shiftController.saveShiftToDatabasen(CreateNewWorkday, currWorkday.getShiftList());
		currWorkday = null;
		return work;
	}

	/**
	 * This method is used to set workday by date 
	 * @param date
	 */
	public void setCurrWorkday(LocalDate date) {
		currWorkday = findWorkdayByDate(date);
	}

	/**
	 * This method is used to tell shiftController to create a new shift, and then adding that shift to the workday
	 * @param startAt
	 * @param endAt
	 * @param note
	 * @param roleId
	 */
	public void createNewShift(LocalTime startAt, LocalTime endAt, String note, int roleId) {
		Shift shift = shiftController.createNewShift(startAt, endAt, note, roleController.findRoleById(roleId));
		getShiftForWorkday().add(shift);
	}

	/**
	 * This method is used to return all the shift for the workday
	 * @return list<Shift> currShiftList returns a list of shifts on the workday
	 */
	public List<Shift> getShiftForWorkday() {
		List<Shift> currShiftList = null;
		if (currWorkday != null) {

			currShiftList = currWorkday.getShiftList();
		}
		return currShiftList;
	}

	/**
	 * This method is used to create a new workday and check if it already exists, if it exists
	 * it finds the one that already exists instead.
	 * @param date
	 * @param note
	 * @param published
	 * @return
	 */
	public Workday createNewWorkday(LocalDate date, String note, boolean published) {
		boolean exiting = false;
		exiting = CheckWorkday(date);
		Workday workday = null;
		//checks if the workday already exists
		if (!exiting) {

			workday = new Workday(getlastWorkdayId() + 1, date, note, published);

		} else {
			workday = findWorkdayByDate(date);
		}
		return workday;
	}

	/**
	 * This method is used to return the id out from the last object added to database.
	 * @return currWorkday
	 */
	public int getlastWorkdayId() {
		int currWorkday = 0;
		try {
			currWorkday = workdayDB.getLastId();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currWorkday;
	}

	/**
	 * This method is used to check if the workday already exists exists
	 * @param date
	 * @return exiting
	 */
	private boolean CheckWorkday(LocalDate date) {
		boolean exiting = false;
		List<Workday> listWorkDays = findAllWorkdays();
		int i = 0;
		int size = listWorkDays.size();
		//goes through all elements
		while (i < size && !exiting) {
			Workday temp = listWorkDays.get(i);
			// checks if the a workday exists on the date
			if (temp.getDate().equals(date)) {
				exiting = true;
			}
			i++;
		}
		return exiting;
	}

	/**
	 * This method is used to return list of days and dates of the current week and always starts on mondays
	 * @param weeks
	 * @return list<String> days list of days in the week
	 */
	public List<String> getDaysOfWeek(int weeks) {
		List<String> days = new ArrayList<String>();
		Calendar c = Calendar.getInstance(Locale.FRANCE);

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int day = Calendar.getInstance().get(Calendar.DATE);

		c.set(year, month, day);
		c.getTime();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.add(Calendar.DATE, weeks * 7);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
		for (int i = 0; i < 7; i++) {
			days.add(df.format(c.getTime()));
			c.add(Calendar.DAY_OF_MONTH, 1);

		}
		return days;
	}
	
	/**
	 * This method is used to return a list of workdays between a start date and end date,
	 * always return for a week starting monday and ending sunday
	 * @param beginDate
	 * @param endDate
	 * @return list<Workday> returns a list of workdays between the dates
	 */
	public List<Workday> getListOfWorkdaysFromRange(String beginDate, String endDate){
		List<Workday> workdayList = null;
		
		try {
			workdayList = workdayDB.findWorkdayRange(beginDate, endDate);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return workdayList;
	}

}
