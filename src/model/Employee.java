/**
 * 
 */
package model;

/**
 * @author Thomas
 *
 */
public class Employee {
	private int employeeNo;
	private String fname;
	private String lname;
	private String email;
	private String phoneNo;
	private Role role;
	private boolean isAdmin;
	private Level level;

	public Employee() {

	}

	/**
	 * @param id
	 * @param fname
	 * @param lname
	 * @param email
	 * @param phoneNo
	 * @param roleType
	 * @param isAdmin
	 * @param level
	 * @param tempid 
	 */
	public Employee(int EmployeeNo, String fname, String lname, String email, String phoneNo, Role role,
			boolean isAdmin, Level level) {
		super();
		this.employeeNo = EmployeeNo;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.phoneNo = phoneNo;
		this.role = role;
		this.isAdmin = isAdmin;
		this.level = level;
	}

	/**
	 * @return the employeeNo
	 */
	public int getEmployeeNo() {
		return employeeNo;
	}

	/**
	 * @param employeeNo the employeeNo to set
	 */
	public void setEmployeeNo(int employeeNo) {
		this.employeeNo = employeeNo;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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
		this.role  = role;
	}

	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

}
