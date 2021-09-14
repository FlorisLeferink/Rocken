package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import controller.DataAccessException;
import model.Employee;
import model.Level;
import model.Role;
import model.Workday;

/**
 * @author Brian Bundgaard Engelbrektsen
 * @author Thomas Henriksen
 * @author Simon Ørts Niese
 * @author Floris Ruben Leferink
 * @author Kasper Johan Knudsen
 */

public class EmployeeDB implements EmployeeDBIF {
	private DBConnection con;
	private RoleDBIF roleTypeDB;
	private LevelDBIF levelDB;

	private static final String FIND_ALL_Q = "select employeeNo, fname, lname, email, phoneNo, isAdmin, role, level from Employee";
	private PreparedStatement findAllPS;

	private static final String FIND_BY_EmployeeNo_Q = FIND_ALL_Q + " where employeeNo = ?";
	private PreparedStatement findByEmployeeNoPS;

	private static final String FIND_BY_NAME_Q = FIND_ALL_Q + " where name = ?";
	private PreparedStatement findByNamePS;
	
	private static final String FIND_BY_FULL_NAME_Q = FIND_ALL_Q + " where fname + ' ' + lname = ?";
	private PreparedStatement findByFullNamePS;

	private static final String INSERT_Q = "insert into Employee (employeeNo, fname, lname, email, phoneNo, isAdmin, role, level) values (?, ?,?,?,?,?,?,?)";
	private PreparedStatement insertPS;
	
	private static final String UPDATE_Q =  "UPDATE Employee SET employeeNo = ?, fname = ?, lname = ?, email = ?, phoneNo = ?, isAdmin = ?, role = ?, level = ?  where employeeNo = ?";
	private PreparedStatement updatePS;
	
	private static final String DELETE_Q = "DELETE FROM Employee WHERE employeeNo = ?";
	private PreparedStatement deletePS;

	public EmployeeDB(RoleDBIF RoleTypeDB, LevelDBIF LevelDB) throws DataAccessException {
		this.roleTypeDB = RoleTypeDB;
		this.levelDB = LevelDB;
		init();
	}

	public EmployeeDB() throws DataAccessException {
		roleTypeDB = new RoleDB();
		levelDB = new LevelDB();
		init();
	}

	/**
	 * This method is used to create the prepared statements 
	 * @throws DataAccessException
	 */
	private void init() throws DataAccessException {
		con = DBConnection.getInstance();
		try {
			findAllPS = con.getConnection().prepareStatement(FIND_ALL_Q);
			findByEmployeeNoPS = con.getConnection().prepareStatement(FIND_BY_EmployeeNo_Q);
			findByNamePS = con.getConnection().prepareStatement(FIND_BY_NAME_Q);
			findByFullNamePS = con.getConnection().prepareStatement(FIND_BY_FULL_NAME_Q);
			insertPS = con.getConnection().prepareStatement(INSERT_Q);
			updatePS = con.getConnection().prepareStatement(UPDATE_Q);
			deletePS = con.getConnection().prepareStatement(DELETE_Q);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}

	}

	/**
	 * This method is used to find a employee in database with a specific employeeNo
	 * @param employeeNo
	 * @return res
	 */	
	@Override
	public Employee findByEmployeeNo(int employeeNo) throws DataAccessException, SQLException {
		Employee res = null;
		findByEmployeeNoPS.setInt(1, employeeNo);
		ResultSet rs = findByEmployeeNoPS.executeQuery();
		if (rs.next()) {
			res = buildObject(rs);
		}

		return res;
	}

