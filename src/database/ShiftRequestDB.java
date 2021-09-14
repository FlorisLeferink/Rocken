package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.DataAccessException;
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

public class ShiftRequestDB implements ShiftRequestDBIF {
	private DBConnection con;
	private EmployeeDBIF employeeDB;

	private static final String FIND_ALL_Q = "select id, note, shiftId, employeeNo from ShiftRequest ";
	private PreparedStatement findAllPS;

	private static final String FIND_BY_ID_Q = FIND_ALL_Q + " where id = ?";
	private PreparedStatement findByIdPS;

	private static final String FIND_BY_SHIFT_ID_Q = FIND_ALL_Q + " where shiftId = ?";
	private PreparedStatement findByShiftIdPS;

	private static final String INSERT_Q = "insert into ShiftRequest (note, shiftId, employeeNo) values (?,?,?)";
	private PreparedStatement insertPS;

	private static final String REMOVE_Q = "delete from ShiftRequest where id = ?";
	private PreparedStatement removePS;

	private static final String LASTID_Q = "select top 1 id from ShiftRequest ORDER BY id DESC";
	private PreparedStatement lastIdPS;

	public ShiftRequestDB() throws DataAccessException {
		employeeDB = new EmployeeDB();
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
			findByIdPS = con.getConnection().prepareStatement(FIND_BY_ID_Q);
			insertPS = con.getConnection().prepareStatement(INSERT_Q);
			findByShiftIdPS = con.getConnection().prepareStatement(FIND_BY_SHIFT_ID_Q);
			removePS = con.getConnection().prepareStatement(REMOVE_Q);
			lastIdPS = con.getConnection().prepareStatement(LASTID_Q);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	/**
	 * This method is used to return the shiftReqest found by id
	 * @return res
	 */
	@Override
	public ShiftRequest findById(int id) throws DataAccessException {
		ShiftRequest res = null;
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
	 * This method is used to insert a shiftRequest into the database
	 * @return shiftRequest
	 */
	@Override
	public ShiftRequest insert(ShiftRequest shiftRequest, Shift shift) throws DataAccessException, SQLException {
		try {
			con.startTransaction();
			insertPS.setString(1, shiftRequest.getNote());
			insertPS.setInt(2, shift.getId());
			insertPS.setInt(3, shiftRequest.getEmployee().getEmployeeNo());
			insertPS.execute();
			con.commitTransaction();
		} catch (DataAccessException e) {
			con.rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return shiftRequest;
	}

	/**
	 * This method is used to return shiftRequests found by the id from a shift
	 * @return List<ShiftRequest> res returns shiftRequests found on the shift
	 */
	@Override
	public List<ShiftRequest> findShiftRequestByShiftId(int id) throws DataAccessException {
		ResultSet rs = null;
		try {
			findByShiftIdPS.setInt(1, id);
			rs = findByShiftIdPS.executeQuery();
		} catch (SQLException e) { //
			e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		List<ShiftRequest> res = buildObjects(rs);
		return res;
	}

	/**
	 * This method is used to return all shiftRequests
	 * @return List<ShiftRequest> res returns all shiftRequests
	 */
	@Override
	public List<ShiftRequest> findAll() throws DataAccessException {
		ResultSet rs;
		try {
			rs = findAllPS.executeQuery();
		} catch (SQLException e) {
			// e.printStackTsrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		List<ShiftRequest> res = buildObjects(rs);
		return res;
	}

	/**
	 * This method is used to delete a shiftRequest by id
	 * @param id
	 */
	@Override
	public void removeById(int id) throws DataAccessException {

		con.startTransaction();

		try {
			removePS.setInt(1, id);
			removePS.execute();
			con.commitTransaction();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			con.rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
	}

	/**
	 * This method is used to find last last shiftRequest insert into the database
	 * @return res
	 */
	@Override
	public int getLastId() throws DataAccessException {
		int res = 1;
		ResultSet rs;
		try {
			rs = lastIdPS.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * This method is used to build a list of objects from the database
	 * @param rs
	 * @return List<ShiftRequest> res
	 * @throws DataAccessException
	 */
	private List<ShiftRequest> buildObjects(ResultSet rs) throws DataAccessException {
		List<ShiftRequest> res = new ArrayList<ShiftRequest>();
		try {
			while (rs.next()) {
				ShiftRequest currShiftRequest = buildObject(rs);
				res.add(currShiftRequest);
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
	 * @return currShiftRequest
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	private ShiftRequest buildObject(ResultSet rs) throws DataAccessException, SQLException {
		ShiftRequest currShiftRequest = new ShiftRequest();
		currShiftRequest.setId(rs.getInt("id"));
		currShiftRequest.setNote(rs.getString("note"));
		Employee currEmployee = employeeDB.findByEmployeeNo(rs.getInt("employeeNo"));
		currShiftRequest.setEmployee(currEmployee);
		return currShiftRequest;
	}

}
