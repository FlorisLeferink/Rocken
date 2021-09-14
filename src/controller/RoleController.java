package controller;

import java.sql.SQLException;
import java.util.List;

import database.RoleDB;
import database.RoleDBIF;
import model.Role;

/**
 * @author Brian Bundgaard Engelbrektsen
 * @author Thomas Henriksen
 * @author Simon Ørts Niese
 * @author Floris Ruben Leferink
 * @author Kasper Johan Knudsen
 */

/**
 *		This is the controller for Role, responsible for create new role, find role by id or name,
 *		update exiting Role, return list of roles, save role in datebase, makes a check in the database
 *      for exiting role.		 
 */
public class RoleController {
	private RoleDBIF roleDB;

	/**
	 * This constructor makes a instance of a ShiftDB 
	 */
	public RoleController() {
		try {
			roleDB = new RoleDB();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to find a shift by id. It will search the database for
	 * the shift and return the shift.
	 * @param id
	 * @return currRole
	 */
	public Role findRoleById(int id) {
		Role currRole = null;
		try {
			return currRole = roleDB.findById(id);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currRole;
	}

	/**
	 * This method is used to find a role by name. It will search the database for
	 * the role and return the role
	 * @param name
	 * @return
	 */
	public Role findRoleByName(String name) {
		Role currRole = null;
		try {
			return currRole = roleDB.findByName(name);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currRole;
	}

	/**
	 * This method is used to create a new role
	 * @param name
	 * @return check boolean
	 */
	public boolean CreateNewRole(String name) {
		boolean check = false;
		// Call a list of role 
		try {
			check = checkListOfRoles(name);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// If the role do not exited create a new role and insert into database
		if (!check) {
			Role role = new Role(name);
			try {
				roleDB.insert(role);
			} catch (DataAccessException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return check;

	}

	/**
	 * This method is used for find a list of all role. It will search the database for
	 * the roles and return a list of roles.
	 * @return List<Role> list of role
	 */
	public List<Role> ListOfRole() {
		List<Role> currRole = null;
		try {
			currRole = roleDB.findAll();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currRole;
	}

	/**
	 * This method is used to check if a name of a role already exiting in the database
	 * @param name
	 * @return exiting boolean
	 * @throws DataAccessException
	 */
	private boolean checkListOfRoles(String name) throws DataAccessException {
		boolean exiting = false;
		List<Role> listOfRoles = ListOfRole();
		int i = 0;
		int size = listOfRoles.size();
		while (i < size && !exiting) {
			Role temp = listOfRoles.get(i);
			if (temp.getName().equals(name)) {
				exiting = true;
			}
			i++;
		}
		return exiting;
	}

}
