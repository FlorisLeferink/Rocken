package controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import database.MonitorDB;
import database.MonitorDBIF;

public class MonitorController {
	MonitorDBIF monitorDB;
	
	public MonitorController() {
		monitorDB = new MonitorDB();
	}
	
	public Boolean checkIfEdited() {
		Boolean dateTime = null;
		try {
			dateTime = monitorDB.checkForEdit();
		} catch (DataAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dateTime;
	}
	
	public void changePerformed() {
		monitorDB.stateChanged();
	}
	
	public void informationUpdated(){
		monitorDB.informationUpdated();
	}

}
