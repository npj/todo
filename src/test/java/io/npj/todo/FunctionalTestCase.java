package io.npj.todo;

import junit.framework.TestCase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class FunctionalTestCase extends TestCase {
	private TestTodoComponent testTodoComponent;
	protected DB testDB;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		testTodoComponent = DaggerTestTodoComponent.create();
		testDB = testTodoComponent.db().get();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		testDB.getConnection().rollback();
	}

	protected int getLastInsertId(Statement stmt) throws SQLException {
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		return rs.getInt("last_insert_rowid()");
	}
}
