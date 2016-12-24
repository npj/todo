package io.npj.todo.lists;

import io.npj.todo.DB;
import junit.framework.TestCase;
import org.junit.Before;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by pbrindisi on 12/23/16.
 */
public class DbTestCase extends TestCase {
	DB testDB;

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
