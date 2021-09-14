/**
 * 
 */
package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Bundgaard Engelbrektsen
 * @author Thomas Henriksen
 * @author Simon Ørts Niese
 * @author Floris Ruben Leferink
 * @author Kasper Johan Knudsen
 */

public class Shift {
	private int id;
	private LocalTime startAt;
	private LocalTime endAt;
	private String note;
	private Role role;
	private Employee employee;
	private List<ShiftRequest> listOfShiftRequest;

	public Shift() {
	}

	/**
	 * @param id
	 * @param startAt
	 * @param endAt
	 * @param note
	 * @param clockIn
	 * @param clockout
	 * @param locket
	 * @param roleType
	 */
	public Shift(int id, LocalTime startAt, LocalTime endAt, String note,  Role role) {
		super();
		this.id = id;
		this.startAt = startAt;
		this.endAt = endAt;
		this.note = note;
		this.role = role;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the startAt
	 */
	public LocalTime getStartAt() {
		return startAt;
	}

	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(LocalTime startAt) {
		this.startAt = startAt;
	}

	/**
	 * @return the endAt
	 */
	public LocalTime getEndAt() {
		return endAt;
	}

	/**
	 * @param endAt the endAt to set
	 */
	public void setEndAt(LocalTime endAt) {
		this.endAt = endAt;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the roleType
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	/**
	 * @return the listOfShiftRequest
	 */
	public List<ShiftRequest> getListOfShiftRequest() {
		return listOfShiftRequest;
	}
	
	public void setListOfShiftRequest(List<ShiftRequest> list) {
		this.listOfShiftRequest = list;
	}
	/**
	 * @param shiftRequest add to shift
	 */
	public void addShiftRequest(ShiftRequest shiftRequest) {
		listOfShiftRequest.add(shiftRequest);
	}

}
