package io.npj.todo;

import io.npj.todo.DB;

import java.sql.SQLException;

/**
 * Created by pbrindisi on 12/23/16.
 */
public class TestDB extends DB {
	public TestDB() throws SQLException {
		super();
	}

	@Override
	protected String getConnectionUri() {
		// use in-memory db for testing
		return "jdbc:sqlite:";
	}

	@Override
	public void commit() throws SQLException {
		// never commit changes
	}
}
