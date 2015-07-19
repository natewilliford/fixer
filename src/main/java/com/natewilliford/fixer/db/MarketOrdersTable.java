package com.natewilliford.fixer.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MarketOrdersTable extends Table {

    private static final String TABLE_NAME = "market_orders";
    private static final String COL_ID = "id";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_ORDER_TYPE = "order_type";
    private static final String COL_RESOURCE_TYPE = "resource_type";
    private static final String COL_PRICE = "price";
    private static final String COL_FILLED = "filled";

    MarketOrdersTable() {}

    @Override
    String getTableName() {
        return TABLE_NAME;
    }

    @Override
    int getVersion() {
        return 1;
    }

    @Override
    void create(Connection connection) throws SQLException {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_ID + " BIGINT NOT NULL, " +
                COL_ORDER_TYPE + " INT NOT NULL, " +
                COL_RESOURCE_TYPE + " INT NOT NULL, " +
                COL_PRICE + " BIGINT NOT NULL, " +
                COL_FILLED + " BOOLEAN DEFAULT false)";
        System.out.println("Executing SQL: " + sql);
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    @Override
    void upgrade(Connection connection, int version) throws SQLException {}
}
