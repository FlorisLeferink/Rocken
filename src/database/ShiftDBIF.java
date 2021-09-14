package database;

import java.sql.SQLException;
import java.util.List;

import controller.DataAccessException;
import model.Shift;
import model.Workday;

public interface ShiftDBIF {
	public Shift findById(int id) throws DataAccessException;

	public Shift insert(Shift shift, Workday workday) throws DataAccessException, SQLException;

	public Shift update(Shift shift) throws DataAccessException, SQLException;

	public List<Shift> findAll() throws DataAccessException;

	public int getLastId() throws DataAccessException;

	public List<Shift> findByDate(Workday workday) throws DataAccessException;
}
