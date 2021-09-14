/**
 * 
 */
package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Thomas
 *
 */
public class Workday {
	private int id;
	private LocalDate date;
	private String note;
	private boolean published;
	private ArrayList<Shift> listShift;
	
	public Workday() {
		listShift = new ArrayList<Shift>();
	}

	/**
	 * @param id
	 * @param date
	 * @param note
	 * @param published
	 */
	public Workday(int id, LocalDate date, String note, boolean published) {
		super();
		listShift = new ArrayList<Shift>();
		this.id = id;
		this.date = date;
		this.note = note;
		this.published = published;
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
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
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
	 * @return the published
	 */
	public boolean isPublished() {
		return published;
	}

	/**
	 * @param published the published to set
	 */
	public void setPublished(boolean published) {
		this.published = published;
	}
	public void addShift(Shift shift) {
		listShift.add(shift);
	}
	public ArrayList<Shift> getShiftList(){
		return listShift;
	}
}
