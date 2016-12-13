package io.npj.todo;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by npj on 12/12/16.
 */
public class ListService extends BaseService {
    public boolean create(ListModel list) throws SQLException {
        TodoDB db = TodoDB.getInstance();

        list.setCreatedAt(new Date());
        list.setUpdatedAt(new Date());

        PreparedStatement stmt = db.prepareInsert(
                "todo.lists",
                new String[]{"name", "created_at", "updated_at"}
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
