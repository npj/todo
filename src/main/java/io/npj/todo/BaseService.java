package io.npj.todo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by npj on 12/12/16.
 */
public class BaseService {
    protected boolean doInsert(PreparedStatement stmt, BaseModel model) throws SQLException {
        if (stmt.executeUpdate() <= 0) {
            return false;
        }

        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        model.setId(rs.getInt("id"));

        return true;
    }
}
