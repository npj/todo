package io.npj.todo;

import javafx.beans.binding.StringBinding;

import java.sql.*;

/**
 * Created by npj on 12/12/16.
 */
public class TodoDB {
    protected static TodoDB instance = null;
    protected static String DATA_DIR = ".todo";

    protected Connection connection = null;

    public static TodoDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new TodoDB();
        }
        return instance;
    }

    protected TodoDB() throws SQLException {
        connectDB();
        createTables();
    }

    public Connection getConnection() {
        return connection;
    }

    public PreparedStatement prepareInsert(String tableName, String [] colummns) throws SQLException {
        StringBuilder builder = new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append(" (");

        for (String col : colummns) {
            builder.append(col);
        }

        builder.append(") VALUES (");

        for (String col : colummns) {
            builder.append("?");
        }

        builder.append(")");

        return connection.prepareStatement(
            builder.toString(),
            PreparedStatement.RETURN_GENERATED_KEYS
        );
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    private void connectDB() throws SQLException {
        String connectionUri = new StringBuilder("jdbc:sqlite:")
                .append(System.getProperty("user.home"))
                .append("/")
                .append(DATA_DIR)
                .append("/")
                .toString();

        connection = DriverManager.getConnection(connectionUri);
        connection.setAutoCommit(false);
    }

    private void createTables() throws SQLException {
        createListsTable();
        createItemsTable();
    }

    private void createListsTable() throws SQLException {
        String sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append("todo.lists (")
                .append("id INTEGER PRIMARY KEY AUTOINCREMENT")
                .append("name TEXT NOT NULL")
                .append("created_at TEXT NOT NULL")
                .append("updated_at TEXT NOT NULL")
                .append(")")
                .toString();

        connection.createStatement().executeUpdate(sql);
    }

    private void createItemsTable() throws SQLException {
        String sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append("todo.items (")
                .append("id INTEGER PRIMARY KEY AUTOINCREMENT")
                .append("list_id INTEGER NOT NULL")
                .append("item_text TEXT NOT NULL")
                .append("completed_at TEXT NOT NULL")
                .append("created_at TEXT NOT NULL")
                .append("updated_at TEXT NOT NULL")
                .append(")")
                .toString();

        connection.createStatement().executeUpdate(sql);
    }
}
