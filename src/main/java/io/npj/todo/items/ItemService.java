package io.npj.todo.items;

import io.npj.todo.DB;
import io.npj.todo.mvc.Model;
import io.npj.todo.mvc.Service;
import io.npj.todo.lists.ListModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pbrindisi on 12/21/16.
 */
public class ItemService extends Service {
	public List<ItemModel> fetchAll(ListModel list) throws SQLException {
		final String sql = "SELECT * FROM todo_items WHERE list_id = ?";
		final Connection conn = DB.getInstance().getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		List<ItemModel> results = new ArrayList<>();

		stmt.setInt(1, list.getId());

		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			ItemModel item = new ItemModel(list);
			item.setId(res.getInt("id"));
			item.setName(res.getString("item_text"));
			item.setCreatedAt(Model.parseDate(res.getString("created_at")));
			item.setUpdatedAt(Model.parseDate(res.getString("updated_at")));
			results.add(item);
		}

		return results;
	}

	public boolean create(ListModel list, ItemModel item) throws SQLException {
		DB db = DB.getInstance();

		PreparedStatement stmt = db.prepareInsert(
				"todo_items",
				new String[] { "list_id", "item_text", "created_at", "updated_at" }
		);

		if (item.getCreatedAt() == null) {
			item.setCreatedAt(new Date());
		}

		if (item.getUpdatedAt() == null) {
			item.setUpdatedAt(new Date());
		}

		stmt.setInt(1, list.getId());
		stmt.setString(2, item.getName());
		stmt.setString(3, Model.unparseDate(item.getCreatedAt()));
		stmt.setString(4, Model.unparseDate(item.getUpdatedAt()));

		if (doInsert(stmt, item)) {
			db.commit();
			return true;
		} else {
			return false;
		}
	}

	public void delete(ListModel list, Integer id) throws SQLException {
		DB db = DB.getInstance();
		Connection conn = db.getConnection();

		final String sql = "DELETE FROM todo_items WHERE list_id = ? AND id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, list.getId());
		stmt.setInt(2, id);

		if (stmt.executeUpdate() > 0) {
			db.commit();
		}
	}
}
