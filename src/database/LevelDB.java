package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.DataAccessException;

import model.Level;

/**
 * @author Brian Bundgaard Engelbrektsen
 * @author Thomas Henriksen
 * @author Simon Ørts Niese
 * @author Floris Ruben Leferink
 * @author Kasper Johan Knudsen
 */

public class LevelDB implements LevelDBIF {
	private DBConnection con;

	private static final String FIND_ALL_Q = "select id, name from Level";
	private PreparedStatement findAllPS;

	private static final String FIND_BY_ID_Q = FIND_ALL_Q + " where id = ?";
	private PreparedStatement findByIdPS;

	private static final String FIND_BY_NAME_Q = FIND_ALL_Q + " where name = ?";
	private PreparedStatement findByNamePS;

	private static final String INSERT_Q = "insert into Level (name) values (?)";
	private PreparedStatement insertPS;

	
	public LevelDB() throws DataAccessException {

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
			findByNamePS = con.getConnection().prepareStatement(FIND_BY_NAME_Q);
			insertPS = con.getConnection().prepareStatement(INSERT_Q);
		
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	/**
	 * This method is used to find a level in database with a specific id
	 * @param id
	 * @return res
	 */
	@Override
	public Level findById(int id) throws DataAccessException {
		Level res = null;
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
	 * This method is used to find a level in database with a specific name
	 * @param name
	 * @return res
	 */	
	@Override
	public Level findByName(String name) throws DataAccessException {
		Level res = null;
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
	 * This method is used to insert level object into the database
	 * @param level
	 * @return level 
	 */	
	@Override
	public Level insert(Level level) throws DataAccessException, SQLException {
		try {
			con.startTransaction();
			insertPS.setString(1, level.getName());
			insertPS.execute();
			con.commitTransaction();
		} catch (DataAccessException e) {
			con.rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		return level;
	}
	
	/**
	 * This method is used to return all level
	 * @return List<ShiftRequest> res returns all level
	 */
	@Override
	public List<Level> findAll() throws DataAccessException {
		ResultSet rs;
		try {
			rs = this.findAllPS.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		List<Level> res = buildObjects(rs);
		return res;
	}

	/**
	 * This method is used to build a list of objects from the database
	 * @param rs
	 * @return res
	 * @throws DataAccessException
	 */
	private List<Level> buildObjects(ResultSet rs) throws DataAccessException {
		List<Level> res = new ArrayList<>();
		try {
			while (rs.next()) {
				Level currLevel = buildObject(rs);
				res.add(currLevel);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}

	/**
	 * This method is used to build a object from the database
	 * @param rs
	 * @return currLevel
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	private Level buildObject(ResultSet rs) throws DataAccessException, SQLException {
		Level currLevel = new Level();
		try {

			currLevel.setId(rs.getInt("id"));
			currLevel.setName(rs.getString("name"));

		} catch (SQLException e) {
			// e.printStackTrace();

			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}

		return currLevel;
	}
}
