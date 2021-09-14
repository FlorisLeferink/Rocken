/**
 * 
 */
package model;

/**
 * @author Thomas
 *
 */
public class ShiftRequest {
	private int id;
	private String note;
	private Employee employee;
	

	public ShiftRequest() {
	}

	/**
	 * @param id
	 * @param note
	 * @param employee
	 */
	public ShiftRequest(int id, String note, Employee employee) {
		this.setId(id);
		this.note = note;
		this.employee = employee;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



}
