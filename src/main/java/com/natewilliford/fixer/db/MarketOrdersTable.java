package com.natewilliford.fixer.db;

import com.natewilliford.fixer.objects.Market;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MarketOrdersTable extends Table {

    public static final String TABLE_NAME = "market_orders";
    public static final String COL_ID = "id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_ORDER_TYPE = "order_type";
    public static final String COL_RESOURCE_TYPE = "resource_type";
    public static final String COL_PRICE = "price";
    public static final String COL_FILLED = "filled";

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
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_ID + " INT NOT NULL, " +
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

    void create (Connection connection, Market.Order order) throws SQLException{
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (%s, %s, %s, %s, %s)",
                getTableName(),
                COL_USER_ID, COL_USER_ID, COL_ORDER_TYPE, COL_RESOURCE_TYPE, COL_PRICE, COL_FILLED,
                order.userId, order.userId, order.orderType, order.resourceType, order.pricePerUnit, order.filled);
        System.out.println("Executing SQL: " + sql);
        connection.createStatement().executeUpdate(sql);
    }

    public static ResultSet getOpenOrders(Connection connection, int resourceType, int orderType) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_RESOURCE_TYPE + " = " + resourceType +
                " AND " + COL_ORDER_TYPE + " = " + orderType +
                " AND " + COL_FILLED + " = 'false'" +
                " ORDER BY " + COL_PRICE + " ASC";
        System.out.println("Executing SQL: " + sql);
        return connection.createStatement().executeQuery(sql);
    }
}
