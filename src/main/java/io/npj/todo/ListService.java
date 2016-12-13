package io.npj.todo;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by npj on 12/12/16.
 */
public class ListService extends BaseService {
    public List<ListModel> fetchAll() throws SQLException {
        final String sql = "SELECT * FROM todo.lists";
        final Connection conn = TodoDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<ListModel> results = new ArrayList<>();

        ResultSet res = stmt.executeQuery();
        while (res.next()) {
            ListModel list = new ListModel();
            list.setId(res.getInt("id"));
            list.setName(res.getString("name"));
            list.setCreatedAt(res.getDate("created_at"));
            list.setUpdatedAt(res.getDate("updated_at"));
            results.add(list);
        }

        return results;
    }

    public boolean create(ListModel list) throws SQLException {
        TodoDB db = TodoDB.getInstance();

        list.setCreatedAt(new Date());
        list.setUpdatedAt(new Date());

        PreparedStatement stmt = db.prepareInsert(
                "todo.lists",
                new String[] { "name", "created_at", "updated_at" }
        );

        stmt.setString(0, list.getName());
        stmt.setDate(1, list.getCreatedAt());
        stmt.setDate(2, list.getUpdatedAt());

        if (doInsert(stmt, list)) {
            db.commit();
            return true;
        } else {
            return false;
        }
    }
}
