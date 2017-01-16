package io.npj.todo;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.sql.*;

public class DB {
	public static class DataFileException extends Exception {
		DataFileException(String msg) {
			super(msg);
		}
	}

	private static DB instance = null;
	private static String DATA_DIR = ".todo";

	private Connection connection = null;

	public static DB getInstance() throws SQLException, DataFileException {
		if (instance == null) {
			instance = new DB();
		}
		return instance;
	}

	private DB() throws SQLException, DataFileException {
		connectDB();
		createTables();
	}

	public Connection getConnection() {
		return connection;
	}

	public PreparedStatement prepareInsert(String tableName, String[] columns) throws SQLException {
		String cols = String.join(",", columns);
	    String vals = StringUtils.repeat("?", ",", columns.length);
	    String sql  = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, cols, vals);
		return connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	private String getConnectionUri() throws DataFileException {
        return String.format("jdbc:sqlite:%s/todo.sqlite", buildDataDirectory());
    }

	private void connectDB() throws SQLException, DataFileException {
		connection = DriverManager.getConnection(getConnectionUri());
		connection.setAutoCommit(false);
	}

	private void createTables() throws SQLException {
		createListsTable();
		createItemsTable();
	}

	private void createListsTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS todo_lists (" +
				"  id INTEGER PRIMARY KEY AUTOINCREMENT" +
				", name TEXT NOT NULL" +
				", created_at TEXT NOT NULL" +
				", updated_at TEXT NOT NULL" +
				")";

		connection.createStatement().executeUpdate(sql);
	}

	private void createItemsTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS todo_items (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT" +
				", list_id INTEGER NOT NULL" +
				", item_text TEXT NOT NULL" +
				", completed_at TEXT NULL" +
				", created_at TEXT NOT NULL" +
				", updated_at TEXT NOT NULL" +
				")";

		connection.createStatement().executeUpdate(sql);
	}

	private String buildDataDirectory() throws DataFileException {
		File path = new File(System.getProperty("user.home"), DATA_DIR);
		if (!path.exists() && !path.mkdirs()) {
		    throw new DataFileException(String.format("unable to create data directory: %s", path.toString()));
		}
		return path.toString();
	}
}
