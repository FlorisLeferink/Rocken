package database;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import controller.DataAccessException;

public interface MonitorDBIF {
	public Boolean checkForEdit() throws DataAccessException, SQLException;
	public void stateChanged();
	public void informationUpdated();
}
