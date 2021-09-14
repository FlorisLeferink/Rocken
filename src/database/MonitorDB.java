package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.DataAccessException;
import model.Level;

public class MonitorDB implements MonitorDBIF {
	private DBConnection con;

	private static final String FIND_ALL_DATETIMES = "select edited from LastEdited";
	private PreparedStatement findAllDt;
	
	private static final String CHANGE_MADE = "UPDATE TOP (1) LastEdited SET  edited = 1";
	private PreparedStatement changeMadePS;
	
	private static final String INFORMATION_UPDATED = "UPDATE TOP (1) LastEdited SET  edited = 0";
	private PreparedStatement informationUpdatedPS;
	
	public MonitorDB()  {
		try {
			init();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	private void init() throws DataAccessException {
		con = DBConnection.getInstance();
		try {
			findAllDt = con.getConnection().prepareStatement(FIND_ALL_DATETIMES);
			changeMadePS = con.getConnection().prepareStatement(CHANGE_MADE);
			informationUpdatedPS = con.getConnection().prepareStatement(INFORMATION_UPDATED);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	@Override
	public Boolean checkForEdit() throws DataAccessException, SQLException {
		Boolean res = null;
		try {
			ResultSet rs = findAllDt.executeQuery();
			if (rs.next()) {
				res = buildObject(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	

	@Override
	public void stateChanged() {
		try {
			changeMadePS.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void informationUpdated() {
		try {
			informationUpdatedPS.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private Boolean buildObject(ResultSet rs) throws DataAccessException, SQLException {
		Boolean bool = null;
		try {
			bool = rs.getBoolean("Edited");
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}

		return bool;
	}
}
