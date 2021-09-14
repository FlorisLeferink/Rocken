package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import controller.DataAccessException;
import model.Employee;
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

public class ShiftDB implements ShiftDBIF {
	private DBConnection con;
	private EmployeeDBIF employeeDB;
	private ShiftRequestDBIF shiftRequestDB;
	private RoleDBIF roleDB;

	private static final String FIND_ALL_Q = "select id, startat, endat, note, role, employeeNo, workdayId from Shift ";
	private PreparedStatement findAllPS;

	private static final String FIND_BY_ID_Q = FIND_ALL_Q + "  where id = ?";
	private PreparedStatement findByIdPS;

	private static final String FIND_BY_DATE_Q = FIND_ALL_Q + " where workdayId = ?";
	private PreparedStatement findByDatePS;

	private static final String UPDATE_SHIFT_Q = "Update Shift set startAt = ?, endAt = ?, note = ?, role = ?, employeeNo = ? where id = ?";
	private PreparedStatement updateShiftPS;

	private static final String INSERT_Q = "insert into Shift (startat, endat, note, role, employeeNo, workdayId) values (?,?,?,?,?,?)";
	private PreparedStatement insertPS;

	private static final String LASTID_Q = "select top 1 id, startat, endat, note, role, employeeNo, workdayId from Shift ORDER BY id DESC";
	private PreparedStatement lastIdPS;

	public ShiftDB(EmployeeDBIF employeeDB, ShiftRequestDBIF shiftRequestDB) throws DataAccessException {
		this.employeeDB = employeeDB;
		this.shiftRequestDB = shiftRequestDB;
		init();
	}

	public ShiftDB() throws DataAccessException {
		employeeDB = new EmployeeDB();
		shiftRequestDB = new ShiftRequestDB();
		roleDB = new RoleDB();
		init();
	}
	/**
	 * This method is used create the prepare statement for the Database
	 */
	private void init() throws DataAccessException {
		con = DBConnection.getInstance();
		try {
			findAllPS = con.getConnection().prepareStatement(FIND_ALL_Q);
			findByIdPS = con.getConnection().prepareStatement(FIND_BY_ID_Q);
			findByDatePS = con.getConnection().prepareStatement(FIND_BY_DATE_Q);
			insertPS = con.getConnection().prepareStatement(INSERT_Q);
			updateShiftPS = con.getConnection().prepareStatement(UPDATE_SHIFT_Q);

			lastIdPS = con.getConnection().prepareStatement(LASTID_Q);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	/**
	 * This method is used to return the id from the last shift that was insert into database
	 */	
	@Override
	public int getLastId() {
		Shift res = null;
		ResultSet rs;
		try {
			rs = lastIdPS.executeQuery();
			if (rs.next()) {
				res = buildObject(rs);
			}
		} catch (SQLException | DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (res == null) ? 1 : res.getId();

	}
	/**
	 * This method is used to find a shift in database with a specific id
	 * @param id
	 * @return res
	 */	
	@Override
	public Shift findById(int id) throws DataAccessException {
		Shift res = null;
		try {
			findByIdPS.setInt(1, id);
			ResultSet rs = findByIdPS.executeQuery();
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
	 * This method is used to return a list of shifts from a specific work day
	 * @param workday
	 * @return List<Shift> res
	 */	
	@Override
	public List<Shift> findByDate(Workday workday) throws DataAccessException {
		List<Shift> res = null;
		int id = workday.getId();
		try {
			findByDatePS.setInt(1, id);
			ResultSet rs = findByDatePS.executeQuery();
			res = buildObjects(rs);
		} catch (SQLException e) {
			// e.printStackTsrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}

		return res;
	}

	/**
	 * This method is used to update the attribute on a exiting shift in the database
	 * @param shift
	 * @return shift
	 */	
	@Override
	public Shift update(Shift shift) throws DataAccessException, SQLException {
		try {
			con.startTransaction();
			updateShiftPS.setInt(6, shift.getId());
			updateShiftPS.setTime(1, Time.valueOf(shift.getStartAt()));
			updateShiftPS.setTime(2, Time.valueOf(shift.getEndAt()));
			updateShiftPS.setString(3, shift.getNote());
			updateShiftPS.setInt(4, shift.getRole().getId());

			if (shift.getEmployee() != null) {
				updateShiftPS.setInt(5, shift.getEmployee().getEmployeeNo());
			} else {
				updateShiftPS.setNull(5, Types.INTEGER);
			}
			updateShiftPS.execute();
			con.commitTransaction();
		} catch (DataAccessException e) {
			con.rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return shift;
	}

	/**
	 * This method is used to insert shift object into the database
	 * @param shift
	 * @param workday
	 * @return shift 
	 */	
	@Override
	public Shift insert(Shift shift, Workday workday) throws DataAccessException, SQLException {
		try {
			con.startTransaction();
			insertPS.setTime(1, Time.valueOf(shift.getStartAt()));
			insertPS.setTime(2, Time.valueOf(shift.getEndAt()));
			insertPS.setString(3, shift.getNote());
			insertPS.setInt(4, shift.getRole().getId());

			if (shift.getEmployee() != null) {
				insertPS.setInt(5, shift.getEmployee().getEmployeeNo());
			} else {
				insertPS.setNull(5, Types.INTEGER);
			}

			insertPS.setInt(6, workday.getId());
			insertPS.execute();
			con.commitTransaction();
		} catch (DataAccessException e) {
			con.rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return shift;
	}
	
	/**
	 * This method is used to find all shift from the database into a list
	 * @return res
	 */
	@Override
	public List<Shift> findAll() throws DataAccessException {
		ResultSet rs;
		try {
			rs = this.findAllPS.executeQuery();
		} catch (SQLException e) {
			// e.printStackTsrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		List<Shift> res = buildObjects(rs);
		return res;
	}
	
	/**
	 * This method is used to build a list of objects from the database
	 * @param rs
	 * @return List<Shift> res
	 * @throws DataAccessException
	 */
	private List<Shift> buildObjects(ResultSet rs) throws DataAccessException {
		List<Shift> res = new ArrayList<Shift>();
		try {
			while (rs.next()) {
				Shift currShift = buildObject(rs);
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
	 * @return currShift
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	private Shift buildObject(ResultSet rs) throws DataAccessException, SQLException {
		Shift currShift = new Shift();
		currShift.setId(rs.getInt("id"));
		currShift.setStartAt(rs.getTime("startat").toLocalTime());
		currShift.setEndAt(rs.getTime("endat").toLocalTime());
		currShift.setNote(rs.getString("note"));
		currShift.setRole(roleDB.findById(rs.getInt("role")));
		List<ShiftRequest> shiftRequestList = shiftRequestDB.findShiftRequestByShiftId(rs.getInt("id"));

		if (shiftRequestList != null) {
			currShift.setListOfShiftRequest(shiftRequestList);
		}
		int employeeNo = rs.getInt("employeeNo");
		if (employeeNo == 0) {
			currShift.setEmployee(null);
		} else {
			Employee currEmployee = employeeDB.findByEmployeeNo(rs.getInt("employeeNo"));
			currShift.setEmployee(currEmployee);
		}

		return currShift;
	}
}
