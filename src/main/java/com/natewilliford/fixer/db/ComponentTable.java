package com.natewilliford.fixer.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ComponentTable extends Table {

    private static final String TABLE_NAME = "components";
    private static final String COL_OBJECT_ID = "object_id";
    private static final String COL_TYPE = "type";
    private static final String COL_DATA = "data";

    ComponentTable() {}

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    int getVersion() {
        return 1;
    }

    @Override
    void create(Connection connection) throws SQLException {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_OBJECT_ID + " CHAR(36) NOT NULL, " +
                COL_TYPE + " INT NOT NULL, " +
                COL_DATA + " TEXT NOT NULL" +
                "PRIMARY KEY ( " + COL_OBJECT_ID + ", " + COL_TYPE + " ) " +
                ")";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    @Override
    void upgrade(Connection connection, int version) throws SQLException {}

//    public void save(String gameObjectId, int componentType, JSONObject json) throws SQLException {
//        String sql = String.format("REPLACE INTO %s (%s, %s, %s ) VALUES ('%s', %s, '%s');",
//                TABLE_NAME, COL_OBJECT_ID, COL_TYPE, COL_DATA,
//                gameObjectId, componentType, json);
//
//        Statement statement = connection.createStatement();
//        statement.executeUpdate(sql);
//        statement.close();
//    }
}
