package io.npj.todo.lists;

import io.npj.todo.DB;
import io.npj.todo.mvc.Model;
import io.npj.todo.mvc.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by npj on 12/12/16.
 */
public class ListService extends Service {
	public ListModel fetchOne(int listId) throws SQLException, DB.DataFileException {
		final String sql = "SELECT * FROM todo_lists WHERE id = ?";
		final Connection conn = DB.getInstance().getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, listId);

		ResultSet res = stmt.executeQuery();
		res.next();
		return populateList(res);
	}

	public List<ListModel> fetchAll() throws SQLException, DB.DataFileException {
		final String sql = "SELECT * FROM todo_lists";
		final Connection conn = DB.getInstance().getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		List<ListModel> results = new ArrayList<>();

		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			results.add(populateList(res));
		}

		return results;
	}

	public boolean create(ListModel list) throws SQLException, DB.DataFileException {
		DB db = DB.getInstance();

		PreparedStatement stmt = db.prepareInsert(
				"todo_lists",
				new String[] { "name", "created_at", "updated_at" }
		);

		if (list.getCreatedAt() == null) {
			list.setCreatedAt(new Date());
		}

		if (list.getUpdatedAt() == null) {
		   list.setUpdatedAt(new Date());
		}

		stmt.setString(1, list.getName());
		stmt.setString(2, Model.unparseDate(list.getCreatedAt()));
		stmt.setString(3, Model.unparseDate(list.getUpdatedAt()));

		if (doInsert(stmt, list)) {
			db.commit();
			return true;
		} else {
			return false;
		}
	}

	public void delete(Integer id) throws SQLException, DB.DataFileException {
		DB db = DB.getInstance();
		Connection conn = db.getConnection();

		final String listSql = "DELETE FROM todo_lists WHERE id = ?";
		final String itemSql = "DELETE FROM todo_items WHERE list_id = ?";

		PreparedStatement deleteList = conn.prepareStatement(listSql);
		PreparedStatement deleteItems = conn.prepareStatement(itemSql);

		deleteList.setInt(1, id);
		deleteItems.setInt(1, id);

		if (deleteList.executeUpdate() > 0 && deleteItems.executeUpdate() > 0) {
			db.commit();
		}
	}

	private ListModel populateList(ResultSet res) throws SQLException, DB.DataFileException {
		ListModel list = new ListModel();
		list.setId(res.getInt("id"));
		list.setName(res.getString("name"));
		list.setCreatedAt(Model.parseDate(res.getString("created_at")));
		list.setUpdatedAt(Model.parseDate(res.getString("updated_at")));
		return list;
	}
}
