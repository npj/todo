package io.npj.todo;

import java.sql.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by npj on 12/12/16.
 */
public class DB {
	protected static DB instance = null;
	protected static String DATA_DIR = ".todo";

	protected Connection connection = null;

	public static DB getInstance() throws SQLException {
		if (instance == null) {
			instance = new DB();
		}
		return instance;
	}

	protected DB() throws SQLException {
		connectDB();
		createTables();
	}

	public Connection getConnection() {
		return connection;
	}

	public PreparedStatement prepareInsert(String tableName, String[] columns) throws SQLException {
		StringBuilder builder = new StringBuilder("INSERT INTO ")
				.append(tableName)
				.append(" (");

		builder.append(String.join(",", columns));
		builder.append(") VALUES (");
		builder.append(String.join(",", Arrays.stream(columns).map(c -> "?").collect(Collectors.toList())));
		builder.append(")");

		return connection.prepareStatement(
			builder.toString(),
			PreparedStatement.RETURN_GENERATED_KEYS
		);
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	private void connectDB() throws SQLException {
		String connectionUri = new StringBuilder("jdbc:sqlite:")
				.append(System.getProperty("user.home"))
				.append("/")
				.append(DATA_DIR)
				.append("/todo.sqlite")
				.toString();

		connection = DriverManager.getConnection(connectionUri);
		connection.setAutoCommit(false);
	}

	private void createTables() throws SQLException {
		createListsTable();
		createItemsTable();
	}

	private void createListsTable() throws SQLException {
		String sql = new StringBuilder("CREATE TABLE IF NOT EXISTS todo_lists (")
				.append("id INTEGER PRIMARY KEY AUTOINCREMENT")
				.append(", name TEXT NOT NULL")
				.append(", created_at TEXT NOT NULL")
				.append(", updated_at TEXT NOT NULL")
				.append(")")
				.toString();

		connection.createStatement().executeUpdate(sql);
	}

	private void createItemsTable() throws SQLException {
		String sql = new StringBuilder("CREATE TABLE IF NOT EXISTS todo_items (")
				.append("id INTEGER PRIMARY KEY AUTOINCREMENT")
				.append(", list_id INTEGER NOT NULL")
				.append(", item_text TEXT NOT NULL")
				.append(", completed_at TEXT NULL")
				.append(", created_at TEXT NOT NULL")
				.append(", updated_at TEXT NOT NULL")
				.append(")")
				.toString();

		connection.createStatement().executeUpdate(sql);
	}
}
