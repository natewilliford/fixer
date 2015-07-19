package com.natewilliford.fixer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String DB_NAME = "dev.db";
    private static final String VERSIONS_TABLE_NAME = "table_versions";
    private static final String VERSIONS_COL_TABLE_NAME = "table_name";
    private static final String VERSIONS_COL_VERSION = "version";

    private Connection connection = null;

    private List<Table> tables = new ArrayList<>();


    public Database() {}

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
            connection.setAutoCommit(true);
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void init() throws SQLException {
        tables.add(new MarketOrdersTable());
        create();
    }

    private void create() throws SQLException {
        String createTableVersionsSql = "CREATE TABLE IF NOT EXISTS '" + VERSIONS_TABLE_NAME + "' (" +
                VERSIONS_COL_TABLE_NAME + " VAR_CHAR(255) PRIMARY KEY," +
                VERSIONS_COL_VERSION + " INT NOT NULL" +
                ")";

        System.out.println("Executing SQL: " + createTableVersionsSql);
        connection.createStatement().executeUpdate(createTableVersionsSql);
//        connection.commit();

        for (Table table : tables) {
            int originalTableVersion = getTableVersion(table.getTableName());
            int tableVersion = originalTableVersion;
            if (tableVersion == -1) {
                tableVersion = 1;
                table.create(connection);
            }

            while (tableVersion < table.getVersion()) {
                table.upgrade(connection, ++tableVersion);
            }

            if (originalTableVersion != tableVersion) {
                setTableVersion(table.getTableName(), tableVersion);
            }
        }
    }

    private int getTableVersion(String tableName) {
        ResultSet resultSet = null;
        try {
            String sql = "SELECT version FROM " + VERSIONS_TABLE_NAME + " WHERE " + VERSIONS_COL_TABLE_NAME + " = '" + tableName + "'";
            System.out.println("Executing SQL: " + sql);
            resultSet = connection.createStatement().executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt("version");
            }
        } catch (SQLException e) {
          // TODO: Log something.
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
                // meh.
            }
        }
        return -1;
    }

    private void setTableVersion(String tableName, int version) throws SQLException{
        String sql = "INSERT OR REPLACE INTO " + VERSIONS_TABLE_NAME +
                " (" + VERSIONS_COL_TABLE_NAME + ", " + VERSIONS_COL_VERSION + ") " +
                "VALUES ('" + tableName + "', " + version + ")";
        System.out.println("Executing SQL: " + sql);
        connection.createStatement().executeUpdate(sql);
    }
}
