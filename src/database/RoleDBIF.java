/**
 * 
 */
package database;

import java.sql.SQLException;
import java.util.List;

import controller.DataAccessException;
import model.Role;

/**
 * @author Thomas
 *
 */
public interface RoleDBIF {
	public Role findById(int i) throws DataAccessException;

	public Role insert(Role roleType) throws DataAccessException, SQLException;

	public List<Role> findAll() throws DataAccessException;

	public Role findByName(String name) throws DataAccessException;
}
