package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controller.DataAccessException;
import model.Shift;
import model.Workday;

/**
 * @author Brian Bundgaard Engelbrektsen
 * @author Thomas Henriksen
 * @author Simon Ørts Niese
 * @author Floris Ruben Leferink
 * @author Kasper Johan Knudsen
 */

public class WorkdayDB implements WorkdayDBIF {
	private DBConnection con;
	private ShiftDBIF shiftDB;

	private static final String FIND_ALL_Q = "select id, date, note, published from Workday";
	private PreparedStatement findAllPS;

	private static final String FIND_BY_ID_Q = FIND_ALL_Q + " where id = ?";
	private PreparedStatement findById;

	private static final String FIND_BY_Date_Q = FIND_ALL_Q + " where date = ?";
	private PreparedStatement findByDate;

	private static final String INSERT_Q = "insert into Workday (date, note, published ) values (?,?,?)";
	private PreparedStatement insertPS;

	private static final String LASTID_Q = "select top 1 id, date, note, published from Workday ORDER BY id DESC";
	private PreparedStatement lastIdPS;
	
	private static final String WORKDAY_RANGE = "SELECT * FROM Workday WHERE date between ? and ?";
	private PreparedStatement workdayRange;

	public WorkdayDB() throws DataAccessException {
		shiftDB = new ShiftDB();
		init();
	}

	/**
	 * This method is used create the prepared statements for the Database
	 */
	private void init() throws DataAccessException {
		con = DBConnection.getInstance();
		try {
			findAllPS = con.getConnection().prepareStatement(FIND_ALL_Q);
			findById = con.getConnection().prepareStatement(FIND_BY_ID_Q);
			insertPS = con.getConnection().prepareStatement(INSERT_Q);
			findByDate = con.getConnection().prepareStatement(FIND_BY_Date_Q);
			lastIdPS = con.getConnection().prepareStatement(LASTID_Q);
			workdayRange = con.getConnection().prepareStatement(WORKDAY_RANGE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	/**
	 * This method is used to return the id from the last workday that was insert into database
	 */	
	@Override
	public int getLastId() throws DataAccessException {
		Workday res = null;
		try {
			ResultSet rs = lastIdPS.executeQuery();
			if (rs.next()) {
				res = buildObject(rs);
			}
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return (res == null ? 0 : res.getId());
	}

	/**
	 * This method is used to find a workday in database with a specific id
	 * @param id
	 * @return res
	 * @Override
	 */	
	@Override
	public Workday findById(int id) throws DataAccessException {
		Workday res = null;
		try {
			findById.setInt(1, id);
			ResultSet rs = findById.executeQuery();
			if (rs.next()) {
				res = buildObject(rs);
			}
		} catch (SQLException e) {

			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return res;

	}

	/**
	 * This method is used to find a workday by a specific date from the database
	 * @param date
	 * @return res	 
	 */
	@Override
	public Workday findByDate(LocalDate date) throws DataAccessException {
		Workday res = null;
		try {
			findByDate.setDate(1, Date.valueOf(date));
			ResultSet rs = findByDate.executeQuery();
			if (rs.next()) {
				res = buildObject(rs);
			}
		} catch (SQLException e) {

			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return res;

	}

	
	/**
	 * This method is used to insert a workday object into the database
	 * @param workday
	 * @return workday
	 */
	@Override
	public Workday insert(Workday workday) throws SQLException, DataAccessException {
		try {
			con.startTransaction();
			insertPS.setDate(1, Date.valueOf(workday.getDate()));
			insertPS.setString(2, workday.getNote());
			insertPS.setBoolean(3, workday.isPublished());
			insertPS.execute();
			con.commitTransaction();
		} catch (DataAccessException e) {
			con.rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}

		return workday;
	}


	/**
	 * This method is to find all workday in the database into a list
	 * @return List<Workday> res returns all workdays in a list
	 */
	@Override
	public List<Workday> findAll() throws DataAccessException {
		ResultSet rs;
		try {
			rs = findAllPS.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		List<Workday> res = buildObjects(rs);
		return res;
	}
	
	
	/**
	 * This method is used to find all workdays between startDate and Enddate and return them in a list
	 * Used to return a week worth of workdays, starting at monday and ending sunday
	 * @param startDate
	 * @param endDate
	 * @return List<Workday> res returns all workdays found within range in a list
	 */
	@Override
	public List<Workday> findWorkdayRange(String startDate, String endDate) throws DataAccessException {
		ResultSet rs = null;
		
		try {
			con.startTransaction();
			workdayRange.setString(1, startDate);
			workdayRange.setString(2, endDate);
			rs = workdayRange.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Workday> res = buildObjects(rs);
		return res;
	}
	
	/**
	 * This method is used to build a object from the database
	 * @param rs
	 * @return List<Workday> res
	 * @throws DataAccessException
	 */
	private List<Workday> buildObjects(ResultSet rs) throws DataAccessException {
		List<Workday> res = new ArrayList<Workday>();
		try {
			while (rs.next()) {
				Workday currWorkDay = buildObject(rs);
				res.add(currWorkDay);
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
	 * @return currWorkDay
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	private Workday buildObject(ResultSet rs) throws DataAccessException, SQLException {
		Workday currWorkDay = new Workday();
		try {
			currWorkDay.setId(rs.getInt("id"));
			currWorkDay.setDate(rs.getDate("date").toLocalDate());
			currWorkDay.setNote(rs.getString("note"));
			currWorkDay.setPublished(rs.getBoolean("published"));
			List<Shift> listOfShift = shiftDB.findByDate(currWorkDay);
			if (listOfShift != null) {
				for (Shift shift : listOfShift) {
					currWorkDay.addShift(shift);
				}
			}
		} catch (SQLException e) {
			// e.printStackTrace();

			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}

		return currWorkDay;
	}
}
