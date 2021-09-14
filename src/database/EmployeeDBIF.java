package database;

import java.sql.SQLException;
import java.util.List;

import controller.DataAccessException;
import model.Employee;

public interface EmployeeDBIF {
	public List<Employee> findAll() throws DataAccessException, SQLException;

	public Employee findByEmployeeNo(int employeeNo) throws DataAccessException, SQLException;

	public Employee findByName(String name) throws DataAccessException;

	public Employee insert(Employee employee) throws DataAccessException, SQLException;
	
	public Employee update(Employee employee, int tempid) throws DataAccessException, SQLException;

	public Employee findEmployeeByFirstAndLastName(String fullname) throws DataAccessException, SQLException;

	public void deleteEmployeeById(int employeeNo) throws DataAccessException, SQLException;

}
