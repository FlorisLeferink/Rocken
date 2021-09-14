package database;

import java.sql.SQLException;
import java.util.List;

import controller.DataAccessException;
import model.Level;

public interface LevelDBIF {
	public Level findById(int id) throws DataAccessException;

	public Level insert(Level level) throws DataAccessException, SQLException;

	public List<Level> findAll() throws DataAccessException;

	public Level findByName(String name) throws DataAccessException;
}