	/**
	 * This method is used to find a employee in database with a specific name
	 * @param name
	 * @return res
	 */	
	@Override
	public Employee findByName(String name) throws DataAccessException {
		Employee res = null;
		try {
			findByNamePS.setString(1, name);
			ResultSet rs = findByNamePS.executeQuery();
			if (rs.next()) {
				res = buildObject(rs);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return res;
	}
	
	/**
	 * This method is used to find a employee in database with a specific first name and last name
	 * @param fullname
	 * @return res
	 * 
	 */	
	@Override
	public Employee findEmployeeByFirstAndLastName(String fullname) throws DataAccessException, SQLException {
		Employee res = null;
		try {
			findByFullNamePS.setString(1, fullname);
			ResultSet rs = findByFullNamePS.executeQuery();
			if (rs.next()) {
				res = buildObject(rs);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return res;
	}

	/**
	 * This method is used to insert employee object into the database
	 * @param employee
	 * @return employee 
	 */	
	@Override
	public Employee insert(Employee employee) throws DataAccessException, SQLException {
		try {
			con.startTransaction();
			insertPS.setInt(1, employee.getEmployeeNo());
			insertPS.setString(2, employee.getFname());
			insertPS.setString(3, employee.getLname());
			insertPS.setString(4, employee.getEmail());
			insertPS.setString(5, employee.getPhoneNo());
			insertPS.setBoolean(6, employee.isAdmin());
			insertPS.setInt(7, employee.getRole().getId());
			insertPS.setInt(8, employee.getLevel().getId());
			insertPS.execute();
			con.commitTransaction();
		} catch (DataAccessException e) {
			con.rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return employee;
	}
	/**
	 * This method is used to update attribute for a employee in the database 
	 * and the tempid
	 * @param employee
	 * @param tempid
	 * @return employee
	 */
	@Override
	public Employee update(Employee employee, int tempid) throws DataAccessException, SQLException {
		try {
			con.startTransaction();
			updatePS.setInt(1, employee.getEmployeeNo());
			updatePS.setString(2, employee.getFname());
			updatePS.setString(3, employee.getLname());
			updatePS.setString(4, employee.getEmail());
			updatePS.setString(5, employee.getPhoneNo());
			updatePS.setBoolean(6, employee.isAdmin());
			updatePS.setInt(7, employee.getRole().getId());
			updatePS.setInt(8, employee.getLevel().getId());
			updatePS.setInt(9, tempid);
			updatePS.execute();
			con.commitTransaction();
		} catch (DataAccessException e) {
			con.rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return employee;
	}
	
	/**
	 * This method is used to delete a employee from the database use the employeeNo
	 * @param employee
	 */
	@Override
	public void deleteEmployeeById(int employeeNo) throws DataAccessException, SQLException {
		deletePS.setInt(1, employeeNo);
		deletePS.execute();
	}

	@Override
	public  List<Employee>  findAll() throws DataAccessException  {
		ResultSet rs = null;	
		try {
			rs = this.findAllPS.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Employee> res = buildObjects(rs);
		return res;
	}

	/**
	 * This method is used to build a list of objects from the database
	 * @param rs
	 * @return List<Shift> res
	 * @throws DataAccessException
	 */
	private List<Employee> buildObjects(ResultSet rs) throws DataAccessException  {
		List<Employee> res = new ArrayList<Employee>();
		try {
			while (rs.next()) {
				Employee currShift = buildObject(rs);
				res.add(currShift);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}

	/**
	 * This method is used to build a object from the database
	 * @param rs
	 * @return currEmployee
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	private Employee buildObject(ResultSet rs) throws DataAccessException, SQLException {
		Employee currEmployee = new Employee();
		
		currEmployee.setEmployeeNo(rs.getInt("EmployeeNo"));
		currEmployee.setFname(rs.getString("fname"));
		currEmployee.setLname(rs.getString("lname"));
		currEmployee.setEmail(rs.getString("email"));
		currEmployee.setPhoneNo(rs.getString("phoneNo"));
		currEmployee.setAdmin(rs.getBoolean("isAdmin"));
		Role curRole = roleTypeDB.findById(rs.getInt("role"));
		currEmployee.setRole(curRole);
		Level curLevel = levelDB.findById(rs.getInt("level"));
		currEmployee.setLevel(curLevel);
		
		return currEmployee;
	}
}
