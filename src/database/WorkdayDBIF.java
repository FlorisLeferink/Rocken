package database;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import controller.DataAccessException;
import model.Workday;

public interface WorkdayDBIF {
	public Workday findById(int id) throws DataAccessException;

	public Workday insert(Workday workday) throws SQLException, DataAccessException;

	public List<Workday> findAll() throws DataAccessException;

	public Workday findByDate(LocalDate date) throws DataAccessException;
	
	public int getLastId() throws DataAccessException;

	List<Workday> findWorkdayRange(String startDate, String endDate) throws DataAccessException;
}
