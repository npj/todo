package io.npj.todo.mvc;

import io.npj.todo.DB;
import io.npj.todo.mvc.Model;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by npj on 12/12/16.
 */
public class Store {
	private Optional<DB> db;

	public Store(Optional<DB> db) {
		this.db = db;
	}

	protected boolean doInsert(PreparedStatement stmt, Model model) throws SQLException {
		if (stmt.executeUpdate() <= 0) {
			return false;
		}

		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		model.setId(rs.getInt("last_insert_rowid()"));

		return true;
	}

	protected DB getDB() throws SQLException {
		return db.orElseThrow(() -> new SQLException("No database connection."));
	}
}
