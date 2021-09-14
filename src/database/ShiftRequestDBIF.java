package database;

import java.sql.SQLException;
import java.util.List;

import controller.DataAccessException;
import model.Shift;
import model.ShiftRequest;

public interface ShiftRequestDBIF {
	public ShiftRequest findById(int id) throws DataAccessException;

	public List<ShiftRequest> findAll() throws DataAccessException;

	public ShiftRequest insert(ShiftRequest shiftRequest, Shift shift) throws DataAccessException, SQLException;
	
	public void removeById(int id) throws DataAccessException;

	public List<ShiftRequest> findShiftRequestByShiftId(int id) throws DataAccessException;

	int getLastId() throws DataAccessException;
}
