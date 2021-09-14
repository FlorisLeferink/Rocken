package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.DataAccessException;
import model.Role;

/**
 * @author Brian Bundgaard Engelbrektsen
 * @author Thomas Henriksen
 * @author Simon Ørts Niese
 * @author Floris Ruben Leferink
 * @author Kasper Johan Knudsen
 */

public class RoleDB implements RoleDBIF {
	private DBConnection con;

	private static final String FIND_ALL_Q = "select id, name from Role ";
	private PreparedStatement findAllPS;

	private static final String FIND_BY_ID_Q = FIND_ALL_Q + " where id = ?";
	private PreparedStatement findByIdPS;

	private static final String FIND_BY_NAME_Q = FIND_ALL_Q + " where name = ?";
	private PreparedStatement findByNamePS;

	private static final String INSERT_Q = "insert into Role (name) values (?)";
	private PreparedStatement insertPS;

	public RoleDB() throws DataAccessException {

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
	 * This method is used to find a role in database with a specific id
	 * @param id
	 * @return res
	 */	
	@Override
	public Role findById(int id) throws DataAccessException {
		Role res = null;
		try {
			findByIdPS.setInt(1, id);
			ResultSet rs = findByIdPS.executeQuery();
			if (rs.next()) {
				res = buildObject(rs);
			}
		} catch (SQLException e) {

			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return res;
	}

	/**
	 * This method is used to find a role in database with a specific name
	 * @param name
	 * @return res
	 */	
	@Override
	public Role findByName(String name) throws DataAccessException {
		Role res = null;
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
	 * This method is used to insert role object into the database
	 * @param role
	 * @return shift 
	 */	
	@Override
	public Role insert(Role role) throws DataAccessException, SQLException {
		try {
			con.startTransaction();
			insertPS.setString(1, role.getName());
			insertPS.execute();
			con.commitTransaction();
		} catch (DataAccessException e) {
			con.rollbackTransaction();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return role;
	}

	/**
	 * This method is used to find all role from the database into a list
	 * @return res
	 */
	@Override
	public List<Role> findAll() throws DataAccessException {
		ResultSet rs;
		try {
			rs = this.findAllPS.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		List<Role> res = buildObjects(rs);
		return res;
	}

	/**
	 * This method is used to build a list of objects from the database
	 * @param rs
	 * @return List<Shift> res
	 * @throws DataAccessException
	 */
	private List<Role> buildObjects(ResultSet rs) throws DataAccessException {
		List<Role> res = new ArrayList<>();
		try {
			while (rs.next()) {
				Role currRole = buildObject(rs);
				res.add(currRole);
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
	 * @return currRole
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	private Role buildObject(ResultSet rs) throws DataAccessException, SQLException {
		Role currRole = new Role();
		try {

			currRole.setId(rs.getInt("id"));
			currRole.setName(rs.getString("name"));

		} catch (SQLException e) {
			// e.printStackTrace();

			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}

		return currRole;
	}
}
