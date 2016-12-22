package io.npj.todo.mvc;

import io.npj.todo.mvc.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by npj on 12/12/16.
 */
public class Service {
	protected boolean doInsert(PreparedStatement stmt, Model model) throws SQLException {
		if (stmt.executeUpdate() <= 0) {
			return false;
		}

		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		model.setId(rs.getInt("last_insert_rowid()"));

		return true;
	}
}
