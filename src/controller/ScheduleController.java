package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

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
 *		This is the controller for Schedule  
 */
public class ScheduleController {
	private WorkdayController workdayController;
	private ShiftController shiftController;

	public ScheduleController() {
		shiftController = new ShiftController();
		workdayController = new WorkdayController();

	}

	public ScheduleController(WorkdayController workdayController, ShiftController shiftController) {
		this.workdayController = workdayController;
		this.shiftController = shiftController;
	}

	/**
	 *  This method returns the number of shifts in the schedule
	 * 
	 * @param workdayList
	 * @return max
	 */
	public int maxNumberOfShifts(List<Workday> workdayList) {
		int max = 1;

		for (Workday workday : workdayList) {
			if (workday != null)
				if (workday.getShiftList().size() > max)
					max = workday.getShiftList().size();
		}

		return max;
	}

	/**
	 * This method is used to create a vector of vector
	 * 
	 * @param dates
	 * @return shiftToList<vector>
	 */
	public Vector<Vector> rowData(List<String> dates) {
		Vector<Vector> shiftToList = new Vector<Vector>();
		Workday currDay;
		int maxNumberOfShifts = 0;
		List<Workday> workdayList = workdayController.getListOfWorkdaysFromRange(dates.get(0),
				dates.get(dates.size() - 1));

		List<Workday> scheduleList = new ArrayList<Workday>();
		Vector<Shift> shiftList = new Vector<Shift>();
		// Create a list of workdays, null is used as a value if there is no workday for
		// the date
		if (workdayList != null && workdayList.size() != 0) {
			for (int i = 0; i < workdayList.size(); i++) {
				for (int k = 0; k < dates.size(); k++) {
					if (i < workdayList.size()
							&& Objects.equals(workdayList.get(i).getDate().toString(), dates.get(k))) {
						scheduleList.add(workdayList.get(i));
						i++;
					} else {
						scheduleList.add(null);
					}
				}
			}
		}
		else
			for(int i = 0 ; i < 7 ; i++)
				scheduleList.add(null);

		// Have to find the max number of shifts for a day here
		maxNumberOfShifts = maxNumberOfShifts(scheduleList);

		// Add the shifts of the workdays to a list + all null values if there are not
		// enough shifts to fit the mold
		for (int i = 0; i < maxNumberOfShifts; i++) {
			shiftList = new Vector<Shift>();
			for (Workday workday : scheduleList) {
				if (workday != null) {
					if (workday.getShiftList().size() <= i)
						shiftList.add(null);
					else
						shiftList.add(workday.getShiftList().get(i));
				} else
					shiftList.add(null);
			}
			shiftToList.add(shiftList);
		}

		return shiftToList;
	}

}
