package io.npj.todo;

import junit.framework.TestCase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by pbrindisi on 12/23/16.
 */
public abstract class DbTestCase extends TestCase {
	protected DB testDB;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		testDB = new TestDB();
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
